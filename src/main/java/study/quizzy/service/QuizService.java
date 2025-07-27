package study.quizzy.service;

import java.util.List;

import study.quizzy.domain.dto.quiz.QuizRequestDto;
import study.quizzy.domain.dto.quiz.QuizResponseDto;
import study.quizzy.domain.dto.rank.RankResponseDto;

public interface QuizService {

	List<QuizResponseDto> getQuizList(QuizRequestDto request);

	QuizResponseDto getQuizById(QuizRequestDto request);
	
	List<RankResponseDto> getRankListByQuiz(Long quizId);

}
