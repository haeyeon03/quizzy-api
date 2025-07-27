package study.quizzy.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import study.quizzy.domain.dto.challenger.ChallengerRequestDto;
import study.quizzy.domain.dto.challenger.ChallengerResponseDto;
import study.quizzy.domain.dto.rank.RankRequestDto;
import study.quizzy.domain.dto.rank.RankResponseDto;
import study.quizzy.domain.entity.Challenger;
import study.quizzy.domain.entity.Rank;
import study.quizzy.repository.ChallengerRepository;
import study.quizzy.repository.RankRepository;
import study.quizzy.service.ChallengerService;

import java.util.List;

@Service
public class ChallengerServiceImpl implements ChallengerService {
    @Autowired
    ChallengerRepository challengerRepository;

    @Autowired
    RankRepository rankRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ChallengerResponseDto> getChallengerList(ChallengerRequestDto request) {
        return List.of();
    }

    @Override
    public Long addChallenger(ChallengerRequestDto request) {
        String password = request.getPassword();
        // TODO. password 암호화 로직 추가
        
        Challenger challenger = modelMapper.map(request, Challenger.class);

        try {
            Challenger saved = challengerRepository.save(challenger);
            return 1L;
        } catch (DataIntegrityViolationException e) {
            // 제약 조건 위반 (중복, null 등)
            // 실패 처리
        } catch (Exception e) {
            // 기타 실패 처리
        }
        return 0L;
    }

    @Override
    public Long modifyChallenger(ChallengerRequestDto request) {
        String password = request.getPassword();
        // TODO. password 암호화 로직 추가

        Challenger challenger = modelMapper.map(request, Challenger.class);

        try {
            Challenger saved = challengerRepository.save(challenger);
            return 1L;
        } catch (DataIntegrityViolationException e) {
            // 제약 조건 위반 (중복, null 등)
            // 실패 처리
        } catch (Exception e) {
            // 기타 실패 처리
        }
        return 0L;
    }

    @Override
    public Long removeChallenger(ChallengerRequestDto request) {
        String challengerId = request.getChallengerId();
        try {
            // 도전자 정보 존재 확인
            if (challengerRepository.existsById(challengerId)) {
                // 존재 시 삭제
                challengerRepository.deleteById(request.getChallengerId());
                return 1L;
            }
        } catch (DataIntegrityViolationException e) {
            // 제약 조건 위반 (중복, null 등)
            // 실패 처리
        } catch (Exception e) {
            // 기타 실패 처리
        }
        return 0L;
    }

    @Override
    public Long addRankByChallenger(RankRequestDto request) {
        Rank rank = modelMapper.map(request, Rank.class);

        try {
            Rank saved = rankRepository.save(rank);
            return 1L;
        } catch (DataIntegrityViolationException e) {
            // 제약 조건 위반 (중복, null 등)
            // 실패 처리
        } catch (Exception e) {
            // 기타 실패 처리
        }
        return 0L;
    }

    @Override
    public List<RankResponseDto> getRankListByChallenger(String challengerId) {
        List<Rank> rankList = rankRepository.findAllById_ChallengerIdOrderByUpdatedAtDesc(challengerId);

        return rankList.stream().map(rank -> modelMapper.map(rank, RankResponseDto.class)).toList();
    }
}
