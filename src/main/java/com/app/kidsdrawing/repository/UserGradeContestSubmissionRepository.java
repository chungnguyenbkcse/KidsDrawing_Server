package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserGradeContestSubmission;

@Repository
public interface UserGradeContestSubmissionRepository extends JpaRepository <UserGradeContestSubmission, Long>{
    
    @Query("SELECT e FROM UserGradeContestSubmission e JOIN FETCH e.teacher  JOIN FETCH e.contestSubmission ORDER BY e.time")
    List<UserGradeContestSubmission> findAll();

    @Query(
		value = "SELECT e FROM UserGradeContestSubmission e JOIN FETCH e.teacher  JOIN FETCH e.contestSubmission ORDER BY e.time",
		countQuery = "SELECT e FROM UserGradeContestSubmission e INNER JOIN e.teacher  INNER JOIN e.contestSubmission "
	)
    Page<UserGradeContestSubmission> findAll(Pageable pageable);

    @Query("SELECT DISTINCT e FROM UserGradeContestSubmission e JOIN FETCH e.teacher te JOIN FETCH e.contestSubmission cs WHERE te.id = ?1 AND cs.id = ?2")
    Optional<UserGradeContestSubmission> findByTeacherIdAndContestSubmissionId(Long teacher_id, Long contest_submission_id);

    @Query("SELECT DISTINCT e FROM UserGradeContestSubmission e JOIN FETCH e.teacher te WHERE te.id = ?1 ORDER BY e.time")
    List<UserGradeContestSubmission> findByTeacherId1(Long id);

    @Query("SELECT DISTINCT e FROM UserGradeContestSubmission e JOIN FETCH e.teacher te JOIN FETCH e.contestSubmission WHERE te.id = ?1 ORDER BY e.time")
    List<UserGradeContestSubmission> findByTeacherId2(Long id);

    @Query("SELECT DISTINCT e FROM UserGradeContestSubmission e JOIN FETCH e.contestSubmission cs WHERE cs.id = ?1 ORDER BY e.time")
    List<UserGradeContestSubmission> findByContestSubmissionId1(Long id);

    @Query("SELECT DISTINCT e FROM UserGradeContestSubmission e JOIN FETCH e.contestSubmission cs JOIN FETCH e.teacher  WHERE cs.id = ?1 ORDER BY e.time")
    List<UserGradeContestSubmission> findByContestSubmissionId2(Long id);
    
    boolean existsById(Long id);
    boolean existsByContestSubmissionId(Long id);

    @Query("DELETE FROM UserGradeContestSubmission e WHERE e.teacher = ?1 AND e.contestSubmission = ?2")
    void deleteById(Long teacher_id, Long contest_submission_id);

    @Query("SELECT DISTINCT e FROM UserGradeContestSubmission e JOIN FETCH e.contestSubmission cs JOIN FETCH cs.student st JOIN FETCH e.teacher  WHERE st.id = ?1 ORDER BY e.time")
    List<UserGradeContestSubmission> findByStudent(Long id);

    @Query("SELECT DISTINCT e FROM UserGradeContestSubmission e JOIN FETCH e.contestSubmission cs JOIN FETCH cs.contest co JOIN FETCH e.teacher  WHERE co.id = ?1 ORDER BY e.time")
    List<UserGradeContestSubmission> findByContest(Long id);

    @Query("SELECT DISTINCT e FROM UserGradeContestSubmission e JOIN FETCH e.teacher te JOIN FETCH e.contestSubmission cs JOIN FETCH cs.contest co  WHERE co.id = ?1 AND te.id = ?2 ORDER BY e.time")
    List<UserGradeContestSubmission> findByContestAndTeacher(Long contest_id, Long teacher_id);

    @Query("SELECT DISTINCT e FROM UserGradeContestSubmission e JOIN FETCH e.contestSubmission cs JOIN FETCH cs.contest co WHERE co.id = ?1 ORDER BY e.time")
    List<UserGradeContestSubmission> findTotalByContest(Long id);
}
