package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateExerciseRequest;
import com.app.kidsdrawing.dto.GetExerciseResponse;
import com.app.kidsdrawing.entity.Exercise;
import com.app.kidsdrawing.entity.ExerciseLevel;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ExerciseLevelRepository;
import com.app.kidsdrawing.repository.ExerciseRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.service.ExerciseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ExerciseServiceImpl implements ExerciseService{
    
    private final ExerciseRepository exerciseRepository;
    private final SectionRepository sectionRepository;
    private final ExerciseLevelRepository exerciseLevelRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllExercise() {
        List<GetExerciseResponse> allExerciseResponses = new ArrayList<>();
        List<Exercise> listExercise = exerciseRepository.findAll();
        listExercise.forEach(content -> {
            GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                .id(content.getId())
                .section_id(content.getSection().getId())
                .level_id(content.getExerciseLevel().getId())
                .name(content.getName())
                .description(content.getDescription())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allExerciseResponses.add(exerciseResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Exercise", allExerciseResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseBySectionId(Long id) {
        List<GetExerciseResponse> allExerciseResponses = new ArrayList<>();
        List<Exercise> listExercise = exerciseRepository.findAll();
        listExercise.forEach(content -> {
            if (content.getSection().getId() == id){
                GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                    .id(content.getId())
                    .section_id(content.getSection().getId())
                    .level_id(content.getExerciseLevel().getId())
                    .level_name(content.getExerciseLevel().getWeight().toString())
                    .section_name(content.getSection().getName())
                    .name(content.getName())
                    .description(content.getDescription())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allExerciseResponses.add(exerciseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Exercise", allExerciseResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetExerciseResponse getExerciseById(Long id) {
        Optional<Exercise> exerciseOpt = exerciseRepository.findById(id);
        Exercise exercise = exerciseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Exercise.not_found");
        });

        return GetExerciseResponse.builder()
            .id(exercise.getId())
            .section_id(exercise.getSection().getId())
            .level_id(exercise.getExerciseLevel().getId())
            .level_name(exercise.getExerciseLevel().getWeight().toString())
            .section_name(exercise.getSection().getName())
            .name(exercise.getName())
            .description(exercise.getDescription())
            .create_time(exercise.getCreate_time())
            .update_time(exercise.getUpdate_time())
            .build();
    }

    @Override
    public Long createExercise(CreateExerciseRequest createExerciseRequest) {

        Optional <Section> sectionOpt = sectionRepository.findById(createExerciseRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <ExerciseLevel> exerciseLevelOpt = exerciseLevelRepository.findById(createExerciseRequest.getLevel_id());
        ExerciseLevel exercise_level = exerciseLevelOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.exercise_level.not_found");
        });
        
        Exercise savedExercise = Exercise.builder()
                .section(section)
                .exerciseLevel(exercise_level)
                .name(createExerciseRequest.getName())
                .description(createExerciseRequest.getDescription())
                .build();
        exerciseRepository.save(savedExercise);

        return savedExercise.getId();
    }

    @Override
    public Long removeExerciseById(Long id) {
        Optional<Exercise> exerciseOpt = exerciseRepository.findById(id);
        exerciseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Exercise.not_found");
        });

        exerciseRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateExerciseById(Long id, CreateExerciseRequest createExerciseRequest) {
        Optional<Exercise> exerciseOpt = exerciseRepository.findById(id);
        Exercise updatedExercise = exerciseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Exercise.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById(createExerciseRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <ExerciseLevel> exerciseLevelOpt = exerciseLevelRepository.findById(createExerciseRequest.getLevel_id());
        ExerciseLevel exercise_level = exerciseLevelOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.exercise_level.not_found");
        });

        updatedExercise.setName(createExerciseRequest.getName());
        updatedExercise.setDescription(createExerciseRequest.getDescription());
        updatedExercise.setSection(section);
        updatedExercise.setExerciseLevel(exercise_level);

        return updatedExercise.getId();
    }
}
