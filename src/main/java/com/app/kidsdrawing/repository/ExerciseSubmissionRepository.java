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
    

    @Query("SELECT e FROM ExerciseSubmission e JOIN FETCH e.exercise JOIN FETCH e.student ")
    List<ExerciseSubmission> findAll();

    @Query(
		value = "SELECT e FROM ExerciseSubmission e JOIN FETCH e.exercise JOIN FETCH e.student ",
		countQuery = "SELECT e FROM ExerciseSubmission e INNER JOIN e.exercise INNER JOIN e.student "
	)
    Page<ExerciseSubmission> findAll(Pageable pageable);

    @Query("FROM ExerciseSubmission e WHERE e.id = :id")
    Optional<ExerciseSubmission> findById1(UUID id);

    @Query("FROM ExerciseSubmission e JOIN FETCH e.exercise JOIN FETCH e.student WHERE e.id = :id")
    Optional<ExerciseSubmission> findById2(UUID id);
    
    @Query("SELECT COUNT(e.id) = 1 FROM ExerciseSubmission e WHERE e.id = :id")
    boolean existsById(UUID id);

    @Query("FROM ExerciseSubmission e JOIN FETCH e.student s WHERE s.id = :id")
    List<ExerciseSubmission> findByStudentId1(UUID id);

    @Query("FROM ExerciseSubmission e JOIN FETCH e.student s JOIN FETCH e.exercise WHERE s.id = :id")
    List<ExerciseSubmission> findByStudentId2(UUID id);

    @Query("FROM ExerciseSubmission e JOIN FETCH e.exercise ex WHERE ex.id = :id")
    List<ExerciseSubmission> findByExerciseId1(UUID id);

    @Query("FROM ExerciseSubmission e JOIN FETCH e.exercise ex JOIN FETCH e.student WHERE ex.id = :id")
    List<ExerciseSubmission> findByExerciseId2(UUID id);

    
    @Query("FROM ExerciseSubmission e JOIN FETCH e.exercise ex JOIN FETCH e.student s WHERE ex.id = ?1 AND s.id = ?2")
    List<ExerciseSubmission> findByExerciseIdAndStudentId(UUID exercise_id, UUID student_id);
}