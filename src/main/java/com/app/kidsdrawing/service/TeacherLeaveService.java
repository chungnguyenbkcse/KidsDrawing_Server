package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateReviewTeacherLeaveRequest;
import com.app.kidsdrawing.dto.CreateTeacherLeaveRequest;
import com.app.kidsdrawing.dto.GetTeacherLeaveResponse;

public interface TeacherLeaveService {
    ResponseEntity<Map<String, Object>> getAllTeacherLeave();
    GetTeacherLeaveResponse getTeacherLeaveById(UUID id);
    ResponseEntity<Map<String, Object>> getTeacherLeaveByClassId(UUID id);
    ResponseEntity<Map<String, Object>> getTeacherLeaveByTeacher(UUID id);
    GetTeacherLeaveResponse createTeacherLeave(CreateTeacherLeaveRequest createTeacherLeaveRequest);
    UUID removeTeacherLeaveById(UUID id);
    UUID updateTeacherLeaveById(UUID id, CreateTeacherLeaveRequest createTeacherLeaveRequest);
    GetTeacherLeaveResponse updateStatusTeacherLeaveById(UUID id, CreateReviewTeacherLeaveRequest createReviewTeacherLeaveRequest);
}
