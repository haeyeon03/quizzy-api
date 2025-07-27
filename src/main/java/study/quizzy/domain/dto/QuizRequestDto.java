package study.quizzy.domain.dto;

import lombok.Getter;
import lombok.Setter;
import study.quizzy.domain.dto.base.BasePageRequestDto;

@Getter
@Setter
public class QuizRequestDto extends BasePageRequestDto {
	
	private String title;
}
