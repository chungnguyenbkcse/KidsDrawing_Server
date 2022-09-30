package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.ExerciseSubmission;

@Repository
public interface ExerciseSubmissionRepository extends JpaRepository <ExerciseSubmission, UUID>{
    boolean existsById(UUID id);
    List<ExerciseSubmission> findByStudentId(UUID id);
}