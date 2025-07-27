package study.quizzy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import study.quizzy.domain.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
	public List<Quiz> findAllByTitle(String title);
}
