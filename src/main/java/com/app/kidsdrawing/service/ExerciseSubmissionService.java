package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetExerciseSubmissionResponse;

public interface ExerciseSubmissionService {
    ResponseEntity<Map<String, Object>> getAllExerciseSubmission();
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByStudentId(UUID id);
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByExerciseId(UUID id);
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassId(UUID id);
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassAndStudent(UUID class_id, UUID student_id);
    GetExerciseSubmissionResponse getExerciseSubmissionById(UUID id);
    UUID createExerciseSubmission(CreateExerciseSubmissionRequest createExerciseSubmissionRequest);
    UUID removeExerciseSubmissionById(UUID id);
    UUID updateExerciseSubmissionById(UUID id, CreateExerciseSubmissionRequest createExerciseSubmissionRequest);
}
