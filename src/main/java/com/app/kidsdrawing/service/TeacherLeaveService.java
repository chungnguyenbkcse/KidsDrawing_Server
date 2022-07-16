package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateReviewTeacherLeaveRequest;
import com.app.kidsdrawing.dto.CreateTeacherLeaveRequest;
import com.app.kidsdrawing.dto.GetTeacherLeaveResponse;

public interface TeacherLeaveService {
    ResponseEntity<Map<String, Object>> getAllTeacherLeave();
    GetTeacherLeaveResponse getTeacherLeaveById(Long id);
    Long createTeacherLeave(CreateTeacherLeaveRequest createTeacherLeaveRequest);
    Long removeTeacherLeaveById(Long id);
    Long updateTeacherLeaveById(Long id, CreateTeacherLeaveRequest createTeacherLeaveRequest);
    Long updateStatusTeacherLeaveById(Long id, CreateReviewTeacherLeaveRequest createReviewTeacherLeaveRequest);
}
