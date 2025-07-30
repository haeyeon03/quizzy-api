package study.quizzy.domain.dto.challenger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChallengerResponseDto {
    private String challengerId;
    private String nickname;
    private String provider;
    private String email;
}
