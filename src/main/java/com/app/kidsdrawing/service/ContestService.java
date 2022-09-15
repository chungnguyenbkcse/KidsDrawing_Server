package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateContestRequest;
import com.app.kidsdrawing.dto.GetContestResponse;

public interface ContestService {
    ResponseEntity<Map<String, Object>> getAllContest(int page, int size);
    ResponseEntity<Map<String, Object>> getAllContestByArtTypeId(int page, int size, Long id);
    ResponseEntity<Map<String, Object>> getAllContestByArtAgeId(int page, int size, Long id);
    ResponseEntity<Map<String, Object>> getAllContestByParent(Long parent_id);
    ResponseEntity<Map<String, Object>> getAllContestByStudent(Long student_id);
    ResponseEntity<Map<String, Object>> getAllContestByTeacher(Long id);
    GetContestResponse getContestByName(String name);
    GetContestResponse getContestById(Long id);
    Long createContest(CreateContestRequest createContestRequest);
    Long removeContestById(Long id);
    Long updateContestById(Long id, CreateContestRequest createContestRequest);
}
