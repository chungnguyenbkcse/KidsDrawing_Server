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
import com.app.kidsdrawing.dto.GetExerciseParentResponse;
import com.app.kidsdrawing.dto.GetExerciseResponse;
import com.app.kidsdrawing.dto.GetExerciseTeacherResponse;
import com.app.kidsdrawing.entity.Exercise;
import com.app.kidsdrawing.entity.ExerciseSubmission;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ExerciseRepository;
import com.app.kidsdrawing.repository.ExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.ExerciseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ExerciseServiceImpl implements ExerciseService{
    
    private final ExerciseRepository exerciseRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final ExerciseSubmissionRepository exerciseSubmissionRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllExercise() {
        List<GetExerciseResponse> allExerciseResponses = new ArrayList<>();
        List<Exercise> listExercise = exerciseRepository.findAll();
        listExercise.forEach(content -> {
            GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                .id(content.getId())
                .section_id(content.getSection().getId())
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
    public ResponseEntity<Map<String, Object>> getAllExerciseByClassAndParent(Long classes_id, Long parent_id) {
        List<GetExerciseParentResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseParentResponse> exerciseSubmittedResponses = new ArrayList<>();
        List<User> listChilds = userRepository.findByParentId(parent_id);
        listChilds.forEach(student -> {
            List<Exercise> allExerciseByClassAndStudent = exerciseRepository
                    .findAllExerciseByClassAndStudent(classes_id, student.getId());
            List<ExerciseSubmission> exerciseSubmissions = exerciseSubmissionRepository
                    .findAllExerciseSubmissionByClassAndStudent(classes_id, student.getId());

            System.out.print(allExerciseByClassAndStudent.size());
            List<Exercise> allExerciseSubmiss = new ArrayList<>();
            exerciseSubmissions.forEach(ele -> {
                allExerciseSubmiss.add(ele.getExercise());
            });

            allExerciseByClassAndStudent.forEach(ele -> {
                if (allExerciseSubmiss.contains(ele) == false) {
                    GetExerciseParentResponse exerciseResponse = GetExerciseParentResponse.builder()
                            .id(ele.getId())
                            .section_id(ele.getSection().getId())
                            .section_name(ele.getSection().getName())
                            .name(ele.getName())
                            .student_id(student.getId())
                            .student_name(student.getUsername() + student.getFirstName() + " " + student.getLastName())
                            .exercise_submission_id(ele.getId())
                            .description(ele.getDescription())
                            .deadline(ele.getDeadline())
                            .create_time(ele.getCreate_time())
                            .update_time(ele.getUpdate_time())
                            .build();
                    exerciseResponses.add(exerciseResponse);
                } else {
                    GetExerciseParentResponse exerciseResponse = GetExerciseParentResponse.builder()
                            .id(ele.getId())
                            .section_id(ele.getSection().getId())
                            .section_name(ele.getSection().getName())
                            .name(ele.getName())
                            .student_id(student.getId())
                            .student_name(student.getUsername() + " - " + student.getFirstName() + " " + student.getLastName())
                            .time_submit(ele.getUpdate_time())
                            .exercise_submission_id(ele.getId())
                            .description(ele.getDescription())
                            .deadline(ele.getDeadline())
                            .create_time(ele.getCreate_time())
                            .update_time(ele.getUpdate_time())
                            .build();
                    exerciseSubmittedResponses.add(exerciseResponse);
                }
            });
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_submit", exerciseResponses);
        response.put("exercise_submitted", exerciseSubmittedResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseByClassAndStudent(Long classes_id, Long student_id) {
        List<GetExerciseResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseResponse> exerciseSubmittedResponses = new ArrayList<>();

        List<Exercise> allExerciseByClassAndStudent = exerciseRepository.findAllExerciseByClassAndStudent(classes_id, student_id);
        List<ExerciseSubmission> exerciseSubmissions = exerciseSubmissionRepository.findAllExerciseSubmissionByClassAndStudent(classes_id, student_id);

        System.out.print(allExerciseByClassAndStudent.size());
        List<Exercise> allExerciseSubmiss = new ArrayList<>();
        exerciseSubmissions.forEach(ele -> {
            allExerciseSubmiss.add(ele.getExercise());
        });

        allExerciseByClassAndStudent.forEach(ele -> {
            if (allExerciseSubmiss.contains(ele) == false){
                GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                    .id(ele.getId())
                    .section_id(ele.getSection().getId())
                    .section_name(ele.getSection().getName())
                    .name(ele.getName())
                    .exercise_submission_id(ele.getId())
                    .description(ele.getDescription())
                    .deadline(ele.getDeadline())
                    .create_time(ele.getCreate_time())
                    .update_time(ele.getUpdate_time())
                    .build();
                exerciseResponses.add(exerciseResponse);
            }
            else {
                GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                    .id(ele.getId())
                    .section_id(ele.getSection().getId())
                    .section_name(ele.getSection().getName())
                    .name(ele.getName())
                    .time_submit(ele.getUpdate_time())
                    .exercise_submission_id(ele.getId())
                    .description(ele.getDescription())
                    .deadline(ele.getDeadline())
                    .create_time(ele.getCreate_time())
                    .update_time(ele.getUpdate_time())
                    .build();
                exerciseSubmittedResponses.add(exerciseResponse);
            }
        });

        
        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_submit", exerciseResponses);
        response.put("exercise_submitted", exerciseSubmittedResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseBySectionAndParent(Long section_id, Long parent_id, int total_child) {
        List<GetExerciseResponse> exerciseNotSubmitResponses = new ArrayList<>();
        List<GetExerciseResponse> exerciseSubmittedResponses = new ArrayList<>();
        List<GetExerciseResponse> exerciseEleSubmittedResponses = new ArrayList<>();

        List<Exercise> listExerciseForSection = exerciseRepository.findBySectionId1(section_id);
        List<ExerciseSubmission> exerciseSubmissions = exerciseSubmissionRepository.findAllExerciseSubmissionBySectionAndParent(section_id, parent_id);
        System.out.print(exerciseSubmissions.size());
        List<Exercise> allExerciseSubmiss = new ArrayList<>();
        exerciseSubmissions.forEach(ele -> {
            allExerciseSubmiss.add(ele.getExercise());
        });

        listExerciseForSection.forEach(ele -> {
            int count = (int) allExerciseSubmiss.stream().filter(elex -> elex.getId() == ele.getId()).count();
            if (count == 0) {
                GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                    .id(ele.getId())
                    .section_id(ele.getSection().getId())
                    .section_name(ele.getSection().getName())
                    .name(ele.getName())
                    .exercise_submission_id(ele.getId())
                    .description(ele.getDescription())
                    .deadline(ele.getDeadline())
                    .create_time(ele.getCreate_time())
                    .update_time(ele.getUpdate_time())
                    .build();
                exerciseNotSubmitResponses.add(exerciseResponse);
            }
            else {
                if (count == total_child) {
                    GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                        .id(ele.getId())
                        .section_id(ele.getSection().getId())
                        .section_name(ele.getSection().getName())
                        .name(ele.getName())
                        .exercise_submission_id(ele.getId())
                        .description(ele.getDescription())
                        .deadline(ele.getDeadline())
                        .create_time(ele.getCreate_time())
                        .update_time(ele.getUpdate_time())
                        .build();
                    exerciseSubmittedResponses.add(exerciseResponse);
                }
                else {
                    List<String> student_names = new ArrayList<>();
                    List<Long> student_ids = new ArrayList<>();
                    exerciseSubmissions.stream().filter(elex -> elex.getExercise().getId() == ele.getId()).forEach(eley -> {
                        student_names.add(eley.getStudent().getUsername() + " - " + eley.getStudent().getFirstName() + " " + eley.getStudent().getLastName());
                        student_ids.add(eley.getStudent().getId());
                    });
                    GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                        .id(ele.getId())
                        .section_id(ele.getSection().getId())
                        .section_name(ele.getSection().getName())
                        .name(ele.getName())
                        .student_ids(student_ids)
                        .student_names(student_names)
                        .exercise_submission_id(ele.getId())
                        .description(ele.getDescription())
                        .deadline(ele.getDeadline())
                        .create_time(ele.getCreate_time())
                        .update_time(ele.getUpdate_time())
                        .build();
                    exerciseEleSubmittedResponses.add(exerciseResponse);
                }
            }
        });

        
        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_submit", exerciseNotSubmitResponses);
        response.put("exercise_submitted_full", exerciseSubmittedResponses);
        response.put("exercise_submitted_not_full", exerciseEleSubmittedResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseBySectionAndStudent(Long section_id, Long student_id) {
        List<GetExerciseResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseResponse> exerciseSubmittedResponses = new ArrayList<>();

        List<Exercise> listExerciseForSectionAndStuent = exerciseRepository.findAllExerciseBySectionAndStudent(section_id, student_id);
        List<ExerciseSubmission> exerciseSubmissions = exerciseSubmissionRepository.findAllExerciseSubmissionBySectionAndStudent(section_id, student_id);

        List<Exercise> allExerciseSubmiss = new ArrayList<>();
        exerciseSubmissions.forEach(ele -> {
            allExerciseSubmiss.add(ele.getExercise());
        });

        listExerciseForSectionAndStuent.forEach(ele -> {
            if (allExerciseSubmiss.contains(ele) == false){
                GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                    .id(ele.getId())
                    .section_id(ele.getSection().getId())
                    .section_name(ele.getSection().getName())
                    .name(ele.getName())
                    .exercise_submission_id(ele.getId())
                    .description(ele.getDescription())
                    .deadline(ele.getDeadline())
                    .create_time(ele.getCreate_time())
                    .update_time(ele.getUpdate_time())
                    .build();
                exerciseResponses.add(exerciseResponse);
            }
            else {
                GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                    .id(ele.getId())
                    .section_id(ele.getSection().getId())
                    .section_name(ele.getSection().getName())
                    .name(ele.getName())
                    .exercise_submission_id(ele.getId())
                    .description(ele.getDescription())
                    .deadline(ele.getDeadline())
                    .create_time(ele.getCreate_time())
                    .update_time(ele.getUpdate_time())
                    .build();
                exerciseSubmittedResponses.add(exerciseResponse);
            }
        });

        
        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_submit", exerciseResponses);
        response.put("exercise_submitted", exerciseSubmittedResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseForTeacherBySectionId(Long id) {
        List<GetExerciseTeacherResponse> allExerciseResponses = new ArrayList<>();
        List<Exercise> listExerciseBySection = exerciseRepository.findBySectionId2(id);
        listExerciseBySection.forEach(content -> {
            if (content.getSection().getId().compareTo(id) == 0){
                List<ExerciseSubmission> allExerciseSubmissionForExercise = exerciseSubmissionRepository.findByExerciseId1(content.getId());
                if (allExerciseSubmissionForExercise.size() == 0) {
                    GetExerciseTeacherResponse exerciseResponse = GetExerciseTeacherResponse.builder()
                        .id(content.getId())
                        .section_id(content.getSection().getId())
                        .status("No submissions")
                        .section_name(content.getSection().getName())
                        .name(content.getName())
                        .deadline(content.getDeadline())
                        .description(content.getDescription())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                    allExerciseResponses.add(exerciseResponse);
                }
                else {
                    int total_user_grade_exercise_submission = (int) allExerciseSubmissionForExercise.stream().filter(animal -> animal.getScore() != null).count();
                    if (allExerciseSubmissionForExercise.size() > total_user_grade_exercise_submission) {
                        GetExerciseTeacherResponse exerciseResponse = GetExerciseTeacherResponse.builder()
                            .id(content.getId())
                            .section_id(content.getSection().getId())
                            .status("Scoring")
                            .section_name(content.getSection().getName())
                            .name(content.getName())
                            .deadline(content.getDeadline())
                            .description(content.getDescription())
                            .create_time(content.getCreate_time())
                            .update_time(content.getUpdate_time())
                            .build();
                        allExerciseResponses.add(exerciseResponse);
                    }
                    else {
                        GetExerciseTeacherResponse exerciseResponse = GetExerciseTeacherResponse.builder()
                            .id(content.getId())
                            .section_id(content.getSection().getId())
                            .status("Scoring done")
                            .section_name(content.getSection().getName())
                            .name(content.getName())
                            .deadline(content.getDeadline())
                            .description(content.getDescription())
                            .create_time(content.getCreate_time())
                            .update_time(content.getUpdate_time())
                            .build();
                        allExerciseResponses.add(exerciseResponse);
                    }
                }
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("Exercise", allExerciseResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseBySectionId(Long id) {
        List<GetExerciseResponse> allExerciseResponses = new ArrayList<>();
        List<Exercise> listExerciseBySection = exerciseRepository.findBySectionId2(id);
        listExerciseBySection.forEach(content -> {
            if (content.getSection().getId().compareTo(id) == 0){
                GetExerciseResponse exerciseResponse = GetExerciseResponse.builder()
                    .id(content.getId())
                    .section_id(content.getSection().getId())
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
        Optional<Exercise> exerciseOpt = exerciseRepository.findById2(id);
        Exercise exercise = exerciseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Exercise.not_found");
        });

        return GetExerciseResponse.builder()
            .id(exercise.getId())
            .section_id(exercise.getSection().getId())
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

        Optional <Section> sectionOpt = sectionRepository.findById1(createExerciseRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        
        Exercise savedExercise = Exercise.builder()
                .section(section)
                .name(createExerciseRequest.getName())
                .description(createExerciseRequest.getDescription())
                .deadline(createExerciseRequest.getDeadline())
                .build();
        exerciseRepository.save(savedExercise);

        return savedExercise.getId();
    }

    @Override
    public Long removeExerciseById(Long id) {
        Optional<Exercise> exerciseOpt = exerciseRepository.findById1(id);
        exerciseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Exercise.not_found");
        });

        exerciseRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateExerciseById(Long id, CreateExerciseRequest createExerciseRequest) {
        Optional<Exercise> exerciseOpt = exerciseRepository.findById1(id);
        Exercise updatedExercise = exerciseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Exercise.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById1(createExerciseRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        updatedExercise.setName(createExerciseRequest.getName());
        updatedExercise.setDescription(createExerciseRequest.getDescription());
        updatedExercise.setSection(section);
        updatedExercise.setDeadline(createExerciseRequest.getDeadline());
        exerciseRepository.save(updatedExercise);

        return updatedExercise.getId();
    }
}
