package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateReviewStudentLeaveRequest;
import com.app.kidsdrawing.dto.CreateStudentLeaveRequest;
import com.app.kidsdrawing.dto.GetStudentLeaveResponse;

public interface StudentLeaveService {
    ResponseEntity<Map<String, Object>> getAllStudentLeave();
    GetStudentLeaveResponse getStudentLeaveById(Long id);
    Long createStudentLeave(CreateStudentLeaveRequest createStudentLeaveRequest);
    Long removeStudentLeaveById(Long id);
    Long updateStudentLeaveById(Long id, CreateStudentLeaveRequest createStudentLeaveRequest);
    Long updateStatusStudentLeaveById(Long id, CreateReviewStudentLeaveRequest createReviewStudentLeaveRequest);
}
