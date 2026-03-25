package com.trung.repository;

import com.trung.domain.entity.AssessmentRound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAssessmentRoundsRepository extends JpaRepository<AssessmentRound, Long> {
    Optional<AssessmentRound> findByRoundIdAndIsDeletedFalse(Long roundId);

    @Query("select a from AssessmentRound a join RoundCriteria r on a.roundId = r.round.roundId " +
            "join EvaluationCriteria e on e.criterionId = r.criterion.criterionId " +
            "where a.isDeleted = false and " +
            "(lower(a.roundName) like lower(concat('%', :keyword, '%')) or " +
            "lower(a.description) like lower(concat('%', :keyword, '%')) or " +
            "lower(a.phase.phaseName) like lower(concat('%', :keyword, '%')) " +
            "and e.isDeleted = false and r.isDeleted = false )")
    Page<AssessmentRound> findAllByKeyword(@Param("keyword") String keyword,
                                           Pageable pageable);

    Page<AssessmentRound> findAllByPhase_PhaseIdAndIsDeletedFalse(Long phaseId, Pageable pageable);

    @Query("select a from AssessmentRound a join RoundCriteria r on a.roundId = r.round.roundId " +
            "join EvaluationCriteria e on e.criterionId = r.criterion.criterionId " +
            "where a.isDeleted = false and " +
            "(lower(a.roundName) like lower(concat('%', :keyword, '%')) or " +
            "lower(a.description) like lower(concat('%', :keyword, '%')) or " +
            "lower(a.phase.phaseName) like lower(concat('%', :keyword, '%')) or " +
            "a.phase.phaseId = :phaseId " +
            "and e.isDeleted = false and r.isDeleted = false)")
    Page<AssessmentRound> findAllByKeywordAndPhaseId(@Param("keyword") String keyword,
                                                     @Param("phaseId") Long phaseId,
                                                     Pageable pageable);

    @Query("select a from AssessmentRound a join RoundCriteria r on a.roundId = r.round.roundId " +
            "join EvaluationCriteria e on e.criterionId = r.criterion.criterionId " +
            "where a.isDeleted = false and " +
            "e.isDeleted = false and r.isDeleted = false")
    Page<AssessmentRound> findAllByIsDeletedFalse(Pageable pageable);
}
