package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateReviewTeacherLeaveRequest;
import com.app.kidsdrawing.dto.CreateTeacherLeaveRequest;
import com.app.kidsdrawing.dto.GetTeacherLeaveResponse;

public interface TeacherLeaveService {
    ResponseEntity<Map<String, Object>> getAllTeacherLeave();
    GetTeacherLeaveResponse getTeacherLeaveById(Long id);
    ResponseEntity<Map<String, Object>> getTeacherLeaveByClassId(Long id);
    ResponseEntity<Map<String, Object>> getTeacherLeaveByTeacher(Long id);
    GetTeacherLeaveResponse createTeacherLeave(CreateTeacherLeaveRequest createTeacherLeaveRequest);
    Long removeTeacherLeaveById(Long id);
    Long updateTeacherLeaveById(Long id, CreateTeacherLeaveRequest createTeacherLeaveRequest);
    GetTeacherLeaveResponse updateStatusTeacherLeaveById(Long id, CreateReviewTeacherLeaveRequest createReviewTeacherLeaveRequest);
}
