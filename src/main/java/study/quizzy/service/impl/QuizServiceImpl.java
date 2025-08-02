package study.quizzy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import study.quizzy.comm.response.PageResponse;
import study.quizzy.domain.dto.quiz.QuizAnswerRequestDto;
import study.quizzy.domain.dto.quiz.QuizAnswerResponseDto;
import study.quizzy.domain.dto.quiz.QuizQuestionRequestDto;
import study.quizzy.domain.dto.quiz.QuizQuestionResponseDto;
import study.quizzy.domain.dto.quiz.QuizRankResponseDto;
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
import study.quizzy.repository.QuizAnswerRepository;
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
	QuizAnswerRepository quizAnswerRepository;

	@Autowired
	RankRepository rankRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public PageResponse<QuizResponseDto> getQuizList(QuizRequestDto request) {
		if (request.getCurPage() > 0) {
			request.setCurPage(request.getCurPage() - 1);
		}

		Pageable pageable = PageRequest.of(request.getCurPage(), request.getPageSize(),
				Sort.by(Sort.Direction.DESC, "updatedAt"));

		Page<Quiz> entityList = null;

		String title = request.getTitle();
		if (title == null || title.isBlank()) {
			entityList = quizRepository.findAll(pageable);
		} else {
			entityList = quizRepository.findAllByTitle(title, pageable);
		}

		Page<QuizResponseDto> dtoList = entityList.map(e -> modelMapper.map(e, QuizResponseDto.class));

		return new PageResponse<QuizResponseDto>(dtoList);
	}

	@Override
	public QuizResponseDto getQuizById(Long quizId) {
		Optional<Quiz> quiz = quizRepository.findById(quizId);
		return modelMapper.map(quiz, QuizResponseDto.class);
	}

	@Override
	public List<QuizAnswerResponseDto> getAnswerListByQuizQuestionId(Long quizId, Long quizQuestionId) {
		QuizQuestion quizQuestion = quizQuestionRepository.findByQuizQuestionIdAndQuiz_QuizId(quizQuestionId, quizId)
				.orElseThrow(() -> new EntityNotFoundException("해당 퀴즈의 문제가 존재하지 않습니다."));

		return modelMapper.map(quizQuestion, QuizQuestionResponseDto.class).getQuizAnswerList();
	}

	@Override
	public Long addQuiz(QuizRequestDto request) {
		Quiz quiz = modelMapper.map(request, Quiz.class);

		// entity 양방향 연관 관계를 위해 부모 객체를 채워주어야 함.
		for (QuizQuestion question : quiz.getQuizQuestionList()) {
			question.setQuiz(quiz);
			for (QuizAnswer answer : question.getQuizAnswerList()) {
				answer.setQuizQuestion(question);
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

	@Override
	public Long modifyQuiz(Long quizId, QuizRequestDto request) {
		try {
			// 1. 기존 퀴즈 조회
			Quiz quiz = quizRepository.findById(quizId)
					.orElseThrow(() -> new EntityNotFoundException("해당 퀴즈가 존재하지 않습니다."));

			// 2. 퀴즈 기본 정보 수정
			quiz.setTitle(request.getTitle());
			quiz.setDescription(request.getDescription());
			quiz.setImageFile(request.getImageFile());

			// 3. 기존 문제들 준비
			List<QuizQuestion> oldQuestionList = quiz.getQuizQuestionList();

			// 4. 수정된 문제 리스트를 저장할 공간
			List<QuizQuestion> updatedQuestionList = new ArrayList<>();

			// 5. 수정할 문제 하나씩 보기
			for (QuizQuestionRequestDto questionDto : request.getQuizQuestionList()) {
				QuizQuestion question = null;

				// 6. 기존 문제에서 내가 요청한(수정한) 문제 찾기
				for (QuizQuestion oldQuestion : oldQuestionList) {
					if (questionDto.getQuizQuestionId() != null
							&& questionDto.getQuizQuestionId().equals(oldQuestion.getQuizQuestionId())) {
						question = oldQuestion;
						break;
					}
				}

				// 7-1. 내가 요청한(수정한) 문제 라면 문제 수정
				if (question != null) {
					question.setQuestion(questionDto.getQuestion());
					question.setImageFile(questionDto.getImageFile());
				} else {
					// 7-2. 요청한(수정한) 문제가 아닌 새로운 문제라면 새 문제 생성
					question = new QuizQuestion();
					question.setQuestion(questionDto.getQuestion());
					question.setImageFile(questionDto.getImageFile());
					// 어떤 퀴즈에 속하게 될지 알려줘야함
					question.setQuiz(quiz);
				}

				// 8. 답 수정
				List<QuizAnswer> updatedAnswerList = new ArrayList<>();
				List<QuizAnswer> oldAnswersList = question.getQuizAnswerList();

				// 9. 수정 할 답 하나씩 보기
				for (QuizAnswerRequestDto answerDto : questionDto.getQuizAnswerList()) {
					QuizAnswer answer = null;

					// 10. 기존 답에서 내가 요청한(수정한) 답 찾기
					for (QuizAnswer oldAnswer : oldAnswersList) {
						if (answerDto.getQuizAnswerId() != null
								&& answerDto.getQuizAnswerId().equals(oldAnswer.getQuizAnswerId())) {
							answer = oldAnswer;
							break;
						}
					}

					// 11-1. 내가 요청한(수정한) 답이라면 답 수정
					if (answer != null) {
						// 기존 답변 수정
						answer.setAnswer(answerDto.getAnswer());
					} else {
						// 11-2. 요청한(수정한) 답이 아닌 새로운 답이라면 새 답 생성
						answer = new QuizAnswer();
						answer.setAnswer(answerDto.getAnswer());
						answer.setQuizQuestion(question);
					}

					updatedAnswerList.add(answer);
				}

				// 기존 답변 리스트 교체
				oldAnswersList.clear();
				oldAnswersList.addAll(updatedAnswerList);

				// 수정된 문제 리스트에 추가
				updatedQuestionList.add(question);
			}

			// 12. 기존 문제 중에 내가 수정한 문제에 삭제가 된 문제는 제외하기
			List<QuizQuestion> removeList = new ArrayList<>();
			for (QuizQuestion oldQuestion : oldQuestionList) {
				boolean stillExists = false;
				for (QuizQuestionRequestDto qDto : request.getQuizQuestionList()) {
					if (qDto.getQuizQuestionId() != null
							&& qDto.getQuizQuestionId().equals(oldQuestion.getQuizQuestionId())) {
						stillExists = true;
						break;
					}
				}
				if (!stillExists) {
					removeList.add(oldQuestion);
				}
			}

			// 기존 문제 리스트에서 요청에 없는(삭제된) 문제들을 제거
			oldQuestionList.removeAll(removeList);

			// 13. 기존 퀴즈 문제 리스트를 완전히 새로 바꿔줌
			oldQuestionList.clear(); // 기존 문제들 전부 제거
			oldQuestionList.addAll(updatedQuestionList); // 새로 수정/추가한 문제들로 다시 채움

			// 저장

			quizRepository.save(quiz);
			return 1L;
		} catch (Exception e) {
			// 오류 처리
			e.fillInStackTrace();
			return 0L;
		}
	}

	@Override
	@Transactional
	public Long removeQuiz(Long quizId) {
		try {
			// 1. 퀴즈 ID 로 퀴즈 조회
			Quiz quiz = quizRepository.findById(quizId)
					.orElseThrow(() -> new EntityNotFoundException("해당 퀴즈가 존재하지 않습니다."));
			// 2. 퀴즈 ID 로 Rank 정보 삭제
			rankRepository.deleteAllById_QuizId(quiz.getQuizId());
			// 3. 퀴즤 삭제 (Question, Answer 연쇄 삭제)
			quizRepository.delete(quiz);
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
	public int addRank(Long quizId, String challengerId, RankRequestDto request) {
		int totalScore = 0;

		for (AnswerSubmissionDto submitAnswer : request.getInputAnswerList()) {
			Long quizQuestionId = submitAnswer.getQuizQuestionId();
			String inputAnswer = submitAnswer.getInputAnswer();

			// 해당 문제의 정답 리스트 가져오기
			QuizRequestDto quizRequestDto = new QuizRequestDto();
			quizRequestDto.setQuizId(quizId);
			quizRequestDto.setQuizQuestionId(quizQuestionId);

			List<QuizAnswerResponseDto> answerList = getAnswerListByQuizQuestionId(quizRequestDto.getQuizId(),
					quizRequestDto.getQuizQuestionId());
			for (QuizAnswerResponseDto answerDto : answerList) {
				if (inputAnswer.equals(answerDto.getAnswer())) {
					totalScore = totalScore + 10;
					break;
				}
			}
		}

		RankId rankId = new RankId(quizId, challengerId);
		Rank rank = new Rank(rankId, totalScore, request.getDurationMs());

		try {
			Rank saved = rankRepository.save(rank);
			return 1;
		} catch (DataIntegrityViolationException e) {
			// 제약 조건 위반 (중복, null 등)
			// 실패 처리
		} catch (Exception e) {
			// 기타 실패 처리
		}
		return 0;
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

}
