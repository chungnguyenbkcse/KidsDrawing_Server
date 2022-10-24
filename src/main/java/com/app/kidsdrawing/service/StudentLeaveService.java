package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateReviewStudentLeaveRequest;
import com.app.kidsdrawing.dto.CreateStudentLeaveRequest;
import com.app.kidsdrawing.dto.GetStudentLeaveResponse;

public interface StudentLeaveService {
    ResponseEntity<Map<String, Object>> getAllStudentLeave();
    ResponseEntity<Map<String, Object>> getAllStudentLeaveByClass(UUID id);
    ResponseEntity<Map<String, Object>> getAllStudentLeaveByTeacher(UUID id);
    ResponseEntity<Map<String, Object>> getAllStudentLeaveByStudent(UUID id);
    ResponseEntity<Map<String, Object>> getAllStudentLeaveByParent(UUID id);
    ResponseEntity<Map<String, Object>> getAllStudentLeaveByClassAndStudent(UUID classes_id, UUID student_id);
    GetStudentLeaveResponse getStudentLeaveById(UUID id);
    UUID createStudentLeave(CreateStudentLeaveRequest createStudentLeaveRequest);
    UUID removeStudentLeaveById(UUID id);
    UUID updateStudentLeaveById(UUID id, CreateStudentLeaveRequest createStudentLeaveRequest);
    UUID updateStatusStudentLeaveById(UUID id, CreateReviewStudentLeaveRequest createReviewStudentLeaveRequest);
}
