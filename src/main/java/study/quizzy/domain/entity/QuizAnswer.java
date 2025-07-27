package study.quizzy.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import study.quizzy.domain.entity.base.BaseTimeEntity;

@Entity
@Table(name = "quiz_answer")
@SequenceGenerator(name = "quiz_answer_seq_gen", sequenceName = "quiz_answer_seq", initialValue = 1, allocationSize = 1)
public class QuizAnswer extends BaseTimeEntity {

	@Id
	@Column(name = "quiz_answer_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_answer_seq_gen")
	private Long quizAnswerId;

	@ManyToOne
	@JoinColumn(name = "quiz_question_id")
	private QuizQuestion quizQuestion;

	@Column(name = "answer")
	private String answer;
}
