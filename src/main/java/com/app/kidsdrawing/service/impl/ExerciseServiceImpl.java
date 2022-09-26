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
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.ExerciseLevel;
import com.app.kidsdrawing.entity.ExerciseSubmission;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.ExerciseLevelRepository;
import com.app.kidsdrawing.repository.ExerciseRepository;
import com.app.kidsdrawing.repository.ExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.service.ExerciseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ExerciseServiceImpl implements ExerciseService{
    
    private final ExerciseRepository exerciseRepository;
    private final ExerciseSubmissionRepository exerciseSubmissionRepository;
    private final SectionRepository sectionRepository;
    private final ExerciseLevelRepository exerciseLevelRepository;
    private final ClassesRepository classRepository;

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
                .deadline(content.getDeadline())
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
    public ResponseEntity<Map<String, Object>> getAllExerciseByClassAndStudent(Long classes_id, Long student_id) {
        List<GetExerciseResponse> allExerciseNotSubmitResponses = new ArrayList<>();
        List<GetExerciseResponse> allExerciseSubmitedResponses = new ArrayList<>();

        Optional<Classes> classOpt = classRepository.findById(classes_id);
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        List<Exercise> listExercise = exerciseRepository.findAll();
        List<Exercise> listExerciseForClass = new ArrayList<>();

        listExercise.forEach(content -> {
            if (content.getSection().getClasses() == classes){
                listExerciseForClass.add(content);
            }
            
        });

        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findAll();
        List<Exercise> listExerciseSubmitedByStudentClass = new ArrayList<>();
        listExerciseSubmission.forEach(ele -> {
            if (listExerciseForClass.contains(ele.getExercise()) && ele.getStudent().getId() == student_id){
                listExerciseSubmitedByStudentClass.add(ele.getExercise());
                GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                    .id(ele.getExercise().getId())
                    .section_id(ele.getExercise().getSection().getId())
                    .level_id(ele.getExercise().getExerciseLevel().getId())
                    .level_name(ele.getExercise().getExerciseLevel().getWeight().toString())
                    .section_name(ele.getExercise().getSection().getName())
                    .name(ele.getExercise().getName())
                    .description(ele.getExercise().getDescription())
                    .deadline(ele.getExercise().getDeadline())
                    .create_time(ele.getExercise().getCreate_time())
                    .update_time(ele.getExercise().getUpdate_time())
                    .build();
                allExerciseSubmitedResponses.add(exerciseResponse);
            }
        });

        listExerciseForClass.forEach(ele -> {
            if (listExerciseSubmitedByStudentClass.contains(ele) == false){
                GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                    .id(ele.getId())
                    .section_id(ele.getSection().getId())
                    .level_id(ele.getExerciseLevel().getId())
                    .level_name(ele.getExerciseLevel().getWeight().toString())
                    .section_name(ele.getSection().getName())
                    .name(ele.getName())
                    .description(ele.getDescription())
                    .deadline(ele.getDeadline())
                    .create_time(ele.getCreate_time())
                    .update_time(ele.getUpdate_time())
                    .build();
                allExerciseNotSubmitResponses.add(exerciseResponse);
            }
        });


        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_submit", allExerciseNotSubmitResponses);
        response.put("exercise_submitted", allExerciseSubmitedResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseBySectionAndStudent(Long section_id, Long student_id) {
        List<GetExerciseResponse> allExerciseNotSubmitResponses = new ArrayList<>();
        List<GetExerciseResponse> allExerciseSubmitedResponses = new ArrayList<>();

        Optional<Section> sectionOpt = sectionRepository.findById(section_id);
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Section.not_found");
        });

        List<Exercise> listExercise = exerciseRepository.findAll();
        List<Exercise> listExerciseForSection = new ArrayList<>();

        listExercise.forEach(content -> {
            if (content.getSection() == section){
                listExerciseForSection.add(content);
            }
            
        });

        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findAll();
        
        List<Exercise> listExerciseSubmitedByStudentClass = new ArrayList<>();
        listExerciseSubmission.forEach(ele -> {
            
            if (listExerciseForSection.contains(ele.getExercise()) && ele.getStudent().getId() == student_id){
                listExerciseSubmitedByStudentClass.add(ele.getExercise());
                GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                    .id(ele.getExercise().getId())
                    .section_id(ele.getExercise().getSection().getId())
                    .level_id(ele.getExercise().getExerciseLevel().getId())
                    .level_name(ele.getExercise().getExerciseLevel().getWeight().toString())
                    .section_name(ele.getExercise().getSection().getName())
                    .name(ele.getExercise().getName())
                    .description(ele.getExercise().getDescription())
                    .deadline(ele.getExercise().getDeadline())
                    .create_time(ele.getExercise().getCreate_time())
                    .update_time(ele.getExercise().getUpdate_time())
                    .build();
                allExerciseSubmitedResponses.add(exerciseResponse);
            }
        });

        listExerciseForSection.forEach(ele -> {
            if (listExerciseSubmitedByStudentClass.contains(ele) == false){
                GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                    .id(ele.getId())
                    .section_id(ele.getSection().getId())
                    .level_id(ele.getExerciseLevel().getId())
                    .level_name(ele.getExerciseLevel().getWeight().toString())
                    .section_name(ele.getSection().getName())
                    .name(ele.getName())
                    .description(ele.getDescription())
                    .deadline(ele.getDeadline())
                    .create_time(ele.getCreate_time())
                    .update_time(ele.getUpdate_time())
                    .build();
                allExerciseNotSubmitResponses.add(exerciseResponse);
            }
        });


        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_submit", allExerciseNotSubmitResponses);
        response.put("exercise_submitted", allExerciseSubmitedResponses);
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
                    .deadline(content.getDeadline())
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
            .deadline(exercise.getDeadline())
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
                .deadline(createExerciseRequest.getDeadline())
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
        updatedExercise.setDeadline(createExerciseRequest.getDeadline());
        exerciseRepository.save(updatedExercise);

        return updatedExercise.getId();
    }
}
