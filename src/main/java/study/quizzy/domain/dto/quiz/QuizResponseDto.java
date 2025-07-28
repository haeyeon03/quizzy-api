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
public class QuizResponseDto{
	private Long quizId;
	private String title;
	private String description;
	private String imageFile;
	
	private List<QuizQuestionResponseDto> quizQuestionList = new ArrayList<>();
	private List<String> answerList = new ArrayList<>();
}
