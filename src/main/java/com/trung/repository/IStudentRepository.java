package com.trung.repository;

import com.trung.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Long> {

    boolean existsByStudentCode(String studentCode);
    boolean existsByStudentCodeAndStudentIdNot(String studentCode, Long studentId);
}
