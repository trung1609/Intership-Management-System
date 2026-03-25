package com.trung.repository;

import com.trung.domain.entity.User;
import com.trung.domain.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndIsDeletedFalseAndIsActiveTrue(String username);

    @Query("select case when count(u) > 0 then true else false end " +
            "from User u " +
            "where lower( replace(u.username, ' ', '')) = lower( replace(:username, ' ', '')) " +
            "and u.isDeleted = false " +
            "and u.isActive = true")
    boolean existsByUsername(String username);

    boolean existsByEmailAndIsDeletedFalseAndIsActiveTrue(String email);

    Optional<User> findByUserIdAndIsDeletedFalseAndIsActiveTrue(Long userId);

    Optional<User> findByUserIdAndIsDeletedFalse(Long userId);

    Page<User> findAllByIsDeletedFalseAndIsActiveTrue(Pageable pageable);

    Page<User> findByRoleAndIsDeletedFalseAndIsActiveTrue(Role role, Pageable pageable);

    @Query("select case when count(u) > 0 then true else false end " +
            "from User u " +
            "where lower( replace(u.username, ' ', '')) = lower( replace(:username, ' ', '')) " +
            "and u.isDeleted = false " +
            "and u.isActive = true "
            + "and u.userId <> :id")
    boolean existsByUsernameAndUserIdNot(String username, Long id);

    boolean existsByEmailAndIsDeletedFalseAndIsActiveTrueAndUserIdNot(String email, Long id);

}
