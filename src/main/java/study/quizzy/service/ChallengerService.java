package study.quizzy.service;

import study.quizzy.domain.dto.challenger.ChallengerRequestDto;
import study.quizzy.domain.dto.challenger.ChallengerResponseDto;
import study.quizzy.domain.dto.rank.RankRequestDto;
import study.quizzy.domain.dto.rank.RankResponseDto;

import java.util.List;

public interface ChallengerService {
    List<ChallengerResponseDto> getChallengerList(ChallengerRequestDto request);

    Long addChallenger(ChallengerRequestDto request);

    Long modifyChallenger(ChallengerRequestDto request);

    Long removeChallenger(ChallengerRequestDto request);

    Long addRankByChallenger(RankRequestDto request);

    List<RankResponseDto> getRankListByChallenger(String challengerId);


}
