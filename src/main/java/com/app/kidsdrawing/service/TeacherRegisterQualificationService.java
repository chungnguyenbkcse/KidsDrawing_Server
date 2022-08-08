package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateTeacherRegisterQualificationRequest;
import com.app.kidsdrawing.dto.GetTeacherRegisterQualificationResponse;

public interface TeacherRegisterQualificationService {
    ResponseEntity<Map<String, Object>> getAllTeacherRegisterQualification();
    ResponseEntity<Map<String, Object>> getAllTeacherRegisterQualificationByTeacherId(Long id);
    GetTeacherRegisterQualificationResponse getTeacherRegisterQualificationById(Long id);
    Long createTeacherRegisterQualification(CreateTeacherRegisterQualificationRequest createTeacherRegisterQualificationRequest);
    Long removeTeacherRegisterQualificationById(Long id);
    Long updateTeacherRegisterQualificationById(Long id, CreateTeacherRegisterQualificationRequest createTeacherRegisterQualificationRequest);
}
