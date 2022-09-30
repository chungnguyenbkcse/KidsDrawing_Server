package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserRegisterJoinContest;

@Repository
public interface UserRegisterJoinContestRepository extends JpaRepository <UserRegisterJoinContest, UUID>{
    Page<UserRegisterJoinContest> findAll(Pageable pageable);
    List<UserRegisterJoinContest> findByContestId(UUID id);
    List<UserRegisterJoinContest> findByStudentId(UUID id);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}