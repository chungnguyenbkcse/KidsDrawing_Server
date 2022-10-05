package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.ContestSubmission;

@Repository
public interface ContestSubmissionRepository extends JpaRepository <ContestSubmission, UUID>{
    
    @Query("SELECT count(c.id) = 1 FROM ContestSubmission c WHERE c.id = :id")
    boolean existsById(UUID id);

    @Query("FROM ContestSubmission c  JOIN FETCH c.contest co WHERE co.id = :id")
    List<ContestSubmission> findByContestId1(UUID id);

    @Query("FROM ContestSubmission c JOIN FETCH c.userGradeContestSubmissions JOIN FETCH c.contest  co JOIN FETCH c.student WHERE co.id = :id")
    List<ContestSubmission> findByContestId2(UUID id);

    @Query("FROM ContestSubmission c  JOIN FETCH c.student s WHERE s.id = :id")
    List<ContestSubmission> findByStudentId1(UUID id);

    @Query("FROM ContestSubmission c  JOIN FETCH c.student  s JOIN FETCH c.userGradeContestSubmissions WHERE s.id = :id")
    List<ContestSubmission> findByStudentId2(UUID id);

    @Query("FROM ContestSubmission c  JOIN FETCH c.student  JOIN FETCH c.contest ")
    List<ContestSubmission> findAll(UUID id);

    @Query("FROM ContestSubmission c WHERE c.id = :id")
    Optional<ContestSubmission> findById1(UUID id);

    @Query("FROM ContestSubmission c JOIN FETCH c.student  JOIN FETCH c.contest WHERE c.id = :id")
    Optional<ContestSubmission> findById2(UUID id);
}