package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateContestSubmissionRequest;
import com.app.kidsdrawing.dto.GetContestSubmissionResponse;
import java.util.UUID;

public interface ContestSubmissionService {
    ResponseEntity<Map<String, Object>> getAllContestSubmission();
    ResponseEntity<Map<String, Object>> getAllContestSubmissionByStudentId(UUID id);
    ResponseEntity<Map<String, Object>> getAllContestSubmissionByContestId(UUID id);
    GetContestSubmissionResponse getContestSubmissionById(UUID id);
    UUID createContestSubmission(CreateContestSubmissionRequest createContestSubmissionRequest);
    UUID removeContestSubmissionById(UUID id);
    UUID updateContestSubmissionById(UUID id, CreateContestSubmissionRequest createContestSubmissionRequest);
}
