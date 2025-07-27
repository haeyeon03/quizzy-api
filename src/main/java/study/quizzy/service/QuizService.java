package study.quizzy.service;

import java.util.List;

import study.quizzy.domain.dto.QuizRequestDto;
import study.quizzy.domain.dto.QuizResponseDto;

public interface QuizService {

	List<QuizResponseDto> getQuizList(QuizRequestDto request);
	
}
