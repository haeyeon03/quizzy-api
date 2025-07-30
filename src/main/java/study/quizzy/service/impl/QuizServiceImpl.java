package study.quizzy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import study.quizzy.domain.dto.quiz.QuizAnswerRequestDto;
import study.quizzy.domain.dto.quiz.QuizAnswerResponseDto;
import study.quizzy.domain.dto.quiz.QuizQuestionRequestDto;
import study.quizzy.domain.dto.quiz.QuizQuestionResponseDto;
import study.quizzy.domain.dto.quiz.QuizRequestDto;
import study.quizzy.domain.dto.quiz.QuizResponseDto;
import study.quizzy.domain.dto.rank.AnswerSubmissionDto;
import study.quizzy.domain.dto.rank.RankRequestDto;
import study.quizzy.domain.dto.rank.RankResponseDto;
import study.quizzy.domain.entity.Quiz;
import study.quizzy.domain.entity.QuizAnswer;
import study.quizzy.domain.entity.QuizQuestion;
import study.quizzy.domain.entity.Rank;
import study.quizzy.domain.entity.id.RankId;
import study.quizzy.repository.QuizQuestionRepository;
import study.quizzy.repository.QuizRepository;
import study.quizzy.repository.RankRepository;
import study.quizzy.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	QuizRepository quizRepository;

	@Autowired
	QuizQuestionRepository quizQuestionRepository;

	@Autowired
	RankRepository rankRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<QuizResponseDto> getQuizList(QuizRequestDto request) {
		List<Quiz> entityList = new ArrayList<>();
		List<QuizResponseDto> dtoList = new ArrayList<>();

		String title = request.getTitle();
		if (title == null || title.isBlank()) {
			entityList = quizRepository.findAll();
		} else {
			entityList = quizRepository.findAllByTitle(title);
		}

		for (Quiz quiz : entityList) {
			QuizResponseDto dto = modelMapper.map(quiz, QuizResponseDto.class);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public QuizResponseDto getQuizById(Long quizId) {
		Optional<Quiz> quiz = quizRepository.findById(quizId);
		QuizResponseDto dto = modelMapper.map(quiz, QuizResponseDto.class);
		return dto;
	}

	@Override
	public List<QuizAnswerResponseDto> getAnswerListByQuizQuestionId(Long quizId, Long quizQuestionId) {
		QuizQuestion quizQuestion = quizQuestionRepository.findByQuizQuestionIdAndQuiz_QuizId(quizQuestionId, quizId)
				.orElseThrow(() -> new EntityNotFoundException("해당 퀴즈의 문제가 존재하지 않습니다."));

		return modelMapper.map(quizQuestion, QuizQuestionResponseDto.class).getQuizAnswerList();
	}

	@Override
	public List<RankResponseDto> getRankListByQuiz(Long quizId) {
		List<Rank> rankList = rankRepository.findAllById_QuizIdOrderByScoreDescDurationMsAsc(quizId);

		AtomicInteger counter = new AtomicInteger(1);
		return rankList.stream().map(rank -> {
			RankResponseDto dto = modelMapper.map(rank, RankResponseDto.class);
			dto.setRankNumber(counter.getAndIncrement());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public Long addQuiz(QuizRequestDto request) {
		Quiz quiz = modelMapper.map(request, Quiz.class);
		for (QuizQuestionRequestDto question : request.getQuizQuestionList()) {
			quiz.getQuizQuestionList().add(modelMapper.map(question, QuizQuestion.class));
			for (QuizAnswerRequestDto answer : question.getQuizAnswerList()) {
				
			}
		}

		try {
			Quiz saved = quizRepository.save(quiz);
			return 1L;
		} catch (DataIntegrityViolationException e) {
			// 제약 조건 위반 (중복, null 등)
			// 실패 처리
		} catch (Exception e) {
			// 기타 실패 처리
		}
		return 0L;
	}

	@Transactional
	@Override
	public Long modifyQuiz(QuizRequestDto request) {
		try {
			Quiz quiz = quizRepository.findById(request.getQuizId())
					.orElseThrow(() -> new RuntimeException("퀴즈를 찾을 수 없습니다."));

			quiz.setTitle(request.getTitle());
			quiz.setDescription(request.getDescription());
			quiz.setImageFile(request.getImageFile());

			// 기존 질문들을 ID 기반 Map으로 구성
			Map<Long, QuizQuestion> existingQuestions = quiz.getQuizQuestionList().stream()
					.filter(q -> q.getQuizQuestionId() != null)
					.collect(Collectors.toMap(QuizQuestion::getQuizQuestionId, q -> q));

			List<QuizQuestion> updatedQuestions = new ArrayList<>();

			for (QuizQuestionRequestDto qDto : request.getQuizQuestionList()) {
				QuizQuestion question = null;

				if (qDto.getQuizQuestionId() != null) {
					question = existingQuestions.remove(qDto.getQuizQuestionId());
				}

				if (question == null) {
					question = new QuizQuestion(qDto.getQuestion(), qDto.getImageFile(), quiz);
				} else {
					question.setQuestion(qDto.getQuestion());
					question.setImageFile(qDto.getImageFile());
				}

				// 기존 답변들을 ID 기반 Map으로 구성
				Map<Long, QuizAnswer> existingAnswers = question.getQuizAnswerList().stream()
						.filter(a -> a.getQuizAnswerId() != null)
						.collect(Collectors.toMap(QuizAnswer::getQuizAnswerId, a -> a));

				List<QuizAnswer> updatedAnswers = new ArrayList<>();

				for (QuizAnswerRequestDto aDto : qDto.getQuizAnswerList()) {
					QuizAnswer answer = null;

					if (aDto.getQuizAnswerId() != null) {
						answer = existingAnswers.remove(aDto.getQuizAnswerId());
					}

					if (answer == null) {
						answer = new QuizAnswer(aDto.getAnswer(), question);
					} else {
						answer.setAnswer(aDto.getAnswer());
					}

					updatedAnswers.add(answer);
				}

				// 기존 답변 리스트 교체
				question.getQuizAnswerList().clear();
				question.getQuizAnswerList().addAll(updatedAnswers);

				updatedQuestions.add(question);
			}

			// 기존 질문 리스트 교체
			quiz.getQuizQuestionList().clear();
			quiz.getQuizQuestionList().addAll(updatedQuestions);

			Quiz saved = quizRepository.save(quiz);
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
	public Long removeQuiz(QuizRequestDto request) {
		Long quizId = request.getQuizId();
		try {
			if (quizRepository.existsById(quizId)) {
				quizRepository.deleteById(quizId);
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
	@Transactional
	public Long addScore(RankRequestDto request) {
//		Long quizId = request.getQuizId();
//		String challengerId = request.getChallengerId();
//		int score = 0;
//
//		for (AnswerSubmissionDto submitAnswer : request.getInputAnswerList()) {
//			Long quizQuestionId = submitAnswer.getQuizQuestionId();
//			String inputAnswer = submitAnswer.getInputAnswer();
//
//			// 해당 문제의 정답 리스트 가져오기
//			QuizRequestDto quizRequestDto = new QuizRequestDto();
//			quizRequestDto.setQuizId(quizId);
//			quizRequestDto.setQuizQuestionId(quizQuestionId);
//
//			List<QuizAnswerResponseDto> answerList = getAnswerListByQuestion(quizRequestDto);
//			for (QuizAnswerResponseDto answerDto : answerList) {
//				if (inputAnswer.equals(answerDto.getAnswer())) {
//					score = score + 10;
//					break;
//				}
//			}
//		}
//
//		RankId rankId = new RankId(quizId, challengerId);
//		Rank rank = new Rank(rankId, score, request.getDurationMs());
//
//		try {
//			Rank saved = rankRepository.save(rank);
//			return 1L;
//		} catch (DataIntegrityViolationException e) {
//			// 제약 조건 위반 (중복, null 등)
//			// 실패 처리
//		} catch (Exception e) {
//			// 기타 실패 처리
//		}
		return 0L;
	}
}
