package com.trung.repository;

import com.trung.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMentorRepository extends JpaRepository<Mentor, Long> {

}
