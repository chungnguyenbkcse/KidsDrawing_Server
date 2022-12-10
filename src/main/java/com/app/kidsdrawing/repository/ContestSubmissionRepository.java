package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ContestSubmission;
import com.app.kidsdrawing.entity.ContestSubmissionKey;

@Repository
public interface ContestSubmissionRepository extends JpaRepository <ContestSubmission, Long>{
    
    @Query("SELECT count(c.id) = 1 FROM ContestSubmission c WHERE c.id = ?1 ")
    boolean existsById(Long id);

    @Query("SELECT DISTINCT c  FROM ContestSubmission c  JOIN FETCH c.contest co WHERE co.id = ?1  ORDER BY c.create_time")
    List<ContestSubmission> findByContestId1(Long id);

    @Query("SELECT DISTINCT c FROM ContestSubmission c  JOIN FETCH c.contest  co JOIN FETCH c.student st JOIN FETCH st.user WHERE co.id = ?1  ORDER BY c.create_time")
    List<ContestSubmission> findByContestId2(Long id);

    @Query("SELECT DISTINCT c FROM ContestSubmission c  JOIN FETCH c.contest  co WHERE co.id = ?1  ORDER BY c.create_time")
    List<ContestSubmission> findByContestId3(Long id);

    @Query("SELECT DISTINCT c FROM ContestSubmission c JOIN FETCH c.contest  co JOIN FETCH c.student st JOIN FETCH st.user WHERE co.id = ?1 AND st.id = ?2  ORDER BY c.create_time")
    Optional<ContestSubmission> findByContestAndStudent(Long contest_id, Long student_id);

    @Query("SELECT DISTINCT c FROM ContestSubmission c  JOIN FETCH c.student s JOIN FETCH s.user WHERE s.id = ?1  ORDER BY c.create_time")
    List<ContestSubmission> findByStudentId1(Long id);

    @Query("SELECT DISTINCT c FROM ContestSubmission c  JOIN FETCH c.student  s JOIN FETCH s.user  WHERE s.id = ?1  ORDER BY c.create_time")
    List<ContestSubmission> findByStudentId2(Long id);

    @Query("FROM ContestSubmission c  JOIN FETCH c.student st JOIN FETCH st.user JOIN FETCH c.contest ORDER BY c.create_time")
    List<ContestSubmission> findAll();

    @Query("FROM ContestSubmission c WHERE c.id = ?1 ")
    Optional<ContestSubmission> findById1(Long id);

    @Query("FROM ContestSubmission c JOIN FETCH c.student st JOIN FETCH st.user  JOIN FETCH c.contest WHERE c.id = ?1 ")
    Optional<ContestSubmission> findById2(Long id);

    @Modifying
    @Query("DELETE FROM ContestSubmission e WHERE e.id = ?1")
    void deleteById(ContestSubmissionKey id);
}