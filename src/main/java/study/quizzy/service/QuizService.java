package study.quizzy.service;

import java.util.List;

import study.quizzy.domain.dto.quiz.QuizAnswerResponseDto;
import study.quizzy.domain.dto.quiz.QuizRequestDto;
import study.quizzy.domain.dto.quiz.QuizResponseDto;
import study.quizzy.domain.dto.rank.RankRequestDto;
import study.quizzy.domain.dto.rank.RankResponseDto;

public interface QuizService {

	List<QuizResponseDto> getQuizList(QuizRequestDto request);

	QuizResponseDto getQuizById(QuizRequestDto request);
	
	List<RankResponseDto> getRankListByQuiz(Long quizId);

	List<QuizAnswerResponseDto> getAnswerListByQuestion(QuizRequestDto request);

	Long removeQuiz(QuizRequestDto request);

	Long addQuiz(QuizRequestDto request);

	Long modifyQuiz(QuizRequestDto request);

	Long addScore(RankRequestDto request);
}
