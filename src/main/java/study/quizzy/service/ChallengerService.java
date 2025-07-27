package study.quizzy.service;

import study.quizzy.domain.dto.challenger.ChallengerRequestDto;
import study.quizzy.domain.dto.challenger.ChallengerResponseDto;

import java.util.List;

public interface ChallengerService {
    List<ChallengerResponseDto> getChallengerList(ChallengerRequestDto request);
}
