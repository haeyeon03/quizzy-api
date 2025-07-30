package study.quizzy.auth.jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import study.quizzy.auth.jwt.util.JwtUtil;
import study.quizzy.domain.dto.challenger.ChallengerAuthDto;

public class JwtFilter extends OncePerRequestFilter{
	
	private static final List<String> EXCLUDE_URLS = List.of("/api/challengers/login");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// header 에 있는 accessToken 을 꺼냄
		try {
			String accessToken = extractToken(request.getHeader("Authorization"));
			
			// accessToken 복호화 및 유효성 검사
			Map<String,Object> claims = JwtUtil.validateToken(accessToken);
			
			// accessToken 의 claims 으로 UserDetails DTO 생성
			String challengerId = (String) claims.get("challengerId");
			ChallengerAuthDto challengerAuthDto = new ChallengerAuthDto(challengerId);
			
			// security 인증
			UsernamePasswordAuthenticationToken authToken =  new UsernamePasswordAuthenticationToken(challengerAuthDto, null, challengerAuthDto.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authToken);
			
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			ObjectMapper objectMapper = new ObjectMapper();
			
			String jsonStr = objectMapper.writeValueAsString(Map.of("error", "ERROR_LOGIN"));
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.println(jsonStr);
			printWriter.close();
		}
		
	}

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return EXCLUDE_URLS.contains(path); // true or false
    }
    
    public String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // "Bearer " 길이는 7
        }
        return null; // or throw exception / return "" 등 처리 방식 선택
    }

}
