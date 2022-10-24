package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateTeacherRegisterQualificationAdminRequest;
import com.app.kidsdrawing.dto.CreateTeacherRegisterQualificationRequest;
import com.app.kidsdrawing.dto.GetTeacherRegisterQualificationResponse;

public interface TeacherRegisterQualificationService {
    ResponseEntity<Map<String, Object>> getAllTeacherRegisterQualification();
    ResponseEntity<Map<String, Object>> getAllTeacherRegisterQualificationByTeacherId(UUID id);
    ResponseEntity<Map<String, Object>> getAllTeacherRegisterQualificationApprovedByTeacherId(UUID id);
    GetTeacherRegisterQualificationResponse getTeacherRegisterQualificationById(UUID id);
    UUID createTeacherRegisterQualification(CreateTeacherRegisterQualificationRequest createTeacherRegisterQualificationRequest);
    UUID removeTeacherRegisterQualificationById(UUID id);
    UUID updateTeacherRegisterQualificationById(UUID id, CreateTeacherRegisterQualificationRequest createTeacherRegisterQualificationRequest);
    UUID updateTeacherRegisterQualificationByAdmin(UUID id, CreateTeacherRegisterQualificationAdminRequest createTeacherRegisterQualificationRequest);
}
