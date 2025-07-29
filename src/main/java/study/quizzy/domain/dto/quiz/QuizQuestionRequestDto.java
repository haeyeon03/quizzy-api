package study.quizzy.domain.dto.quiz;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import study.quizzy.domain.entity.QuizAnswer;

@Getter
@Setter
public class QuizQuestionRequestDto {
	
	private Long quizQuestionId;
	private String question;
	private String imageFile;
	private List<QuizAnswerRequestDto> quizAnswerList = new ArrayList<>();
}
