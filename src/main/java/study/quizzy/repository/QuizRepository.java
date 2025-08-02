package study.quizzy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import study.quizzy.domain.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
	Page<Quiz> findAllByTitle(String title, Pageable pagarable);
}
