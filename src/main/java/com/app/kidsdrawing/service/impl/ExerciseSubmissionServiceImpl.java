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

import com.app.kidsdrawing.dto.CreateExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetExerciseSubmissionResponse;
import com.app.kidsdrawing.entity.ExerciseSubmission;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserGradeExerciseSubmission;
import com.app.kidsdrawing.entity.Exercise;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ExerciseRepository;
import com.app.kidsdrawing.repository.ExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.UserGradeExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.ExerciseSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ExerciseSubmissionServiceImpl implements ExerciseSubmissionService {
    
    private final ExerciseSubmissionRepository exerciseSubmissionRepository;
    private final UserGradeExerciseSubmissionRepository userGradeExerciseSubmissionRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;


    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmission() {
        List<GetExerciseSubmissionResponse> allExerciseSubmissionResponses = new ArrayList<>();
        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findAll();
        listExerciseSubmission.forEach(content -> {
            GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                .id(content.getId())
                .exercise_id(content.getExercise().getId())
                .student_id(content.getStudent().getId())
                .image_url(content.getImage_url())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allExerciseSubmissionResponses.add(exerciseSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("ExerciseSubmission", allExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByStudentId(UUID id) {
        List<GetExerciseSubmissionResponse> allExerciseNotGradedSubmissionResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> allExerciseGradedSubmissionResponses = new ArrayList<>();
        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findAll();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        List<ExerciseSubmission> exersiceGraded = new ArrayList<>();
        listUserGradeExerciseSubmission.forEach(ele -> {
            exersiceGraded.add(ele.getExerciseSubmission());
        });
        listExerciseSubmission.forEach(content -> {
            if (content.getStudent().getId().compareTo(id) == 0){
                if (exersiceGraded.contains(content)) {
                    GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                        .id(content.getId())
                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                    allExerciseGradedSubmissionResponses.add(exerciseSubmissionResponse);
                }
                else {
                    GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                        .id(content.getId())
                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                        allExerciseNotGradedSubmissionResponses.add(exerciseSubmissionResponse);
                }
                
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", allExerciseNotGradedSubmissionResponses);
        response.put("exercise_graded", allExerciseGradedSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByExerciseId(UUID id) {
        List<GetExerciseSubmissionResponse> allExerciseNotGradedSubmissionResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> allExerciseGradedSubmissionResponses = new ArrayList<>();
        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findAll();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        List<ExerciseSubmission> exersiceGraded = new ArrayList<>();
        listUserGradeExerciseSubmission.forEach(ele -> {
            exersiceGraded.add(ele.getExerciseSubmission());
        });
        listExerciseSubmission.forEach(content -> {
            if (content.getExercise().getId().compareTo(id) == 0){
                if (exersiceGraded.contains(content)) {
                    GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                        .id(content.getId())
                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                    allExerciseGradedSubmissionResponses.add(exerciseSubmissionResponse);
                }
                else {
                    GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                        .id(content.getId())
                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                        allExerciseNotGradedSubmissionResponses.add(exerciseSubmissionResponse);
                }
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", allExerciseNotGradedSubmissionResponses);
        response.put("exercise_graded", allExerciseGradedSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassAndStudent(UUID class_id, UUID student_id) {
        List<GetExerciseSubmissionResponse> allExerciseNotGradedSubmissionResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> allExerciseGradedSubmissionResponses = new ArrayList<>();
        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findByStudentId2(student_id);
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        List<ExerciseSubmission> exersiceGraded = new ArrayList<>();
        listUserGradeExerciseSubmission.forEach(ele -> {
            exersiceGraded.add(ele.getExerciseSubmission());
        });
        listExerciseSubmission.forEach(content -> {
            if (content.getExercise().getSection().getClasses().getId().compareTo(class_id) == 0){
                if (exersiceGraded.contains(content)) {
                    GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                        .id(content.getId())
                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                    allExerciseGradedSubmissionResponses.add(exerciseSubmissionResponse);
                }
                else {
                    GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                        .id(content.getId())
                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                        allExerciseNotGradedSubmissionResponses.add(exerciseSubmissionResponse);
                }
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", allExerciseNotGradedSubmissionResponses);
        response.put("exercise_graded", allExerciseGradedSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassId(UUID id) {
        List<GetExerciseSubmissionResponse> allExerciseNotGradedSubmissionResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> allExerciseGradedSubmissionResponses = new ArrayList<>();
        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findAll();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        List<ExerciseSubmission> exersiceGraded = new ArrayList<>();
        listUserGradeExerciseSubmission.forEach(ele -> {
            exersiceGraded.add(ele.getExerciseSubmission());
        });
        listExerciseSubmission.forEach(content -> {
            if (content.getExercise().getSection().getClasses().getId().compareTo(id) == 0){
                if (exersiceGraded.contains(content)) {
                    GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                        .id(content.getId())
                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                    allExerciseGradedSubmissionResponses.add(exerciseSubmissionResponse);
                }
                else {
                    GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                        .id(content.getId())
                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                        allExerciseNotGradedSubmissionResponses.add(exerciseSubmissionResponse);
                }
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", allExerciseNotGradedSubmissionResponses);
        response.put("exercise_graded", allExerciseGradedSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetExerciseSubmissionResponse getExerciseSubmissionById(UUID id) {
        Optional<ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findById2(id);
        ExerciseSubmission exerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
        });

        return GetExerciseSubmissionResponse.builder()
            .id(exerciseSubmission.getId())
            .exercise_id(exerciseSubmission.getExercise().getId())
            .student_id(exerciseSubmission.getStudent().getId())
            .exercise_name(exerciseSubmission.getExercise().getName())
            .student_name(exerciseSubmission.getStudent().getFirstName() + " " + exerciseSubmission.getStudent().getLastName())
            .image_url(exerciseSubmission.getImage_url())
            .create_time(exerciseSubmission.getCreate_time())
            .update_time(exerciseSubmission.getUpdate_time())
            .build();
    }

    @Override
    public UUID createExerciseSubmission(CreateExerciseSubmissionRequest createExerciseSubmissionRequest) {

        Optional <User> studentOpt = userRepository.findById1(createExerciseSubmissionRequest.getStudent_id());
        User student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <Exercise> exerciseOpt = exerciseRepository.findById1(createExerciseSubmissionRequest.getExercise_id());
        Exercise exercise = exerciseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.exercise.not_found");
        });
        
        ExerciseSubmission savedExerciseSubmission = ExerciseSubmission.builder()
                .student(student)
                .exercise(exercise)
                .image_url(createExerciseSubmissionRequest.getImage_url())
                .build();
        exerciseSubmissionRepository.save(savedExerciseSubmission);

        return savedExerciseSubmission.getId();
    }

    @Override
    public UUID removeExerciseSubmissionById(UUID id) {
        Optional<ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findById1(id);
        exerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
        });

        exerciseSubmissionRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateExerciseSubmissionById(UUID id, CreateExerciseSubmissionRequest createExerciseSubmissionRequest) {
        Optional<ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findById1(id);
        ExerciseSubmission updatedExerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
        });

        Optional <User> studentOpt = userRepository.findById1(createExerciseSubmissionRequest.getStudent_id());
        User student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <Exercise> exerciseOpt = exerciseRepository.findById1(createExerciseSubmissionRequest.getExercise_id());
        Exercise exercise = exerciseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.exercise.not_found");
        });

        updatedExerciseSubmission.setExercise(exercise);
        updatedExerciseSubmission.setStudent(student);
        updatedExerciseSubmission.setImage_url(createExerciseSubmissionRequest.getImage_url());

        return updatedExerciseSubmission.getId();
    }
}
