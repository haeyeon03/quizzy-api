package study.quizzy.auth.jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import study.quizzy.auth.jwt.util.JwtUtil;
import study.quizzy.comm.response.ApiResponse;
import study.quizzy.comm.util.TimeUtil;
import study.quizzy.domain.dto.challenger.ChallengerAuthDto;

@Log4j2
public class JwtFilter extends OncePerRequestFilter {

	// filter 예외 URL 목록
	private static final List<String> EXCLUDE_URLS = List.of("/api/challengers/login");

	private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		return EXCLUDE_URLS.stream().anyMatch(pattern -> PATH_MATCHER.match(pattern, path));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Header 에서 Access Token 추출
		String accessToken = extractToken(request.getHeader("Authorization"));
		if (accessToken == null || accessToken.isBlank()) {
			// Security 인증 객체가 없이 호출되어 SecurityConfig 에 permitAll() URL 이 아니라면 인증 오류 발생
			filterChain.doFilter(request, response);
			return;
		}

		try {
			// 토큰 유효성 검사
			Map<String, Object> claims = JwtUtil.validateToken(accessToken);
			String challengerId = (String) claims.get("challengerId");
			String role = (String) claims.get("role");

			ChallengerAuthDto challengerAuthDto = new ChallengerAuthDto(challengerId, role);

			// Security 인증 객체 생성 및 인증
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(challengerAuthDto,
					null, challengerAuthDto.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authToken);

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			handleJwtException(response, e);
		}
	}

	private void handleJwtException(HttpServletResponse response, Exception e) throws IOException {
		// Map -> JSON String
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonStr = objectMapper
				.writeValueAsString(ApiResponse.<String>builder().status(HttpStatus.UNAUTHORIZED.value())
						.message(e.getMessage()).timestamp(TimeUtil.getTimestamp()).build());

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.println(jsonStr);
		printWriter.close();
	}

	private String extractToken(String authHeader) {
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}
}
