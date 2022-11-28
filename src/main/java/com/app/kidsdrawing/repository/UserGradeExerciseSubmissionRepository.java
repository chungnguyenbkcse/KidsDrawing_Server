package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.app.kidsdrawing.entity.UserGradeExerciseSubmission;

@Repository
public interface UserGradeExerciseSubmissionRepository extends JpaRepository <UserGradeExerciseSubmission, Long>{
    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission es JOIN FETCH es.exercise JOIN  FETCH es.student ORDER BY e.time")
    List<UserGradeExerciseSubmission> findAll();

    @Query(
		value = "SELECT e FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission ORDER BY e.time",
		countQuery = "SELECT e FROM UserGradeExerciseSubmission e INNER JOIN e.teacher  INNER JOIN e.exerciseSubmission "
	)
    Page<UserGradeExerciseSubmission> findAll(Pageable pageable);

    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission es JOIN FETCH es.exercise JOIN  FETCH es.student WHERE e.teacher = ?1 AND e.exerciseSubmission = ?2")
    Optional<UserGradeExerciseSubmission> findByTeacherIdAndExerciseSubmissionId(Long teacher_id, Long exercise_submission_id);

    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher te WHERE te.id = ?1 ORDER BY e.time")
    List<UserGradeExerciseSubmission> findByTeacherId1(Long id);

    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher te JOIN FETCH e.exerciseSubmission es JOIN FETCH es.exercise JOIN  FETCH es.student WHERE te.id = ?1 ORDER BY e.time")
    List<UserGradeExerciseSubmission> findByTeacherId2(Long id);

    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH es.exercise JOIN  FETCH es.student WHERE es.id = ?1 ORDER BY e.time")
    List<UserGradeExerciseSubmission> findByExerciseSubmissionId1(Long id);

    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise JOIN  FETCH es.student WHERE es.id = ?1 ORDER BY e.time")
    List<UserGradeExerciseSubmission> findByExerciseSubmissionId2(Long id);

    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN FETCH ex.section se JOIN FETCH se.classes cl JOIN  FETCH es.student st WHERE st.id = ?1 AND cl.id = ?2 ORDER BY e.time")
    List<UserGradeExerciseSubmission> findByStudentAndClass(Long student_id, Long class_id);

    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN FETCH ex.section se JOIN  FETCH es.student st WHERE st.id = ?1 AND se.id = ?2 ORDER BY e.time")
    List<UserGradeExerciseSubmission> findByStudentAndSection(Long student_id, Long section_id);

    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH es.exercise ex JOIN FETCH ex.section se  WHERE se.id = ?1 ORDER BY e.time")
    List<UserGradeExerciseSubmission> findBySection(Long section_id);

    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN FETCH ex.section se JOIN FETCH se.classes cl JOIN  FETCH es.student st WHERE st.id = ?1 ORDER BY e.time")
    List<UserGradeExerciseSubmission> findByStudent1(Long student_id);


    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN  FETCH es.student st WHERE st.id = ?1 ORDER BY e.time")
    List<UserGradeExerciseSubmission> findByStudent(Long student_id);

    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN FETCH ex.section se JOIN FETCH se.classes cl JOIN  FETCH es.student st WHERE cl.id = ?1 ORDER BY e.time")
    List<UserGradeExerciseSubmission> findByClass(Long class_id);

    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN  FETCH es.student st WHERE ex.id = ?1 ORDER BY e.time")
    List<UserGradeExerciseSubmission> findByExercise(Long exercise_id);

    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN FETCH ex.section se JOIN FETCH se.classes cl JOIN  FETCH es.student st WHERE ex.id = ?1 AND cl.id = ?2 ORDER BY e.time")
    List<UserGradeExerciseSubmission> findByExerciseAndClass(Long exercise_id, Long class_id);

    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN FETCH ex.section se JOIN FETCH se.classes cl JOIN  FETCH es.student st WHERE ex.id = ?1 AND st.id = ?2 ORDER BY e.time")
    List<UserGradeExerciseSubmission> findByExerciseAndStudent(Long exercise_id, Long student_id);

    @Query("SELECT DISTINCT e FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN FETCH ex.section se JOIN FETCH se.classes cl JOIN  FETCH es.student st WHERE cl.id = ?1 AND st.id = ?2 ORDER BY e.time")
    List<UserGradeExerciseSubmission> findByClassAndStudent(Long class_id, Long student_id);
    
    boolean existsById(Long id);
    boolean existsByExerciseSubmissionId(Long id);


    @Query("DELETE FROM UserGradeExerciseSubmission e WHERE e.teacher = ?1 AND e.exerciseSubmission = ?2")
    void deleteById(Long teacher_id, Long exercise_submission_id);
}
