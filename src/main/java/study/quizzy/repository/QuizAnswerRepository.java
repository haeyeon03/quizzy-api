package study.quizzy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import study.quizzy.domain.entity.QuizAnswer;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
}
