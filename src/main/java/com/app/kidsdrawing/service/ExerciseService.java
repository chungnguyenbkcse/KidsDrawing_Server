package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateExerciseRequest;
import com.app.kidsdrawing.dto.GetExerciseResponse;

public interface ExerciseService {
    ResponseEntity<Map<String, Object>> getAllExercise();
    ResponseEntity<Map<String, Object>> getAllExerciseBySectionId(Long id);
    ResponseEntity<Map<String, Object>> getAllExerciseForTeacherBySectionId(Long id);
    ResponseEntity<Map<String, Object>> getAllExerciseByClassAndStudent(Long classes_id, Long student_id);
    ResponseEntity<Map<String, Object>> getAllExerciseByClassAndParent(Long classes_id, Long parent_id);
    ResponseEntity<Map<String, Object>> getAllExerciseBySectionAndStudent(Long section_id, Long student_id);
    GetExerciseResponse getExerciseById(Long id);
    Long createExercise(CreateExerciseRequest createExerciseRequest);
    Long removeExerciseById(Long id);
    Long updateExerciseById(Long id, CreateExerciseRequest createExerciseRequest);
}
