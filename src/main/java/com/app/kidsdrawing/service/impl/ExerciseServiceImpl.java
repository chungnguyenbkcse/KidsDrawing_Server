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

import com.app.kidsdrawing.dto.CreateExerciseRequest;
import com.app.kidsdrawing.dto.GetExerciseResponse;
import com.app.kidsdrawing.entity.Exercise;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.ExerciseLevel;
import com.app.kidsdrawing.entity.ExerciseSubmission;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.UserGradeExerciseSubmission;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.ExerciseLevelRepository;
import com.app.kidsdrawing.repository.ExerciseRepository;
import com.app.kidsdrawing.repository.ExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.repository.UserGradeExerciseSubmissionRepository;
import com.app.kidsdrawing.service.ExerciseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ExerciseServiceImpl implements ExerciseService{
    
    private final ExerciseRepository exerciseRepository;
    private final ExerciseSubmissionRepository exerciseSubmissionRepository;
    private final UserGradeExerciseSubmissionRepository userGradeExerciseSubmissionRepository;
    private final SectionRepository sectionRepository;
    private final ExerciseLevelRepository exerciseLevelRepository;
    private final ClassesRepository classRepository;
    private static Boolean checked = false;

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
    public ResponseEntity<Map<String, Object>> getAllExerciseByClassAndStudent(UUID classes_id, UUID student_id) {
        List<GetExerciseResponse> allExerciseNotSubmitResponses = new ArrayList<>();
        List<GetExerciseResponse> allExerciseSubmitedNotGradeResponses = new ArrayList<>();
        List<GetExerciseResponse> allExerciseSubmitedGradedResponses = new ArrayList<>();

        Optional<Classes> classOpt = classRepository.findById1(classes_id);
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
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmissions = userGradeExerciseSubmissionRepository.findAll();
        List<Exercise> listExerciseSubmitedByStudentClass = new ArrayList<>();
        listExerciseSubmission.forEach(ele -> {
            if (listExerciseForClass.contains(ele.getExercise()) && ele.getStudent().getId().compareTo(student_id) == 0){
                listExerciseSubmitedByStudentClass.add(ele.getExercise());
                checked = false;
                listUserGradeExerciseSubmissions.forEach(user_grade_exercise_submission -> {
                    if (user_grade_exercise_submission.getExerciseSubmission() == ele) {
                        checked = true;
                        GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                            .id(ele.getExercise().getId())
                            .section_id(ele.getExercise().getSection().getId())
                            .teacher_name(ele.getExercise().getSection().getClasses().getUserRegisterTeachSemester().getTeacher().getFirstName() + " " + ele.getExercise().getSection().getClasses().getUserRegisterTeachSemester().getTeacher().getLastName())
                            .time_submit(ele.getUpdate_time())
                            .level_id(ele.getExercise().getExerciseLevel().getId())
                            .level_name(ele.getExercise().getExerciseLevel().getWeight().toString())
                            .section_name(ele.getExercise().getSection().getName())
                            .name(ele.getExercise().getName())
                            .exercise_submission_id(ele.getId())
                            .description(ele.getExercise().getDescription())
                            .deadline(ele.getExercise().getDeadline())
                            .create_time(ele.getExercise().getCreate_time())
                            .update_time(ele.getExercise().getUpdate_time())
                            .build();
                        allExerciseSubmitedGradedResponses.add(exerciseResponse);
                    }
                });

                if (checked == false) {
                    GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                        .id(ele.getExercise().getId())
                        .section_id(ele.getExercise().getSection().getId())
                        .level_id(ele.getExercise().getExerciseLevel().getId())
                        .level_name(ele.getExercise().getExerciseLevel().getWeight().toString())
                        .section_name(ele.getExercise().getSection().getName())
                        .teacher_name(ele.getExercise().getSection().getClasses().getUserRegisterTeachSemester().getTeacher().getFirstName() + " " + ele.getExercise().getSection().getClasses().getUserRegisterTeachSemester().getTeacher().getLastName())
                        .time_submit(ele.getUpdate_time())
                        .name(ele.getExercise().getName())
                        .exercise_submission_id(ele.getId())
                        .description(ele.getExercise().getDescription())
                        .deadline(ele.getExercise().getDeadline())
                        .create_time(ele.getExercise().getCreate_time())
                        .update_time(ele.getExercise().getUpdate_time())
                        .build();
                    allExerciseSubmitedNotGradeResponses.add(exerciseResponse);
                }
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
                    .teacher_name(ele.getSection().getClasses().getUserRegisterTeachSemester().getTeacher().getFirstName() + " " + ele.getSection().getClasses().getUserRegisterTeachSemester().getTeacher().getLastName())
                    .time_submit(ele.getUpdate_time())
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
        response.put("exercise_submitted_not_grade", allExerciseSubmitedNotGradeResponses);
        response.put("exercise_submitted_graded", allExerciseSubmitedGradedResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseBySectionAndStudent(UUID section_id, UUID student_id) {
        List<GetExerciseResponse> allExerciseNotSubmitResponses = new ArrayList<>();
        List<GetExerciseResponse> allExerciseSubmitedNotGradeResponses = new ArrayList<>();
        List<GetExerciseResponse> allExerciseSubmitedGradedResponses = new ArrayList<>();

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
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmissions = userGradeExerciseSubmissionRepository.findAll();

        List<Exercise> listExerciseSubmitedByStudentClass = new ArrayList<>();
        listExerciseSubmission.forEach(ele -> {
            
            if (listExerciseForSection.contains(ele.getExercise()) && ele.getStudent().getId().compareTo(student_id) == 0){
                listExerciseSubmitedByStudentClass.add(ele.getExercise());
                checked = false;
                listUserGradeExerciseSubmissions.forEach(user_grade_exercise_submission -> {
                    if (user_grade_exercise_submission.getExerciseSubmission() == ele) {
                        checked = true;
                        GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                            .id(ele.getExercise().getId())
                            .section_id(ele.getExercise().getSection().getId())
                            .teacher_name(ele.getExercise().getSection().getClasses().getUserRegisterTeachSemester().getTeacher().getFirstName() + " " + ele.getExercise().getSection().getClasses().getUserRegisterTeachSemester().getTeacher().getLastName())
                            .time_submit(ele.getUpdate_time())
                            .exercise_submission_id(ele.getId())
                            .level_id(ele.getExercise().getExerciseLevel().getId())
                            .level_name(ele.getExercise().getExerciseLevel().getWeight().toString())
                            .section_name(ele.getExercise().getSection().getName())
                            .name(ele.getExercise().getName())
                            .description(ele.getExercise().getDescription())
                            .deadline(ele.getExercise().getDeadline())
                            .create_time(ele.getExercise().getCreate_time())
                            .update_time(ele.getExercise().getUpdate_time())
                            .build();
                        allExerciseSubmitedGradedResponses.add(exerciseResponse);
                    }
                });

                if (checked == false) {
                    GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                        .id(ele.getExercise().getId())
                        .section_id(ele.getExercise().getSection().getId())
                        .level_id(ele.getExercise().getExerciseLevel().getId())
                        .level_name(ele.getExercise().getExerciseLevel().getWeight().toString())
                        .section_name(ele.getExercise().getSection().getName())
                        .teacher_name(ele.getExercise().getSection().getClasses().getUserRegisterTeachSemester().getTeacher().getFirstName() + " " + ele.getExercise().getSection().getClasses().getUserRegisterTeachSemester().getTeacher().getLastName())
                        .time_submit(ele.getUpdate_time())
                        .exercise_submission_id(ele.getId())
                        .name(ele.getExercise().getName())
                        .description(ele.getExercise().getDescription())
                        .deadline(ele.getExercise().getDeadline())
                        .create_time(ele.getExercise().getCreate_time())
                        .update_time(ele.getExercise().getUpdate_time())
                        .build();
                    allExerciseSubmitedNotGradeResponses.add(exerciseResponse);
                }
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
                    .teacher_name(ele.getSection().getClasses().getUserRegisterTeachSemester().getTeacher().getFirstName() + " " + ele.getSection().getClasses().getUserRegisterTeachSemester().getTeacher().getLastName())
                    .time_submit(ele.getUpdate_time())
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
        response.put("exercise_submitted_not_grade", allExerciseSubmitedNotGradeResponses);
        response.put("exercise_submitted_graded", allExerciseSubmitedGradedResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseBySectionId(UUID id) {
        List<GetExerciseResponse> allExerciseResponses = new ArrayList<>();
        List<Exercise> listExercise = exerciseRepository.findAll();
        listExercise.forEach(content -> {
            if (content.getSection().getId().compareTo(id) == 0){
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
    public GetExerciseResponse getExerciseById(UUID id) {
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
    public UUID createExercise(CreateExerciseRequest createExerciseRequest) {

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
    public UUID removeExerciseById(UUID id) {
        Optional<Exercise> exerciseOpt = exerciseRepository.findById(id);
        exerciseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Exercise.not_found");
        });

        exerciseRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateExerciseById(UUID id, CreateExerciseRequest createExerciseRequest) {
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
