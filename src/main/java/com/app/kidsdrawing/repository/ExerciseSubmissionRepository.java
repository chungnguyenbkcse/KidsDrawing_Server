package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.ExerciseSubmission;

@Repository
public interface ExerciseSubmissionRepository extends JpaRepository <ExerciseSubmission, UUID>{
    

    @Query("SELECT e FROM ExerciseSubmission e JOIN FETCH e.exercise JOIN FETCH e.student ORDER BY e.create_time")
    List<ExerciseSubmission> findAll();

    @Query(
		value = "SELECT e FROM ExerciseSubmission e JOIN FETCH e.exercise JOIN FETCH e.student ORDER BY e.create_time",
		countQuery = "SELECT e FROM ExerciseSubmission e INNER JOIN e.exercise INNER JOIN e.student ORDER BY e.create_time"
	)
    Page<ExerciseSubmission> findAll(Pageable pageable);

    @Query("FROM ExerciseSubmission e WHERE e.id = :id")
    Optional<ExerciseSubmission> findById1(UUID id);

    @Query("FROM ExerciseSubmission e JOIN FETCH e.exercise JOIN FETCH e.student WHERE e.id = :id")
    Optional<ExerciseSubmission> findById2(UUID id);
    
    @Query("SELECT COUNT(e.id) = 1 FROM ExerciseSubmission e WHERE e.id = :id")
    boolean existsById(UUID id);

    @Query("FROM ExerciseSubmission e JOIN FETCH e.student WHERE e.student = :id")
    List<ExerciseSubmission> findByStudentId1(UUID id);

    @Query("FROM ExerciseSubmission e JOIN FETCH e.student JOIN FETCH e.exercise WHERE e.student = :id")
    List<ExerciseSubmission> findByStudentId2(UUID id);

    @Query("FROM ExerciseSubmission e JOIN FETCH e.exercise WHERE e.exercise = :id")
    List<ExerciseSubmission> findByExerciseId1(UUID id);

    @Query("FROM ExerciseSubmission e JOIN FETCH e.exercise JOIN FETCH e.student WHERE e.exercise = :id")
    List<ExerciseSubmission> findByExerciseId2(UUID id);

    @Query("FROM ExerciseSubmission e JOIN FETCH e.exercise JOIN FETCH e.student WHERE e.exercise = ?1 AND e.student= ?2")
    List<ExerciseSubmission> findByExerciseIdAndStudentId(UUID exercise_id, UUID student_id);
}