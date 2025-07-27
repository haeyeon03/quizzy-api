package study.quizzy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.quizzy.domain.dto.quiz.QuizAnswerRequestDto;
import study.quizzy.domain.dto.quiz.QuizAnswerResponseDto;
import study.quizzy.domain.dto.quiz.QuizRequestDto;
import study.quizzy.domain.dto.quiz.QuizResponseDto;
import study.quizzy.domain.dto.rank.RankResponseDto;
import study.quizzy.domain.entity.Quiz;
import study.quizzy.domain.entity.QuizAnswer;
import study.quizzy.domain.entity.Rank;
import study.quizzy.repository.QuizAnswerRepository;
import study.quizzy.repository.QuizRepository;
import study.quizzy.repository.RankRepository;
import study.quizzy.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	QuizRepository quizRepository;
	
	@Autowired
	QuizAnswerRepository quizAnswerRepository;

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
	public QuizResponseDto getQuizById(QuizRequestDto request) {
		Optional<Quiz> quiz = quizRepository.findById(request.getQuizId());
		QuizResponseDto dto = modelMapper.map(quiz, QuizResponseDto.class);
		return dto;
	}
	
	@Override
	public List<QuizAnswerResponseDto> checkAnswer(QuizAnswerRequestDto request) {
		List<QuizAnswer> answerList= quizAnswerRepository.findAllByQuizQuestion_QuizQuestionId(request.getQuestionId());
//		for(QuizAnswer answer: answerList) {
//			
//		}
		return null;
	}


	@Override
	public List<RankResponseDto> getRankListByQuiz(Long quizId) {
		List<Rank> rankList = rankRepository.findAllById_QuizIdOrderByScoreDescDurationMsAsc(quizId);
		return rankList.stream().map(rank -> modelMapper.map(rank, RankResponseDto.class)).toList();
	}



}
