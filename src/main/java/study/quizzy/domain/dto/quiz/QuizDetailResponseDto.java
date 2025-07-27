package study.quizzy.domain.dto.quiz;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuizDetailResponseDto{
	private Long quizId;
	private String title;
	
	private List<QuizQuestionResponseDto> quizQuestionList = new ArrayList<>();
	
//	private List<quizAnswerResponseList> quizQnswerResponseDto;

}
