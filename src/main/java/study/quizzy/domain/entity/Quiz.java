package study.quizzy.domain.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import study.quizzy.domain.entity.base.BaseTimeEntity;

@Entity
@Table(name = "quiz")
@Getter
@Setter
@ToString
@NoArgsConstructor
@SequenceGenerator(name = "quiz_seq_gen", sequenceName = "quiz_seq", initialValue = 1, allocationSize = 1)
public class Quiz extends BaseTimeEntity {
	
	public Quiz(String title, String description, String imageFile) {
		this.title = title;
		this.description = description;
		this.imageFile = imageFile;
	}

	@Id
	@Column(name = "quiz_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_seq_gen")
	private Long quizId;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;
	
	@Column(name = "image_file")
	private String imageFile;

	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuizQuestion> quizQuestionList = new ArrayList<>();
}
