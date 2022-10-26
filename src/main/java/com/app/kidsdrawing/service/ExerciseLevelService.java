package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;


import com.app.kidsdrawing.dto.CreateExerciseLevelRequest;
import com.app.kidsdrawing.dto.GetExerciseLevelResponse;

public interface ExerciseLevelService {
    ResponseEntity<Map<String, Object>> getAllExerciseLevel();
    GetExerciseLevelResponse getExerciseLevelById(Long id);
    Long createExerciseLevel(CreateExerciseLevelRequest createExerciseLevelRequest);
    Long removeExerciseLevelById(Long id);
    Long updateExerciseLevelById(Long id, CreateExerciseLevelRequest createExerciseLevelRequest);
}
