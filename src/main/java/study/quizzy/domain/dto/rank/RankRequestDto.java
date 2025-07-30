package study.quizzy.domain.dto.rank;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankRequestDto {
    private Long quizId;
    private String challengerId;
    private int score;
    private int durationMs;
    
    private List<AnswerSubmissionDto> inputAnswerList = new ArrayList<>();
}
