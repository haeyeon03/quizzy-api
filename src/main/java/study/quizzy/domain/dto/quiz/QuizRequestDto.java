package study.quizzy.domain.dto.quiz;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import study.quizzy.domain.dto.base.BasePageRequestDto;
import study.quizzy.domain.entity.QuizQuestion;

@Getter
@Setter
@ToString
public class QuizRequestDto extends BasePageRequestDto {
	
	private Long quizQuestionId;
	
	private Long quizId;
	private String title;
	private String description;
	private String imageFile;
	private List<QuizQuestionRequestDto> quizQuestionList = new ArrayList<>();
}
