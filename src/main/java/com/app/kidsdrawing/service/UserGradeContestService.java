package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserGradeContestRequest;
import com.app.kidsdrawing.dto.GetUserGradeContestResponse;

public interface UserGradeContestService {
    ResponseEntity<Map<String, Object>> getAllUserGradeContestByTeacherId(int page, int size, Long id);
    ResponseEntity<Map<String, Object>> getAllUserGradeContestByContestId(int page, int size, Long id);
    GetUserGradeContestResponse getUserGradeContestById(Long id);
    Long createUserGradeContest(CreateUserGradeContestRequest createUserGradeContestRequest);
    Long removeUserGradeContestById(Long id);
    Long updateUserGradeContestById(Long id, CreateUserGradeContestRequest createUserGradeContestRequest);
}
