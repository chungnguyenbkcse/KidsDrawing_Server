package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserAttendanceRequest;
import com.app.kidsdrawing.dto.GetUserAttendanceResponse;

public interface UserAttendanceService {
    ResponseEntity<Map<String, Object>> getAllUserAttendance();
    ResponseEntity<Map<String, Object>> getAllUserAttendanceBySection(UUID id);
    ResponseEntity<Map<String, Object>> getAllUserAttendanceByClassAndStudent(UUID classes_id, UUID student_id);
    ResponseEntity<Map<String, Object>> getAllUserAttendanceByClass(UUID classes_id);
    ResponseEntity<Map<String, Object>> getAllUserAttendanceByStudent(UUID id);
    GetUserAttendanceResponse getAllUserAttendanceBySectionAndStudent(UUID section_id, UUID student_id);
    GetUserAttendanceResponse getUserAttendanceById(UUID id);
    UUID createUserAttendance(CreateUserAttendanceRequest createUserAttendanceRequest);
    UUID removeUserAttendanceById(UUID id);
    UUID updateUserAttendanceById(UUID id, CreateUserAttendanceRequest createUserAttendanceRequest);
    UUID updateUserAttendanceBySectionAndStudent(UUID section_id, UUID student_id);
}
