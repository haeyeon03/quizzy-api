package study.quizzy.domain.entity.id;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class RankId implements Serializable {
    @Column(name = "challenger_id")  // ← 대문자!
    private String challengerId;
    @Column(name = "quiz_id")
    private Long quizId;
}