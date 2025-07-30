package study.quizzy.service;

import study.quizzy.domain.dto.challenger.ChallengerRequestDto;
import study.quizzy.domain.dto.challenger.ChallengerResponseDto;
import study.quizzy.domain.dto.quiz.QuizRankResponseDto;

import java.util.List;

public interface ChallengerService {
    Long addChallenger(ChallengerRequestDto request);

    List<ChallengerResponseDto> getChallengerList(ChallengerRequestDto request);

    ChallengerResponseDto getChallenger(String challengerId);

    Long modifyChallenger(String challengerId, ChallengerRequestDto request);

    Long removeChallenger(String challengerId);

    List<QuizRankResponseDto> getMyQuizList(String challengerId);
}
