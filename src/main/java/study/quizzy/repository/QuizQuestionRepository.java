package study.quizzy.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import study.quizzy.domain.entity.QuizQuestion;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    Optional<QuizQuestion> findByQuizQuestionIdAndQuiz_QuizId(Long quizQuestionId, Long quizId);
    void deleteByQuiz_QuizId(Long quizId);

}
