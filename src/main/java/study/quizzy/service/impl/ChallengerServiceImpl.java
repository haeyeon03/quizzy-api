package study.quizzy.service.impl;

import org.springframework.stereotype.Service;
import study.quizzy.domain.dto.challenger.ChallengerRequestDto;
import study.quizzy.domain.dto.challenger.ChallengerResponseDto;
import study.quizzy.service.ChallengerService;

import java.util.List;

@Service
public class ChallengerServiceImpl implements ChallengerService {
    @Override
    public List<ChallengerResponseDto> getChallengerList(ChallengerRequestDto request) {
        return List.of();
    }
}
