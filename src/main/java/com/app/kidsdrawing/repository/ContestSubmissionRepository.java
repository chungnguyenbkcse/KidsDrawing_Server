package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ContestSubmission;

@Repository
public interface ContestSubmissionRepository extends JpaRepository <ContestSubmission, Long>{
    
    @Query("SELECT count(c.id) = 1 FROM ContestSubmission c WHERE c.id = ?1")
    boolean existsById(Long id);

    @Query("SELECT DISTINCT c  FROM ContestSubmission c  JOIN FETCH c.contest co WHERE co.id = ?1 ORDER BY c.id")
    List<ContestSubmission> findByContestId1(Long id);

    @Query("SELECT DISTINCT c FROM ContestSubmission c JOIN FETCH c.userGradeContestSubmissions JOIN FETCH c.contest  co JOIN FETCH c.student WHERE co.id = ?1 ORDER BY c.id")
    List<ContestSubmission> findByContestId2(Long id);

    @Query("SELECT DISTINCT c FROM ContestSubmission c JOIN FETCH c.contest  co JOIN FETCH c.student st WHERE co.id = ?1 AND st.id = ?2 ORDER BY c.id")
    List<ContestSubmission> findByContestAndStudent(Long contest_id, Long student_id);

    @Query("SELECT DISTINCT c FROM ContestSubmission c  JOIN FETCH c.student s WHERE s.id = ?1 ORDER BY c.id")
    List<ContestSubmission> findByStudentId1(Long id);

    @Query("SELECT DISTINCT c FROM ContestSubmission c  JOIN FETCH c.student  s JOIN FETCH c.userGradeContestSubmissions WHERE s.id = ?1 ORDER BY c.id")
    List<ContestSubmission> findByStudentId2(Long id);

    @Query("FROM ContestSubmission c  JOIN FETCH c.student  JOIN FETCH c.contest ORDER BY c.id")
    List<ContestSubmission> findAll(Long id);

    @Query("FROM ContestSubmission c WHERE c.id = ?1")
    Optional<ContestSubmission> findById1(Long id);

    @Query("FROM ContestSubmission c JOIN FETCH c.student  JOIN FETCH c.contest WHERE c.id = ?1")
    Optional<ContestSubmission> findById2(Long id);
}