package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateContestRequest;
import com.app.kidsdrawing.dto.GetContestResponse;

public interface ContestService {
    ResponseEntity<Map<String, Object>> getAllContest(int page, int size);
    GetContestResponse getContestById(Long id);
    Long createContest(CreateContestRequest createContestRequest);
    Long removeContestById(Long id);
    Long updateContestById(Long id, CreateContestRequest createContestRequest);
}
