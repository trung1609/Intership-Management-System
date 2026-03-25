package com.trung.repository;

import com.trung.domain.entity.AssessmentRound;
import com.trung.domain.entity.RoundCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoundCriteriaRepository extends JpaRepository<RoundCriteria, Long> {
    @Query("select rc from RoundCriteria rc where " +
            "rc.round.roundId = :roundId " +
            "and rc.criterion.criterionId = :criterionId " +
            "and rc.isDeleted = false " +
            "and rc.round.isDeleted = false " +
            "and rc.criterion.isDeleted = false")
    Optional<RoundCriteria> findByRoundIdAndCriterionId(@Param("roundId") Long roundId,@Param("criterionId") Long criterionId);

    @Query("select case when count(rc) > 0 then true else false end from RoundCriteria rc where " +
            "rc.round.roundId = :roundId " +
            "and rc.criterion.criterionId = :criterionId " +
            "and rc.isDeleted = false " +
            "and rc.round.isDeleted = false " +
            "and rc.criterion.isDeleted = false")
    boolean existsByRoundIdAndCriterionId(Long roundId, Long criterionId);

}
