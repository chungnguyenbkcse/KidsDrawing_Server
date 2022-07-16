package com.app.kidsdrawing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.ExerciseSubmission;

@Repository
public interface ExerciseSubmissionRepository extends JpaRepository <ExerciseSubmission, Long>{
    boolean existsById(Long id);
}