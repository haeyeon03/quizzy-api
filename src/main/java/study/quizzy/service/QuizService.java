package study.quizzy.service;

import java.util.List;

import study.quizzy.domain.dto.quiz.QuizAnswerResponseDto;
import study.quizzy.domain.dto.quiz.QuizRequestDto;
import study.quizzy.domain.dto.quiz.QuizResponseDto;
import study.quizzy.domain.dto.rank.RankRequestDto;
import study.quizzy.domain.dto.rank.RankResponseDto;

public interface QuizService {

    List<QuizResponseDto> getQuizList(QuizRequestDto request);

    QuizResponseDto getQuizById(Long quizId);

    List<QuizAnswerResponseDto> getAnswerListByQuizQuestionId(Long quizId, Long quizQuestionId);

    Long addQuiz(QuizRequestDto request);

    Long modifyQuiz(Long quizId, QuizRequestDto request);

    Long removeQuiz(Long quizId);

    int addRank(Long quizId, String challengerId, RankRequestDto request);

    List<RankResponseDto> getRankListByQuiz(Long quizId);
}