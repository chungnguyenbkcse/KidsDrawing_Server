package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import com.app.kidsdrawing.dto.CreateExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetExerciseSubmissionResponse;
import com.app.kidsdrawing.dto.GetFinalScoreForStudentResponse;
import com.app.kidsdrawing.entity.ExerciseSubmission;
import com.app.kidsdrawing.entity.ExerciseSubmissionKey;
import com.app.kidsdrawing.entity.Student;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.Exercise;
import com.app.kidsdrawing.exception.ArtAgeNotDeleteException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.ExerciseRepository;
import com.app.kidsdrawing.repository.ExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.StudentRepository;
import com.app.kidsdrawing.service.ExerciseSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ExerciseSubmissionServiceImpl implements ExerciseSubmissionService {

    private final ExerciseSubmissionRepository exerciseSubmissionRepository;
    private final ExerciseRepository exerciseRepository;
    private final StudentRepository studentRepository;
    private final ClassesRepository classRepository;
    private static float exam = 0;

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmission() {
        List<GetExerciseSubmissionResponse> allExerciseSubmissionResponses = new ArrayList<>();
        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findAll();
        listExerciseSubmission.forEach(content -> {
            GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .image_url(content.getImage_url())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .score(content.getScore())
                    .feedback(content.getFeedback())
                    .time(content.getTime())
                    .exercise_deadline(content.getExercise().getDeadline())
                    .exercise_description(content.getExercise().getDescription())
                    .exercise_name(content.getExercise().getName())
                    .student_name(content.getStudent().getUser().getUsername() + " - "
                            + content.getStudent().getUser().getFirstName() + " "
                            + content.getStudent().getUser().getLastName())
                    .build();
            allExerciseSubmissionResponses.add(exerciseSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("ExerciseSubmission", allExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetExerciseSubmissionResponse getAllExerciseSubmissionByExerciseAndStudent(Long exercise_id,
            Long student_id) {
        Optional<ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository
                .findByExerciseIdAndStudentId(exercise_id, student_id);
        ExerciseSubmission exerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
        });

        GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                .exercise_id(exerciseSubmission.getExercise().getId())
                .student_id(exerciseSubmission.getStudent().getId())
                .image_url(exerciseSubmission.getImage_url())
                .create_time(exerciseSubmission.getCreate_time())
                .update_time(exerciseSubmission.getUpdate_time())
                .score(exerciseSubmission.getScore())
                .feedback(exerciseSubmission.getFeedback())
                .time(exerciseSubmission.getTime())
                .exercise_deadline(exerciseSubmission.getExercise().getDeadline())
                .exercise_description(exerciseSubmission.getExercise().getDescription())
                .exercise_name(exerciseSubmission.getExercise().getName())
                .student_name(exerciseSubmission.getStudent().getUser().getUsername() + " - "
                        + exerciseSubmission.getStudent().getUser().getFirstName() + " "
                        + exerciseSubmission.getStudent().getUser().getLastName())
                .build();
        return exerciseSubmissionResponse;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByStudentId(Long id) {
        List<GetExerciseSubmissionResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> exerciseGradeResponses = new ArrayList<>();
        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findByStudentId2(id);

        List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream()
                .filter(animal -> animal.getScore() != null).collect(Collectors.toList());

        listExerciseSubmission.forEach(content -> {
            if (exerciseSubmissionGrade.contains(content)) {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .score(content.getScore())
                        .feedback(content.getFeedback())
                        .time(content.getTime())
                        .exercise_deadline(content.getExercise().getDeadline())
                        .exercise_description(content.getExercise().getDescription())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .build();
                exerciseGradeResponses.add(exerciseSubmissionResponse);
            } else {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .score(content.getScore())
                        .feedback(content.getFeedback())
                        .time(content.getTime())
                        .exercise_deadline(content.getExercise().getDeadline())
                        .exercise_description(content.getExercise().getDescription())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .build();
                exerciseResponses.add(exerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", exerciseResponses);
        response.put("exercise_graded", exerciseGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByExerciseId(Long id) {
        List<GetExerciseSubmissionResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> exerciseGradeResponses = new ArrayList<>();
        List<ExerciseSubmission> exerciseSubmissionByExercise = exerciseSubmissionRepository.findByExerciseId2(id);

        List<ExerciseSubmission> exerciseSubmissionGrade = exerciseSubmissionByExercise.stream()
                .filter(animal -> animal.getScore() != null).collect(Collectors.toList());
        exerciseSubmissionByExercise.forEach(content -> {
            if (exerciseSubmissionGrade.contains(content)) {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .score(content.getScore())
                        .feedback(content.getFeedback())
                        .time(content.getTime())
                        .exercise_deadline(content.getExercise().getDeadline())
                        .exercise_description(content.getExercise().getDescription())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .build();
                exerciseGradeResponses.add(exerciseSubmissionResponse);
            } else {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .score(content.getScore())
                        .feedback(content.getFeedback())
                        .time(content.getTime())
                        .exercise_deadline(content.getExercise().getDeadline())
                        .exercise_description(content.getExercise().getDescription())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .build();
                exerciseResponses.add(exerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", exerciseResponses);
        response.put("exercise_graded", exerciseGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionBySectionAndParent(Long section_id, Long parent_id) {
        List<GetExerciseSubmissionResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> exerciseGradeResponses = new ArrayList<>();
        
        List<Student> allStudent = studentRepository.findByParentId(parent_id);
        allStudent.forEach(student -> {
                List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository
                .findAllExerciseSubmissionBySectionAndStudent(section_id, student.getId());
        System.out.print(listExerciseSubmission.size());
                listExerciseSubmission.forEach(content -> {
                    if (content.getScore() != null) {
                        GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                                .exercise_id(content.getExercise().getId())
                                .student_id(content.getStudent().getId())
                                .image_url(content.getImage_url())
                                .exercise_deadline(content.getExercise().getDeadline())
                                .create_time(content.getCreate_time())
                                .update_time(content.getUpdate_time())
                                .score(content.getScore())
                                .feedback(content.getFeedback())
                                .time(content.getTime())
                                .exercise_description(content.getExercise().getDescription())
                                .exercise_name(content.getExercise().getName())
                                .student_name(content.getStudent().getUser().getUsername() + " - "
                                        + content.getStudent().getUser().getFirstName() + " "
                                        + content.getStudent().getUser().getLastName())
                                .build();
                        exerciseGradeResponses.add(exerciseSubmissionResponse);
                    } else {
                        GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                
                                .exercise_id(content.getExercise().getId())
                                .student_id(content.getStudent().getId())
                                .score(content.getScore())
                                .feedback(content.getFeedback())
                                .time(content.getTime())
                                .exercise_deadline(content.getExercise().getDeadline())
                                .exercise_description(content.getExercise().getDescription())
                                .exercise_name(content.getExercise().getName())
                                .student_name(content.getStudent().getUser().getUsername() + " - "
                                        + content.getStudent().getUser().getFirstName() + " "
                                        + content.getStudent().getUser().getLastName())
                                .image_url(content.getImage_url())
                                .create_time(content.getCreate_time())
                                .update_time(content.getUpdate_time())
                                .build();
                        exerciseResponses.add(exerciseSubmissionResponse);
                    }
                });
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", exerciseResponses);
        response.put("exercise_graded", exerciseGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionBySectionAndStudent(Long section_id,
            Long student_id) {
        List<GetExerciseSubmissionResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> exerciseGradeResponses = new ArrayList<>();

        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository
                .findAllExerciseSubmissionBySectionAndStudent(section_id, student_id);
        System.out.print(listExerciseSubmission.size());
        listExerciseSubmission.forEach(content -> {
            if (content.getScore() != null) {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .image_url(content.getImage_url())
                        .exercise_deadline(content.getExercise().getDeadline())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .score(content.getScore())
                        .feedback(content.getFeedback())
                        .time(content.getTime())
                        .exercise_description(content.getExercise().getDescription())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .build();
                exerciseGradeResponses.add(exerciseSubmissionResponse);
            } else {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()

                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .score(content.getScore())
                        .feedback(content.getFeedback())
                        .time(content.getTime())
                        .exercise_deadline(content.getExercise().getDeadline())
                        .exercise_description(content.getExercise().getDescription())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                exerciseResponses.add(exerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", exerciseResponses);
        response.put("exercise_graded", exerciseGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassAndParent(Long class_id, Long parent_id) {
        List<GetExerciseSubmissionResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> exerciseGradeResponses = new ArrayList<>();
        List<Student> pageUser = studentRepository.findByParentId(parent_id);
        pageUser.forEach(student -> {
            List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository
                    .findAllExerciseSubmissionByClassAndStudent(class_id, student.getId());

            List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream()
                    .filter(animal -> animal.getScore() != null).collect(Collectors.toList());

            listExerciseSubmission.forEach(content -> {
                if (exerciseSubmissionGrade.contains(content)) {
                    GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()

                            .exercise_id(content.getExercise().getId())
                            .student_id(content.getStudent().getId())
                            .score(content.getScore())
                            .feedback(content.getFeedback())
                            .time(content.getTime())
                            .exercise_deadline(content.getExercise().getDeadline())
                            .exercise_description(content.getExercise().getDescription())
                            .exercise_name(content.getExercise().getName())
                            .student_name(content.getStudent().getUser().getUsername() + " - "
                                    + content.getStudent().getUser().getFirstName() + " "
                                    + content.getStudent().getUser().getLastName())
                            .create_time(content.getCreate_time())
                            .update_time(content.getUpdate_time())
                            .build();
                    exerciseGradeResponses.add(exerciseSubmissionResponse);
                } else {
                    GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()

                            .exercise_id(content.getExercise().getId())
                            .student_id(content.getStudent().getId())
                            .score(content.getScore())
                            .feedback(content.getFeedback())
                            .time(content.getTime())
                            .exercise_deadline(content.getExercise().getDeadline())
                            .exercise_description(content.getExercise().getDescription())
                            .exercise_name(content.getExercise().getName())
                            .student_name(content.getStudent().getUser().getUsername() + " - "
                                    + content.getStudent().getUser().getFirstName() + " "
                                    + content.getStudent().getUser().getLastName())
                            .image_url(content.getImage_url())
                            .create_time(content.getCreate_time())
                            .update_time(content.getUpdate_time())
                            .build();
                    exerciseResponses.add(exerciseSubmissionResponse);
                }
            });
        });
        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", exerciseResponses);
        response.put("exercise_graded", exerciseGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassAndStudent(Long class_id,
            Long student_id) {
        List<GetExerciseSubmissionResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> exerciseGradeResponses = new ArrayList<>();

        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository
                .findAllExerciseSubmissionByClassAndStudent(class_id, student_id);
        List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream()
                .filter(animal -> animal.getScore() != null).collect(Collectors.toList());

        listExerciseSubmission.forEach(content -> {
            if (exerciseSubmissionGrade.contains(content)) {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()

                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .score(content.getScore())
                        .feedback(content.getFeedback())
                        .time(content.getTime())
                        .exercise_deadline(content.getExercise().getDeadline())
                        .exercise_description(content.getExercise().getDescription())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .image_url(content.getImage_url())

                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                exerciseGradeResponses.add(exerciseSubmissionResponse);
            } else {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()

                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .score(content.getScore())
                        .feedback(content.getFeedback())
                        .time(content.getTime())
                        .exercise_deadline(content.getExercise().getDeadline())
                        .exercise_description(content.getExercise().getDescription())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                exerciseResponses.add(exerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", exerciseResponses);
        response.put("exercise_graded", exerciseGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassId(Long id) {
        List<GetExerciseSubmissionResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> exerciseGradeResponses = new ArrayList<>();

        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository
                .findAllExerciseSubmissionByClass(id);

        List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream()
                .filter(animal -> animal.getScore() != null).collect(Collectors.toList());

        listExerciseSubmission.forEach(content -> {
            if (exerciseSubmissionGrade.contains(content)) {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()

                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .score(content.getScore())
                        .feedback(content.getFeedback())
                        .time(content.getTime())
                        .exercise_deadline(content.getExercise().getDeadline())
                        .exercise_description(content.getExercise().getDescription())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                exerciseGradeResponses.add(exerciseSubmissionResponse);
            } else {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()

                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .score(content.getScore())
                        .feedback(content.getFeedback())
                        .time(content.getTime())
                        .exercise_deadline(content.getExercise().getDeadline())
                        .exercise_description(content.getExercise().getDescription())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                exerciseResponses.add(exerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", exerciseResponses);
        response.put("exercise_graded", exerciseGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getFinalGradeAndReviewForParentAndClasses(Long parent_id,
            Long classes_id) {
        List<GetExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<Student> listChilds = studentRepository.findByParentId(parent_id);
        List<Float> final_score = new ArrayList<>();
        listChilds.forEach(student -> {
            List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository
                    .findAllExerciseSubmissionByClassAndStudent(classes_id, student.getId());
            List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream()
                    .filter(animal -> animal.getScore() != null).collect(Collectors.toList());
            List<Exercise> allExerciseByClass = exerciseRepository.findAllExerciseByClass(classes_id);
            exam = (float) 0;
            exerciseSubmissionGrade.forEach(content -> {
                exam = exam + content.getScore();
                GetExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetExerciseSubmissionResponse
                        .builder()
                        .exercise_id(content.getExercise().getId())
                        .student_id(content.getStudent().getId())
                        .score(content.getScore())
                        .feedback(content.getFeedback())
                        .time(content.getTime())
                        .exercise_deadline(content.getExercise().getDeadline())
                        .exercise_description(content.getExercise().getDescription())
                        .exercise_name(content.getExercise().getName())
                        .student_name(content.getStudent().getUser().getUsername() + " - "
                                + content.getStudent().getUser().getFirstName() + " "
                                + content.getStudent().getUser().getLastName())
                        .image_url(content.getImage_url())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            });

            final_score.add(exam / allExerciseByClass.size());
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        response.put("final_grade", final_score);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getFinalGradeAndReviewForStudentAndClasses(Long student_id,
            Long classes_id) {
        List<GetExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository
                .findAllExerciseSubmissionByClassAndStudent(classes_id, student_id);
        List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream()
                .filter(animal -> animal.getScore() != null).collect(Collectors.toList());
        List<Exercise> allExerciseByClass = exerciseRepository.findAllExerciseByClass(classes_id);
        exam = (float) 0;
        exerciseSubmissionGrade.forEach(content -> {
            exam = exam + content.getScore();
            GetExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                    .exercise_id(content.getExercise().getId())
                    .student_id(content.getStudent().getId())
                    .score(content.getScore())
                    .feedback(content.getFeedback())
                    .time(content.getTime())
                    .exercise_deadline(content.getExercise().getDeadline())
                    .exercise_description(content.getExercise().getDescription())
                    .exercise_name(content.getExercise().getName())
                    .student_name(content.getStudent().getUser().getUsername() + " - "
                            + content.getStudent().getUser().getFirstName() + " "
                            + content.getStudent().getUser().getLastName())
                    .image_url(content.getImage_url())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
            allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        response.put("final_grade", exam / allExerciseByClass.size());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllFinalGradeAForStudent(Long student_id) {
        List<GetFinalScoreForStudentResponse> allFinalScoreForStudent = new ArrayList<>();
        List<Classes> allClassForStudent = classRepository.findAllByStudent2(student_id);

        allClassForStudent.forEach(element -> {
            List<Exercise> allExerciseByClass = exerciseRepository.findAllExerciseByClass(element.getId());
            List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository
                    .findAllExerciseSubmissionByClassAndStudent(element.getId(), student_id);
            List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream()
                    .filter(animal -> animal.getScore() != null).collect(Collectors.toList());
            exam = (float) 0;
            exerciseSubmissionGrade.forEach(content -> {
                exam = exam + content.getScore();
            });
            GetFinalScoreForStudentResponse userGradeExerciseSubmissionResponse = GetFinalScoreForStudentResponse
                    .builder()
                    .final_score(exam / allExerciseByClass.size())
                    .course_id(element.getSemesterClass().getCourse().getId())
                    .course_name(element.getSemesterClass().getCourse().getName())
                    .build();
            allFinalScoreForStudent.add(userGradeExerciseSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("final_grade", allFinalScoreForStudent);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public Long createExerciseSubmission(CreateExerciseSubmissionRequest createExerciseSubmissionRequest) {

        Optional<Student> studentOpt = studentRepository.findById1(createExerciseSubmissionRequest.getStudent_id());
        Student student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional<Exercise> exerciseOpt = exerciseRepository.findById1(createExerciseSubmissionRequest.getExercise_id());
        Exercise exercise = exerciseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.exercise.not_found");
        });

        ExerciseSubmissionKey id = new ExerciseSubmissionKey(student.getId(), exercise.getId());

        LocalDateTime time_now = LocalDateTime.now();

        if (time_now.isAfter((exercise.getDeadline()))) {
            throw new EntityNotFoundException("exception.deadline.not_found");
        } else {
            ExerciseSubmission savedExerciseSubmission = ExerciseSubmission.builder()
                    .id(id)
                    .student(student)
                    .exercise(exercise)
                    .image_url(createExerciseSubmissionRequest.getImage_url())
                    .build();
            exerciseSubmissionRepository.save(savedExerciseSubmission);

            return savedExerciseSubmission.getStudent().getId();
        }
    }

    @Override
    public Long removeExerciseSubmissionById(Long exercise_id, Long student_id) {
        Optional<ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository
                .findByExerciseIdAndStudentId(exercise_id, student_id);
        ExerciseSubmission exerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
        });

        LocalDateTime time_now = LocalDateTime.now();
        if (time_now.isAfter(exerciseSubmission.getExercise().getDeadline())) {
            throw new ArtAgeNotDeleteException("exception.Exercise_Deadline.not_delete");
        }

        exerciseSubmissionRepository.deleteById(exerciseSubmission.getId());
        return student_id;
    }

    @Override
    public Long updateExerciseSubmissionByTeacher(CreateExerciseSubmissionRequest createExerciseSubmissionRequest) {
        Optional<ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findByExerciseIdAndStudentId(
                createExerciseSubmissionRequest.getExercise_id(), createExerciseSubmissionRequest.getStudent_id());
        ExerciseSubmission updatedExerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
        });

        updatedExerciseSubmission.setScore(createExerciseSubmissionRequest.getScore());
        updatedExerciseSubmission.setFeedback(createExerciseSubmissionRequest.getFeedback());

        return updatedExerciseSubmission.getStudent().getId();
    }

    @Override
    public Long updateExerciseSubmissionByStudent(CreateExerciseSubmissionRequest createExerciseSubmissionRequest) {
        Optional<ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findByExerciseIdAndStudentId(
                createExerciseSubmissionRequest.getExercise_id(), createExerciseSubmissionRequest.getStudent_id());
        ExerciseSubmission updatedExerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
        });

        updatedExerciseSubmission.setImage_url(createExerciseSubmissionRequest.getImage_url());

        return updatedExerciseSubmission.getStudent().getId();
    }
}
