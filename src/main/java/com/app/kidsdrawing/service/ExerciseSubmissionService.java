package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetExerciseSubmissionResponse;

public interface ExerciseSubmissionService {
    ResponseEntity<Map<String, Object>> getAllExerciseSubmission();
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByStudentId(Long id);
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByExerciseId(Long id);
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassId(Long id);
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByExerciseAndStudent(Long exercise_id, Long student_id);
    ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassAndStudent(Long class_id, Long student_id);
    GetExerciseSubmissionResponse getExerciseSubmissionById(Long id);
    Long createExerciseSubmission(CreateExerciseSubmissionRequest createExerciseSubmissionRequest);
    Long removeExerciseSubmissionById(Long id);
    Long updateExerciseSubmissionById(Long id, CreateExerciseSubmissionRequest createExerciseSubmissionRequest);
}
