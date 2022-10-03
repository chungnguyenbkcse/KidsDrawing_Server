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
    @Query("SELECT e FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission ORDER BY e.id")
    List<UserGradeExerciseSubmission> findAll();

    @Query(
		value = "SELECT e FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission ORDER BY e.id",
		countQuery = "SELECT e FROM UserGradeExerciseSubmission e INNER JOIN e.teacher  INNER JOIN e.exerciseSubmission ORDER BY e.id"
	)
    Page<UserGradeExerciseSubmission> findAll(Pageable pageable);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission WHERE e.id = :id")
    Optional<UserGradeExerciseSubmission> findById(UUID id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission WHERE e.teacher = :id")
    List<UserGradeExerciseSubmission> findByTeacherId(UUID id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission WHERE e.exerciseSubmission = :id")
    List<UserGradeExerciseSubmission> findByExerciseSubmissionId(UUID id);
    
    boolean existsById(UUID id);
    boolean existsByExerciseSubmissionId(UUID id);
    void deleteById(UUID id);
}
