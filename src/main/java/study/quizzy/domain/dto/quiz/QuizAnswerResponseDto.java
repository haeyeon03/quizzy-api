package study.quizzy.domain.dto.quiz;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizAnswerResponseDto {
	private Long quizQuestionId;
	private List<String> answerList = new ArrayList<>();
}
