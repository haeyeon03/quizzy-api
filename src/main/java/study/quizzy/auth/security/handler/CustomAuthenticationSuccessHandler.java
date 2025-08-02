package study.quizzy.auth.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import study.quizzy.auth.jwt.util.JwtUtil;
import study.quizzy.comm.response.ApiResponse;
import study.quizzy.comm.util.CookieUtil;
import study.quizzy.comm.util.TimeUtil;
import study.quizzy.domain.dto.challenger.ChallengerAuthDto;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// Authentication 에서 ChallengerAuthDto 정보 가져오기
		ChallengerAuthDto challengerAuthDto = (ChallengerAuthDto) authentication.getPrincipal();

		Map<String, Object> claims = new HashMap<>();
		claims.put("challengerId", challengerAuthDto.getChallengerId());
		claims.put("role", challengerAuthDto.getRole());

		// JWT 생성
		String accessToken = JwtUtil.generateToken(claims, 10);
		String refreshToken = JwtUtil.generateToken(claims, 60 * 24);

		Map<String, String> responseMap = new HashMap<>();
		responseMap.put("accessToken", accessToken);
		responseMap.put("nickname", challengerAuthDto.getNickname());

		// 쿠키로 저장 (HttpOnly)
		CookieUtil.setCookie(response, refreshToken);

		// Map -> JSON String
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonStr = objectMapper.writeValueAsString(
					ApiResponse.<Map<String, String>>builder()
						.status(HttpStatus.OK.value())
						.message("The request has been successfully processed.")
						.data(responseMap)
						.timestamp(TimeUtil.getTimestamp())
						.build()
				);

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.println(jsonStr);
		printWriter.close();
	}

	

}
