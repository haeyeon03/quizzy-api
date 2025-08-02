package study.quizzy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import study.quizzy.domain.entity.Rank;
import study.quizzy.domain.entity.id.RankId;

public interface RankRepository extends JpaRepository<Rank, RankId> {
    List<Rank> findAllById_QuizIdOrderByScoreDescDurationMsAsc(Long quizId);

    Page<Rank> findAllById_ChallengerId(String challengerId, Pageable pageable);
    
    void deleteAllById_QuizId(Long quizId);

}
