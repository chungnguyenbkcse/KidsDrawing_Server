package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import java.util.UUID;

import com.app.kidsdrawing.dto.CreateExerciseLevelRequest;
import com.app.kidsdrawing.dto.GetExerciseLevelResponse;

public interface ExerciseLevelService {
    ResponseEntity<Map<String, Object>> getAllExerciseLevel();
    GetExerciseLevelResponse getExerciseLevelById(UUID id);
    UUID createExerciseLevel(CreateExerciseLevelRequest createExerciseLevelRequest);
    UUID removeExerciseLevelById(UUID id);
    UUID updateExerciseLevelById(UUID id, CreateExerciseLevelRequest createExerciseLevelRequest);
}
