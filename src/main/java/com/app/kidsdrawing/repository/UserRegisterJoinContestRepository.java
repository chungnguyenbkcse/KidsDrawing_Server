package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserRegisterJoinContest;

@Repository
public interface UserRegisterJoinContestRepository extends JpaRepository <UserRegisterJoinContest, Long>{
    Page<UserRegisterJoinContest> findAll(Pageable pageable);
    List<UserRegisterJoinContest> findByContestId(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);
}