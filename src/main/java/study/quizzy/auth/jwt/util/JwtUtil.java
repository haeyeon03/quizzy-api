package study.quizzy.auth.jwt.util;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import study.quizzy.comm.exception.CustomJwtException;

public class JwtUtil {
	// 최소 256비트(32바이트) 이상이어야됨, HMAC-SHA 알고리즘용 사용되는 키값
	private static String KEY = "this-is-my-quizzy-app-for-study-jwt-key-2025";

	// 사용자 정보(Map)를 받아서 JWT 토큰을 생성
	public static String generateToken(Map<String, Object> valueMap, int min) {
		SecretKey key = null;
		try {
			// 문자열 비밀 키를 SecretKey 객체로 변환 (HMAC-SHA 알고리즘 사용)
			key = Keys.hmacShaKeyFor(KEY.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		// JWT 토큰 문자열을 생성
		String jwtStr = Jwts.builder().setHeader(Map.of("typ", "JWT")) // JWT 헤더 지정
				.setClaims(valueMap) // 사용자 정보(Claims) 설정
				.setIssuedAt(Date.from(ZonedDateTime.now().toInstant())) // 발행 시각
				.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant())) // 만료 시각
				.signWith(key) // 서명
				.compact(); // 최종 JWT 문자열 생성
		return jwtStr; // JWT 토큰 문자열 :"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
	}

	// 전달받은 JWT 토큰을 검증 및 해석
	public static Map<String, Object> validateToken(String token) {
		Map<String, Object> claim = null;
		try {
			SecretKey key = Keys.hmacShaKeyFor(KEY.getBytes("UTF-8"));
			// 파싱 및 검증,실패 시 에러
			claim = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		} catch (MalformedJwtException malformedJwtException) {
			throw new CustomJwtException("MalFormed"); // 형식이 잘못된 토큰 (손상 등)
		} catch (ExpiredJwtException expiredJwtException) {
			throw new CustomJwtException("Expired"); // 만료된 토큰
		} catch (InvalidClaimException invalidClaimException) {
			throw new CustomJwtException("Invalid"); // 클래임 내용이 잘못됨
		} catch (JwtException jwtException) {
			throw new CustomJwtException("JWTError"); // 기타 jwt 관련예외
		} catch (Exception e) {
			throw new CustomJwtException("Error"); // 기타 모든 예외
		}
		return claim;
	}
}
