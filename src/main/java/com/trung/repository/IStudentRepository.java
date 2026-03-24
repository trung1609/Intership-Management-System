package com.trung.repository;

import com.trung.domain.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Long> {

    boolean existsByStudentCode(String studentCode);
    boolean existsByStudentCodeAndStudentIdNot(String studentCode, Long studentId);
}
