
package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserGradeContestSubmissionRequest;
import com.app.kidsdrawing.dto.GetUserGradeContestSubmissionResponse;

public interface UserGradeContestSubmissionService {
    ResponseEntity<Map<String, Object>> getAllUserGradeContestSubmission();
    ResponseEntity<Map<String, Object>> getAllUserGradeContestSubmissionByTeacherId(UUID id);
    ResponseEntity<Map<String, Object>> getAllUserGradeContestSubmissionByStudentId(UUID id);
    ResponseEntity<Map<String, Object>> getAllUserGradeContestSubmissionByContestId(UUID id);
    GetUserGradeContestSubmissionResponse getUserGradeContestSubmissionById(UUID teacher_id, UUID contest_submission_id);
    UUID createUserGradeContestSubmission(CreateUserGradeContestSubmissionRequest createUserGradeContestSubmissionRequest);
    UUID removeUserGradeContestSubmissionById(UUID student_id, UUID submisstion_id);
    UUID updateUserGradeContestSubmissionById(UUID student_id, UUID submisstion_id, CreateUserGradeContestSubmissionRequest createUserGradeContestSubmissionRequest);
}
