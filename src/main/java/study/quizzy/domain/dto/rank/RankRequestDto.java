package study.quizzy.domain.dto.rank;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankRequestDto {
    private Long quizId;
    private String challengerId;
    private int score;
    private int durationMs;
}
