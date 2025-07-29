package study.quizzy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.quizzy.domain.entity.QuizQuestion;

import java.util.Optional;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    Optional<QuizQuestion> findByQuizQuestionIdAndQuiz_QuizId(Long quizQuestionId, Long quizId);
    
}
