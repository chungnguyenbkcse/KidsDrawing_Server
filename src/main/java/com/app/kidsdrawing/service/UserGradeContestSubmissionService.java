package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserGradeContestSubmissionRequest;
import com.app.kidsdrawing.dto.GetUserGradeContestSubmissionResponse;

public interface UserGradeContestSubmissionService {
    ResponseEntity<Map<String, Object>> getAllUserGradeContestSubmission();
    ResponseEntity<Map<String, Object>> getAllUserGradeContestSubmissionByStudentId(Long id);
    ResponseEntity<Map<String, Object>> getAllUserGradeContestSubmissionByContestId(Long id);
    GetUserGradeContestSubmissionResponse getUserGradeContestSubmissionById(Long id);
    Long createUserGradeContestSubmission(CreateUserGradeContestSubmissionRequest createUserGradeContestSubmissionRequest);
    Long removeUserGradeContestSubmissionById(Long id);
    Long updateUserGradeContestSubmissionById(Long student_id, Long submisstion_id, CreateUserGradeContestSubmissionRequest createUserGradeContestSubmissionRequest);
}
