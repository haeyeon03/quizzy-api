package study.quizzy.domain.dto.quiz;

import lombok.Getter;
import lombok.Setter;
import study.quizzy.domain.dto.base.BasePageRequestDto;

@Getter
@Setter
public class QuizRequestDto extends BasePageRequestDto {
	
	private Long quizId;
	private Long questionId;
	private String title;
	
}
