package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserGradeContestSubmission;

@Repository
public interface UserGradeContestSubmissionRepository extends JpaRepository <UserGradeContestSubmission, UUID>{
    
    @Query("SELECT e FROM UserGradeContestSubmission e JOIN FETCH e.teacher  JOIN FETCH e.contestSubmission ORDER BY e.id")
    List<UserGradeContestSubmission> findAll();

    @Query(
		value = "SELECT e FROM UserGradeContestSubmission e JOIN FETCH e.teacher  JOIN FETCH e.contestSubmission ORDER BY e.id",
		countQuery = "SELECT e FROM UserGradeContestSubmission e INNER JOIN e.teacher  INNER JOIN e.contestSubmission ORDER BY e.id"
	)
    Page<UserGradeContestSubmission> findAll(Pageable pageable);

    @Query("FROM UserGradeContestSubmission e JOIN FETCH e.teacher  JOIN FETCH e.contestSubmission WHERE e.id = :id")
    Optional<UserGradeContestSubmission> findById(UUID id);

    @Query("FROM UserGradeContestSubmission e JOIN FETCH e.teacher  JOIN FETCH e.contestSubmission WHERE e.teacher = :id")
    List<UserGradeContestSubmission> findByTeacherId(UUID id);

    @Query("FROM UserGradeContestSubmission e JOIN FETCH e.teacher  JOIN FETCH e.contestSubmission WHERE e.contestSubmission = :id")
    List<UserGradeContestSubmission> findByContestSubmissionId(UUID id);
    
    boolean existsById(UUID id);
    boolean existsByContestSubmissionId(UUID id);
    void deleteById(UUID id);
}
