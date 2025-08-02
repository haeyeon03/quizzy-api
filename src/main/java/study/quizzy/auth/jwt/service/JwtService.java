package study.quizzy.auth.jwt.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import study.quizzy.auth.jwt.dto.TokenDto;
import study.quizzy.auth.jwt.util.JwtUtil;
import study.quizzy.comm.exception.CustomJwtException;

@Service
public class JwtService {

	public TokenDto reissueTokens(String refreshToken) {
		// refreshToken이 null 이라면 예외처리
		if (refreshToken == null) {
			throw new CustomJwtException("유효한 Refresh 토큰이 존재하지 않습니다.");
		}
		try {
			// refreshToken을 검사하여 유효하면 새 토큰을 생성하여 반환
			Map<String, Object> claims = JwtUtil.validateToken(refreshToken);

			String newAccessToken = JwtUtil.generateToken(claims, 10);
			String newRefreshToken = JwtUtil.generateToken(claims, 60 * 24);

			return new TokenDto(newAccessToken, newRefreshToken);
		} catch (Exception e) {
			throw new CustomJwtException("유효한 Refresh 토큰이 아닙니다.");
		}	
	}
}