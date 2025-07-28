package study.quizzy.comm.exception.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import study.quizzy.comm.exception.CustomJwtException;
import study.quizzy.comm.response.ApiResponse;
import study.quizzy.comm.response.CustomResponseEntity;

@RestControllerAdvice
public class CustomControllerExceptionAdvice {
	@ExceptionHandler(CustomJwtException.class)
	protected ResponseEntity<ApiResponse<Void>> handleJwtException(CustomJwtException e) {
		String message = e.getMessage();
		return CustomResponseEntity.error(message);
	}
}
