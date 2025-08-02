package study.quizzy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.quizzy.domain.entity.Challenger;

public interface ChallengerRepository extends JpaRepository<Challenger, String> {
	
	
}
