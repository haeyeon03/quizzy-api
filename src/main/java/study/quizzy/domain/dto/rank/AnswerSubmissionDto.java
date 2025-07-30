package study.quizzy.domain.dto.rank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerSubmissionDto {
	private Long quizQuestionId;
    private String inputAnswer;
}
