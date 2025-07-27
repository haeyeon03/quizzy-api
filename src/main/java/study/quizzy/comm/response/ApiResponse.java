package study.quizzy.comm.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;          // HTTP 상태 코드
    private String message;      // 응답 메세지
    private T data;              // 실제 응답 데이터 (nullable)
    private String timestamp;    // 응답 시간 (ISO 포맷)
}
