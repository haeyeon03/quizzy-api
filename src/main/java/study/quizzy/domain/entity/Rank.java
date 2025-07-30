package study.quizzy.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.quizzy.domain.entity.base.BaseTimeEntity;
import study.quizzy.domain.entity.id.RankId;

@Entity
@Table(name = "rank")
@Getter
@NoArgsConstructor
public class Rank extends BaseTimeEntity {
	@EmbeddedId
    private RankId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", insertable = false, updatable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenger_id", insertable = false, updatable = false)
    private Challenger challenger;

    @Column(name = "score")
    private int score;

    @Column(name = "duration_ms")
    private int durationMs;
    
	public Rank(RankId rankId, int score, int durationMs) {
		this.id = rankId;
		this.score= score;
		this.durationMs=durationMs;
		
	}

}

