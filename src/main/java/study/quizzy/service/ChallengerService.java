package study.quizzy.service;

import study.quizzy.comm.response.PageResponse;
import study.quizzy.domain.dto.challenger.ChallengerRequestDto;
import study.quizzy.domain.dto.challenger.ChallengerResponseDto;
import study.quizzy.domain.dto.quiz.QuizRankResponseDto;

public interface ChallengerService {
    Long addChallenger(ChallengerRequestDto request);

    PageResponse<ChallengerResponseDto> getChallengerList(ChallengerRequestDto request);

    ChallengerResponseDto getChallenger(String challengerId);

    Long modifyChallenger(String challengerId, ChallengerRequestDto request);

    Long removeChallenger(String challengerId);

    PageResponse<QuizRankResponseDto> getMyQuizList(String challengerId, ChallengerRequestDto request);
}
