package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ContestSubmission;

@Repository
public interface ContestSubmissionRepository extends JpaRepository <ContestSubmission, Long>{
    boolean existsById(Long id);
    List<ContestSubmission> findByContestId(Long id);
}