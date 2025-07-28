package study.quizzy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import study.quizzy.domain.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
	List<Quiz> findAllByTitle(String title);
}
