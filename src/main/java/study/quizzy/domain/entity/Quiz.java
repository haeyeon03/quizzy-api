package study.quizzy.domain.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import study.quizzy.domain.entity.base.BaseTimeEntity;

@Entity
@Table(name = "quiz")
@SequenceGenerator(name = "quiz_seq_gen", sequenceName = "quiz_seq", initialValue = 1, allocationSize = 1)
public class Quiz extends BaseTimeEntity {
	
	@Id
	@Column(name = "quiz_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_seq_gen")
	private Long quizId;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "quiz")
	private List<QuizQuestion> quizQuestionList = new ArrayList<>();
}
