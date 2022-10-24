package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserRegisterJoinContestRequest;
import com.app.kidsdrawing.dto.GetUserRegisterJoinContestResponse;

public interface UserRegisterJoinContestService {
    ResponseEntity<Map<String, Object>> getAllUserRegisterJoinContestByStudentId(UUID id);
    ResponseEntity<Map<String, Object>> getAllUserRegisterJoinContestByContestId(UUID id);
    GetUserRegisterJoinContestResponse getUserRegisterJoinContestById(UUID id);
    UUID createUserRegisterJoinContest(CreateUserRegisterJoinContestRequest createUserRegisterJoinContestRequest);
    UUID removeUserRegisterJoinContestById(UUID id);
    UUID updateUserRegisterJoinContestById(UUID id, CreateUserRegisterJoinContestRequest createUserRegisterJoinContestRequest);
}
