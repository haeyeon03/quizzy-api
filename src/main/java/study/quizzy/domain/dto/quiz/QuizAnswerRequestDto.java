package study.quizzy.domain.dto.quiz;
import lombok.Getter;
import lombok.Setter;
import study.quizzy.domain.entity.QuizQuestion;

@Getter
@Setter
public class QuizAnswerRequestDto {
	
	private Long quizAnswerId;
	private String answer;

}
