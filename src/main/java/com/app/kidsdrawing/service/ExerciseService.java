package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateExerciseRequest;
import com.app.kidsdrawing.dto.GetExerciseResponse;

public interface ExerciseService {
    ResponseEntity<Map<String, Object>> getAllExercise();
    ResponseEntity<Map<String, Object>> getAllExerciseBySectionId(UUID id);
    ResponseEntity<Map<String, Object>> getAllExerciseByClassAndStudent(UUID classes_id, UUID student_id);
    ResponseEntity<Map<String, Object>> getAllExerciseBySectionAndStudent(UUID section_id, UUID student_id);
    GetExerciseResponse getExerciseById(UUID id);
    UUID createExercise(CreateExerciseRequest createExerciseRequest);
    UUID removeExerciseById(UUID id);
    UUID updateExerciseById(UUID id, CreateExerciseRequest createExerciseRequest);
}
