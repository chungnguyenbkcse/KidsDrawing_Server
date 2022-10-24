package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserGradeExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetUserGradeExerciseSubmissionResponse;

public interface UserGradeExerciseSubmissionService {
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmission();
    ResponseEntity<Map<String, Object>> getFinalGradeAndReviewForStudentAndClasses(UUID student_id, UUID classes_id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByTeacherId(UUID id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentId(UUID id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByClassId(UUID id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseId(UUID id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseAndClass(UUID exercise_id, UUID classes_id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentAndClass(UUID classes_id, UUID student_id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentAndExercise(UUID exercise_id, UUID student_id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseSubmissionId(UUID id);
    GetUserGradeExerciseSubmissionResponse getUserGradeExerciseSubmissionById(UUID teacher_id, UUID exercise_submission_id);
    UUID createUserGradeExerciseSubmission(CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest);
    UUID removeUserGradeExerciseSubmissionById(UUID teacher_id, UUID exercise_submission_id);
    UUID updateUserGradeExerciseSubmissionById(UUID teacher_id, UUID submisstion_id, CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest);
}
