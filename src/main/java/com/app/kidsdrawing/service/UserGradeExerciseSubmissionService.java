package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserGradeExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetUserGradeExerciseSubmissionResponse;

public interface UserGradeExerciseSubmissionService {
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmission();
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentId(Long id);
    ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseSubmissionId(Long id);
    GetUserGradeExerciseSubmissionResponse getUserGradeExerciseSubmissionById(Long id);
    Long createUserGradeExerciseSubmission(CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest);
    Long removeUserGradeExerciseSubmissionById(Long id);
    Long updateUserGradeExerciseSubmissionById(Long student_id, Long submisstion_id, CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest);
}
