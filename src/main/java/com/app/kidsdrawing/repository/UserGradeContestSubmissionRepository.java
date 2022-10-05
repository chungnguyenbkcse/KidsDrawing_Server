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
    
    @Query("SELECT e FROM UserGradeContestSubmission e JOIN FETCH e.teacher  JOIN FETCH e.contestSubmission ")
    List<UserGradeContestSubmission> findAll();

    @Query(
		value = "SELECT e FROM UserGradeContestSubmission e JOIN FETCH e.teacher  JOIN FETCH e.contestSubmission ",
		countQuery = "SELECT e FROM UserGradeContestSubmission e INNER JOIN e.teacher  INNER JOIN e.contestSubmission "
	)
    Page<UserGradeContestSubmission> findAll(Pageable pageable);

    @Query("FROM UserGradeContestSubmission e JOIN FETCH e.teacher te JOIN FETCH e.contestSubmission cs WHERE te.id = ?1 AND cs.id = ?2")
    Optional<UserGradeContestSubmission> findByTeacherIdAndContestSubmissionId(UUID teacher_id, UUID contest_submission_id);

    @Query("FROM UserGradeContestSubmission e JOIN FETCH e.teacher te WHERE te.id = :id")
    List<UserGradeContestSubmission> findByTeacherId1(UUID id);

    @Query("FROM UserGradeContestSubmission e JOIN FETCH e.teacher te JOIN FETCH e.contestSubmission WHERE te.id = :id")
    List<UserGradeContestSubmission> findByTeacherId2(UUID id);

    @Query("FROM UserGradeContestSubmission e JOIN FETCH e.contestSubmission cs WHERE cs.id = :id")
    List<UserGradeContestSubmission> findByContestSubmissionId1(UUID id);

    @Query("FROM UserGradeContestSubmission e JOIN FETCH e.contestSubmission cs JOIN FETCH e.teacher  WHERE cs.id = :id")
    List<UserGradeContestSubmission> findByContestSubmissionId2(UUID id);
    
    boolean existsById(UUID id);
    boolean existsByContestSubmissionId(UUID id);

    @Query("DELETE FROM UserGradeContestSubmission e WHERE e.teacher = ?1 AND e.contestSubmission = ?2")
    void deleteById(UUID teacher_id, UUID contest_submission_id);
}
