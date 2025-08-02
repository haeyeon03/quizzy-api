package study.quizzy.auth.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import study.quizzy.comm.response.ApiResponse;
import study.quizzy.comm.util.TimeUtil;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
			// Map -> JSON String
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonStr = objectMapper.writeValueAsString(
							ApiResponse.<String>builder()
				                .status(HttpStatus.UNAUTHORIZED.value())
				                .message(exception.getMessage())
				                .timestamp(TimeUtil.getTimestamp())
								.build()
						);

				response.setContentType("application/json; charset=UTF-8");
				PrintWriter printWriter = response.getWriter();
				printWriter.println(jsonStr);
				printWriter.close();
	}

}
