package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserRegisterJoinSemesterRequest;
import com.app.kidsdrawing.dto.GetUserRegisterJoinSemesterResponse;

public interface UserRegisterJoinSemesterService {
    ResponseEntity<Map<String, Object>> getAllUserRegisterJoinSemester();
    GetUserRegisterJoinSemesterResponse getUserRegisterJoinSemesterById(Long id);
    Long createUserRegisterJoinSemester(CreateUserRegisterJoinSemesterRequest createUserRegisterJoinSemesterRequest);
    Long removeUserRegisterJoinSemesterById(Long id);
    Long updateUserRegisterJoinSemesterById(Long id, CreateUserRegisterJoinSemesterRequest createUserRegisterJoinSemesterRequest);
}