package study.quizzy.domain.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizQuestionResponseDto {
	private Long quizQuestionId;
	private String question;
	private List<QuizAnswerResponseDto> quizAnswerList = new ArrayList<>();
}
