package com.app.kidsdrawing.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateMomoRequest;
import com.app.kidsdrawing.dto.CreateUserRegisterJoinSemesterRequest;
import com.app.kidsdrawing.dto.GetUserRegisterJoinSemesterResponse;

public interface UserRegisterJoinSemesterService {
    ResponseEntity<Map<String, Object>> getAllUserRegisterJoinSemester();
    ResponseEntity<Map<String, Object>> getAllMoneyUserRegisterJoinSemester();
    ResponseEntity<Map<String, Object>> getAllUserRegisterJoinSemesterBySemesterClass(UUID id);
    ResponseEntity<Map<String, Object>> getAllUserRegisterJoinSemesterBySemesterClassScheduleClass(UUID id);
    ResponseEntity<Map<String, Object>> getAllUserRegisterJoinSemesterByPayerId(UUID id);
    GetUserRegisterJoinSemesterResponse getUserRegisterJoinSemesterById(UUID id);
    ResponseEntity<Map<String, Object>> getReportUserRegisterJoinSemester(int year);
    UUID createUserRegisterJoinSemester(CreateUserRegisterJoinSemesterRequest createUserRegisterJoinSemesterRequest);
    UUID removeUserRegisterJoinSemesterById(UUID id);
    UUID updateUserRegisterJoinSemesterById(UUID id, CreateUserRegisterJoinSemesterRequest createUserRegisterJoinSemesterRequest);
    UUID updateStatusUserRegisterJoinSemester(List<UUID> ids, CreateMomoRequest createMomoRequest);
    UUID updateStatusUserRegisterJoinSemester(List<UUID> ids);
}
