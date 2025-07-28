package study.quizzy.comm.exception;

public class CustomJwtException extends RuntimeException {
	public CustomJwtException(String message) {
		super(message);
	}
}
