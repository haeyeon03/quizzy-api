package study.quizzy.domain.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.quizzy.domain.entity.base.BaseTimeEntity;

@Entity
@Table(name = "quiz_question")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "quiz_question_seq_gen", sequenceName = "quiz_question_seq", initialValue = 1, allocationSize = 1)
public class QuizQuestion extends BaseTimeEntity {

	public QuizQuestion(String question, String imageFile, Quiz quiz) {
		this.question =question;
		this.imageFile =imageFile;
		this.quiz =quiz;
	}

	@Id
	@Column(name = "quiz_question_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_question_seq_gen")
	private Long quizQuestionId;

	@ManyToOne
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;
	
	@Column(name = "question")
	private String question;
	
	@Column(name = "image_file")
	private String imageFile;
	
	@OneToMany(mappedBy = "quizQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuizAnswer> quizAnswerList = new ArrayList<>();
}
