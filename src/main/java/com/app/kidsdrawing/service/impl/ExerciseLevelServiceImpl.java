package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateExerciseLevelRequest;
import com.app.kidsdrawing.dto.GetExerciseLevelResponse;
import com.app.kidsdrawing.entity.ExerciseLevel;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ExerciseLevelRepository;
import com.app.kidsdrawing.service.ExerciseLevelService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ExerciseLevelServiceImpl implements ExerciseLevelService{
    
    private final ExerciseLevelRepository exerciseLevelRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseLevel() {
        List<GetExerciseLevelResponse> allExerciseLevelResponses = new ArrayList<>();
        List<ExerciseLevel> listExerciseLevel = exerciseLevelRepository.findAll();
        listExerciseLevel.forEach(content -> {
            GetExerciseLevelResponse exerciseLevelResponse = GetExerciseLevelResponse.builder()
                .id(content.getId())
                .name(content.getName())
                .description(content.getDescription())
                .weight(content.getWeight())
                .build();
            allExerciseLevelResponses.add(exerciseLevelResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("ExerciseLevel", allExerciseLevelResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetExerciseLevelResponse getExerciseLevelById(UUID id) {
        Optional<ExerciseLevel> exerciseLevelOpt = exerciseLevelRepository.findById(id);
        ExerciseLevel exerciseLevel = exerciseLevelOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseLevel.not_found");
        });

        return GetExerciseLevelResponse.builder()
            .id(exerciseLevel.getId())
            .name(exerciseLevel.getName())
            .description(exerciseLevel.getDescription())
            .weight(exerciseLevel.getWeight())
            .build();
    }

    @Override
    public UUID createExerciseLevel(CreateExerciseLevelRequest createExerciseLevelRequest) {
        
        ExerciseLevel savedExerciseLevel = ExerciseLevel.builder()
                .name(createExerciseLevelRequest.getName())
                .description(createExerciseLevelRequest.getDescription())
                .weight(createExerciseLevelRequest.getWeight())
                .build();
        exerciseLevelRepository.save(savedExerciseLevel);

        return savedExerciseLevel.getId();
    }

    @Override
    public UUID removeExerciseLevelById(UUID id) {
        Optional<ExerciseLevel> exerciseLevelOpt = exerciseLevelRepository.findById(id);
        exerciseLevelOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseLevel.not_found");
        });

        exerciseLevelRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateExerciseLevelById(UUID id, CreateExerciseLevelRequest createExerciseLevelRequest) {
        Optional<ExerciseLevel> exerciseLevelOpt = exerciseLevelRepository.findById(id);
        ExerciseLevel updatedExerciseLevel = exerciseLevelOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseLevel.not_found");
        });

        updatedExerciseLevel.setName(createExerciseLevelRequest.getName());
        updatedExerciseLevel.setDescription(createExerciseLevelRequest.getDescription());
        updatedExerciseLevel.setWeight(createExerciseLevelRequest.getWeight());

        return updatedExerciseLevel.getId();
    }
}
