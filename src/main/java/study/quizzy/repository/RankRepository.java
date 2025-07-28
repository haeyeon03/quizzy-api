package study.quizzy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.quizzy.domain.entity.Rank;
import study.quizzy.domain.entity.id.RankId;

import java.util.List;

public interface RankRepository extends JpaRepository<Rank, RankId> {
    List<Rank> findAllById_QuizIdOrderByScoreDescDurationMsAsc(Long quizId);

    List<Rank> findAllById_ChallengerIdOrderByUpdatedAtDesc(String challengerId);
}
