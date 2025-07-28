package study.quizzy.domain.dto.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuizAnswerResponseDto {
    private Long quizAnswerId;
    private String answer;
}
