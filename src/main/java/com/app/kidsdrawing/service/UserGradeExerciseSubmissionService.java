package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserGradeExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetUserGradeExerciseSubmissionResponse;

public interface UserGradeExerciseSubmissionService {
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmission();
    ResponseEntity<Map<String, Object>> getFinalGradeAndReviewForStudentAndClasses(Long student_id, Long classes_id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByTeacherId(Long id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentId(Long id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByClassId(Long id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseId(Long id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseAndClass(Long exercise_id, Long classes_id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentAndClass(Long classes_id, Long student_id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentAndExercise(Long exercise_id, Long student_id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseSubmissionId(Long id);
    GetUserGradeExerciseSubmissionResponse getUserGradeExerciseSubmissionById(Long id);
    Long createUserGradeExerciseSubmission(CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest);
    Long removeUserGradeExerciseSubmissionById(Long id);
    Long updateUserGradeExerciseSubmissionById(Long teacher_id, Long submisstion_id, CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest);
}
