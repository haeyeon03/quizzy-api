package study.quizzy.domain.dto.rank;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RankResponseDto {
    private Long quizId;
    private String challengerId;
    private String title;
    private String nickname;
    private int score;
    private int durationMs;
}
