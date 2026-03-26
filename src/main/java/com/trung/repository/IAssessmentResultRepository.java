package com.trung.repository;

import com.trung.domain.entity.AssessmentResult;
import com.trung.domain.entity.AssessmentRound;
import com.trung.domain.entity.EvaluationCriteria;
import com.trung.domain.entity.InternshipAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {
    boolean existsByAssignmentAndRoundAndCriterion(InternshipAssignment assignment, AssessmentRound round, EvaluationCriteria criterion);

    boolean existsByResultIdAndEvaluationId_UserId(Long resultId, Long userId);

    Page<AssessmentResult> findAllByEvaluationId_UserId(Long userId, Pageable pageable);

    Page<AssessmentResult> findAllByAssignment_AssignmentId(Long assignmentId, Pageable pageable);

    Page<AssessmentResult> findAllByAssignment_AssignmentIdAndEvaluationId_UserId(Long assignmentId, Long userId, Pageable pageable);

    Page<AssessmentResult> findAllByAssignment_Student_StudentId(Long studentId, Pageable pageable);

}
