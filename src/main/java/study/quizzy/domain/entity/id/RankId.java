package study.quizzy.domain.entity.id;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class RankId implements Serializable {
	private Long quizId;
	private String challengerId;
}
