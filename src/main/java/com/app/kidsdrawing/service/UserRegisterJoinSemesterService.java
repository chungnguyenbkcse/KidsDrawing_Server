package com.app.kidsdrawing.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateMomoRequest;
import com.app.kidsdrawing.dto.CreateUserRegisterJoinSemesterRequest;
import com.app.kidsdrawing.dto.GetUserRegisterJoinSemesterResponse;

public interface UserRegisterJoinSemesterService {
    ResponseEntity<Map<String, Object>> getAllUserRegisterJoinSemester();
    ResponseEntity<Map<String, Object>> getAllUserRegisterJoinSemesterBySemesterClass(Long id);
    ResponseEntity<Map<String, Object>> getAllUserRegisterJoinSemesterByPayerId(Long id);
    GetUserRegisterJoinSemesterResponse getUserRegisterJoinSemesterById(Long id);
    ResponseEntity<Map<String, Object>> getReportUserRegisterJoinSemester(int year);
    Long createUserRegisterJoinSemester(CreateUserRegisterJoinSemesterRequest createUserRegisterJoinSemesterRequest);
    Long removeUserRegisterJoinSemesterById(Long id);
    Long updateUserRegisterJoinSemesterById(Long id, CreateUserRegisterJoinSemesterRequest createUserRegisterJoinSemesterRequest);
    Long updateStatusUserRegisterJoinSemester(List<Long> ids, CreateMomoRequest createMomoRequest);
    Long updateStatusUserRegisterJoinSemester(List<Long> ids);
}
