package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import java.util.UUID;

import com.app.kidsdrawing.dto.CreateContestRequest;
import com.app.kidsdrawing.dto.GetContestResponse;

public interface ContestService {
    ResponseEntity<Map<String, Object>> getAllContest(int page, int size);
    ResponseEntity<Map<String, Object>> getTotalContest();
    ResponseEntity<Map<String, Object>> getTotalContestForStudent(UUID student_id);
    ResponseEntity<Map<String, Object>> getAllContestByArtTypeId(int page, int size, UUID id);
    ResponseEntity<Map<String, Object>> getAllContestByArtAgeId(int page, int size, UUID id);
    ResponseEntity<Map<String, Object>> getAllContestByParent(UUID parent_id);
    ResponseEntity<Map<String, Object>> getAllContestByStudent(UUID student_id);
    ResponseEntity<Map<String, Object>> getAllContestByTeacher(UUID id);
    GetContestResponse getContestByName(String name);
    GetContestResponse getContestById(UUID id);
    UUID createContest(CreateContestRequest createContestRequest);
    UUID removeContestById(UUID id);
    UUID updateContestById(UUID id, CreateContestRequest createContestRequest);
}
