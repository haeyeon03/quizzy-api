package study.quizzy.domain.dto.challenger;

import lombok.*;
import study.quizzy.domain.dto.base.BasePageRequestDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChallengerRequestDto extends BasePageRequestDto {
    private String challengerId;
    private String password;
    private String role;
    private String nickname;
    private String provider;
    private String email;

    private Long quizId; // 퀴즈별 도전자 랭킹 조회용
}
