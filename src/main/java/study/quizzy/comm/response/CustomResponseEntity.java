package study.quizzy.comm.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import study.quizzy.comm.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomResponseEntity {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message("The request has been successfully processed.")
                .data(data)
                .timestamp(TimeUtil.getTimestamp())
                .build();
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<ApiResponse<Void>> success() {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("The request has been successfully processed.")
                .timestamp(TimeUtil.getTimestamp())
                .build();
        return ResponseEntity.ok(response);
    }
    
    public static ResponseEntity<ApiResponse<Void>> error(String message) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(message)
                .timestamp(TimeUtil.getTimestamp())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    public static ResponseEntity<ApiResponse<Void>> error(HttpStatus status, String message) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(status.value())
                .message(message)
                .timestamp(TimeUtil.getTimestamp())
                .build();
        return ResponseEntity.status(status).body(response);
    }
}