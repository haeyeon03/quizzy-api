package study.quizzy.service.impl;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
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
import study.quizzy.domain.dto.quiz.QuizRankResponseDto;
import study.quizzy.domain.entity.Challenger;

import study.quizzy.repository.ChallengerRepository;
import study.quizzy.service.ChallengerService;

@Service
public class ChallengerServiceImpl implements ChallengerService, UserDetailsService {
	@Autowired
	ChallengerRepository challengerRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Challenger challenger = challengerRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + username));

		return modelMapper.map(challenger, ChallengerAuthDto.class);
	}

	@Override
	public Long addChallenger(ChallengerRequestDto request) {
		if (challengerRepository.existsById(request.getChallengerId())) {
			// TODO. 해당 도전자 ID가 존재하면 예외 처리.
			System.out.println("TODO. 해당 도전자 ID가 존재하면 예외 처리.");
		}

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
	public List<ChallengerResponseDto> getChallengerList(ChallengerRequestDto request) {
		List<ChallengerResponseDto> dtoList = new ArrayList<>();

		List<Challenger> entityList = challengerRepository.findAll();

		for (Challenger c : entityList) {
			ChallengerResponseDto dto = modelMapper.map(c, ChallengerResponseDto.class);
			dtoList.add(dto);
		}

		return dtoList;
	}

	@Override
	public ChallengerResponseDto getChallenger(String challengerId) {
		Challenger challenger = challengerRepository.findById(challengerId)
				.orElseThrow(() -> new EntityNotFoundException("해당 도전자가 존재하지 않습니다."));

		return modelMapper.map(challenger, ChallengerResponseDto.class);
	}


	@Override
	public Long modifyChallenger(String challengerId, ChallengerRequestDto request) {
		Challenger challenger = challengerRepository.findById(challengerId)
				.orElseThrow(() -> new EntityNotFoundException("해당 도전자가 존재하지 않습니다."));

		applyUpdates(challenger, request);

		try {
			challengerRepository.save(challenger);
			return 1L;
		} catch (DataIntegrityViolationException e) {
			// 제약 조건 위반 처리
		} catch (Exception e) {
			// 기타 예외 처리
		}
		return 0L;
	}

	@Override
	public Long removeChallenger(String challengerId) {
		try {
			// 도전자 정보 존재 확인
			if (challengerRepository.existsById(challengerId)) {
				// 존재 시 삭제
				challengerRepository.deleteById(challengerId);
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
	public List<QuizRankResponseDto> getMyQuizList(String challengerId) {
		// 1. 도전자 조회
		Challenger challenger = challengerRepository.findById(challengerId)
				.orElseThrow(() -> new EntityNotFoundException("해당 도전자가 존재하지 않습니다."));

		// 2. 도전자가 푼 퀴즈 목록 가져오기 (Rank → Quiz)
		return challenger.getRanks().stream()
				.map(rank -> {
					QuizRankResponseDto dto = modelMapper.map(rank.getQuiz(), QuizRankResponseDto.class);
					dto.setScore(rank.getScore());
					dto.setDurationMs(rank.getDurationMs());
					dto.setUpdatedAt(rank.getUpdatedAt());
					return dto;
				})
				.toList();
	}

	private void applyUpdates(Challenger challenger, ChallengerRequestDto request) {
		if (request.getNickname() != null) {
			challenger.setNickname(request.getNickname());
		}

		if (request.getPassword() != null && !request.getPassword().isBlank()) {
			challenger.setPassword(passwordEncoder.encode(request.getPassword()));
		}

		if (request.getEmail() != null) {
			challenger.setEmail(request.getEmail());
		}

		if (request.getProvider() != null) {
			challenger.setProvider(request.getProvider());
		}
	}
}
