package com.trung.repository;

import com.trung.domain.entity.InternshipAssignment;
import com.trung.domain.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipAssignmentRepository extends JpaRepository<InternshipAssignment, Long> {

    @Query("select ia.student from InternshipAssignment ia " +
            "where ia.mentor.mentorId = :mentorId")
    Page<Student> findStudentsByMentorId(@Param("mentorId") Long mentorId, Pageable pageable);
}
