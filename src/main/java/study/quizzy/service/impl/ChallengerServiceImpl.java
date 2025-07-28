package study.quizzy.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import study.quizzy.domain.dto.challenger.ChallengerAuthDto;
import study.quizzy.domain.dto.challenger.ChallengerRequestDto;
import study.quizzy.domain.dto.challenger.ChallengerResponseDto;
import study.quizzy.domain.dto.rank.RankRequestDto;
import study.quizzy.domain.dto.rank.RankResponseDto;
import study.quizzy.domain.entity.Challenger;
import study.quizzy.domain.entity.Rank;
import study.quizzy.repository.ChallengerRepository;
import study.quizzy.repository.RankRepository;
import study.quizzy.service.ChallengerService;

@Service
public class ChallengerServiceImpl implements ChallengerService, UserDetailsService {
	@Autowired
	ChallengerRepository challengerRepository;

	@Autowired
	RankRepository rankRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Challenger challenger = challengerRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + username));
		
		ChallengerAuthDto dto = modelMapper.map(challenger, ChallengerAuthDto.class);
		return dto;
	}

	@Override
	public List<ChallengerResponseDto> getChallengerList(ChallengerRequestDto request) {
		return List.of();
	}

	@Override
	public Long addChallenger(ChallengerRequestDto request) {
		String password = request.getPassword();
		request.setPassword(passwordEncoder.encode(password));

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
		request.setPassword(passwordEncoder.encode(password));

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
