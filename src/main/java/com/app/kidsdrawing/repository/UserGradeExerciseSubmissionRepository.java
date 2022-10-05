package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserGradeExerciseSubmission;

@Repository
public interface UserGradeExerciseSubmissionRepository extends JpaRepository <UserGradeExerciseSubmission, UUID>{
    @Query("SELECT e FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission es JOIN FETCH es.exercise JOIN  FETCH es.student")
    List<UserGradeExerciseSubmission> findAll();

    @Query(
		value = "SELECT e FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission ",
		countQuery = "SELECT e FROM UserGradeExerciseSubmission e INNER JOIN e.teacher  INNER JOIN e.exerciseSubmission "
	)
    Page<UserGradeExerciseSubmission> findAll(Pageable pageable);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission es JOIN FETCH es.exercise JOIN  FETCH es.student WHERE e.teacher = ?1 AND e.exerciseSubmission = ?2")
    Optional<UserGradeExerciseSubmission> findByTeacherIdAndExerciseSubmissionId(UUID teacher_id, UUID exercise_submission_id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher te WHERE te.id = :id")
    List<UserGradeExerciseSubmission> findByTeacherId1(UUID id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher te JOIN FETCH e.exerciseSubmission es JOIN FETCH es.exercise JOIN  FETCH es.student WHERE te.id = :id")
    List<UserGradeExerciseSubmission> findByTeacherId2(UUID id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH es.exercise JOIN  FETCH es.student WHERE es.id = :id")
    List<UserGradeExerciseSubmission> findByExerciseSubmissionId1(UUID id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise JOIN  FETCH es.student WHERE es.id = :id")
    List<UserGradeExerciseSubmission> findByExerciseSubmissionId2(UUID id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN FETCH ex.section se JOIN FETCH se.classes cl JOIN  FETCH es.student st WHERE st.id = ?1 AND cl.id = ?2")
    List<UserGradeExerciseSubmission> findByStudentAndClass(UUID student_id, UUID class_id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN  FETCH es.student st WHERE st.id = ?1")
    List<UserGradeExerciseSubmission> findByStudent(UUID student_id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN FETCH ex.section se JOIN FETCH se.classes cl JOIN  FETCH es.student st WHERE cl.id = ?1")
    List<UserGradeExerciseSubmission> findByClass(UUID class_id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN  FETCH es.student st WHERE ex.id = ?1")
    List<UserGradeExerciseSubmission> findByExercise(UUID exercise_id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN FETCH ex.section se JOIN FETCH se.classes cl JOIN  FETCH es.student st WHERE ex.id = ?1 AND cl.id = ?2")
    List<UserGradeExerciseSubmission> findByExerciseAndClass(UUID exercise_id, UUID class_id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN FETCH ex.section se JOIN FETCH se.classes cl JOIN  FETCH es.student st WHERE ex.id = ?1 AND st.id = ?2")
    List<UserGradeExerciseSubmission> findByExerciseAndStudent(UUID exercise_id, UUID student_id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission es JOIN FETCH e.teacher JOIN FETCH es.exercise ex JOIN FETCH ex.section se JOIN FETCH se.classes cl JOIN  FETCH es.student st WHERE cl.id = ?1 AND st.id = ?2")
    List<UserGradeExerciseSubmission> findByClassAndStudent(UUID class_id, UUID student_id);
    
    boolean existsById(UUID id);
    boolean existsByExerciseSubmissionId(UUID id);


    @Query("DELETE FROM UserGradeExerciseSubmission e WHERE e.teacher = ?1 AND e.exerciseSubmission = ?2")
    void deleteById(UUID teacher_id, UUID exercise_submission_id);
}
