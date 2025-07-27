package study.quizzy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import study.quizzy.domain.entity.QuizAnswer;

@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {

	List<QuizAnswer> findAllByQuizQuestion_QuizQuestionId(Long questionId);

}
