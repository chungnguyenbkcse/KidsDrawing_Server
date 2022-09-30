package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.ContestSubmission;

@Repository
public interface ContestSubmissionRepository extends JpaRepository <ContestSubmission, UUID>{
    boolean existsById(UUID id);
    List<ContestSubmission> findByContestId(UUID id);
}