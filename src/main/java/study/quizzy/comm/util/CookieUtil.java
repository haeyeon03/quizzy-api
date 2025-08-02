package study.quizzy.comm.util;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
	public static void setCookie(HttpServletResponse response, String refreshToken) {
		ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken).httpOnly(true) // JS 접근 못하게
				.secure(false) // HTTPS에서만 전송
				.path("/api/").sameSite("Strict") // CSRF 방지
				.maxAge(Duration.ofDays(7)) // 유효기간
				.build();

		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}
}
