package study.quizzy.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import study.quizzy.domain.entity.base.BaseTimeEntity;
import study.quizzy.domain.entity.id.RankId;

@Entity
@Table(name = "rank")
public class Rank extends BaseTimeEntity {
	
	@EmbeddedId
	private RankId id;

	@ManyToOne
	@MapsId("quizId")
	@JoinColumn(name = "quiz_id")
	private Quiz Quiz;

	@ManyToOne
	@MapsId("challengerId")
	@JoinColumn(name = "challenger_id")
	private Challenger challenger;

	@Column(name = "score")
	private int score;
	
	@Column(name = "duration_ms")
	private int durationMs;
}
