package study.quizzy.domain.dto.quiz;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import study.quizzy.domain.entity.QuizQuestion;

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
	private List<QuizQuestion> quizQuestionList = new ArrayList<>();
}
