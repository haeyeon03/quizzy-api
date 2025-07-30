package study.quizzy.domain.entity.id;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class RankId implements Serializable {
	
    public RankId(Long quizId, String challengerId) {
		this.quizId = quizId;
		this.challengerId = challengerId;
	}
    
	@Column(name = "challenger_id")  // ← 대문자!
    private String challengerId;
    @Column(name = "quiz_id")
    private Long quizId;
}