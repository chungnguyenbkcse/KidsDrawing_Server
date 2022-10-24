package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserGradeContestRequest;
import com.app.kidsdrawing.dto.GetUserGradeContestResponse;

public interface UserGradeContestService {
    ResponseEntity<Map<String, Object>> getAllUserGradeContestByTeacherId(UUID id);
    ResponseEntity<Map<String, Object>> getAllUserGradeContestByContestId(UUID id);
    GetUserGradeContestResponse getUserGradeContestById(UUID id);
    UUID createUserGradeContest(CreateUserGradeContestRequest createUserGradeContestRequest);
    UUID removeUserGradeContestById(UUID id);
    UUID removeUserGradeContestByContest(UUID id);
    UUID updateUserGradeContestById(UUID id, CreateUserGradeContestRequest createUserGradeContestRequest);
}
