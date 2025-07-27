package study.quizzy.service;

import java.util.List;

import study.quizzy.domain.dto.quiz.QuizRequestDto;
import study.quizzy.domain.dto.quiz.QuizResponseDto;

public interface QuizService {

	List<QuizResponseDto> getQuizList(QuizRequestDto request);
	
}
