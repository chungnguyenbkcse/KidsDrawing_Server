package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateReviewStudentLeaveRequest;
import com.app.kidsdrawing.dto.CreateStudentLeaveRequest;
import com.app.kidsdrawing.dto.GetStudentLeaveResponse;

public interface StudentLeaveService {
    ResponseEntity<Map<String, Object>> getAllStudentLeave();
    ResponseEntity<Map<String, Object>> getAllStudentLeaveByClass(Long id);
    ResponseEntity<Map<String, Object>> getAllStudentLeaveByTeacher(Long id);
    ResponseEntity<Map<String, Object>> getAllStudentLeaveByStudent(Long id);
    ResponseEntity<Map<String, Object>> getAllStudentLeaveByParent(Long id);
    ResponseEntity<Map<String, Object>> getAllStudentLeaveByClassAndStudent(Long classes_id, Long student_id);
    ResponseEntity<Map<String, Object>> getAllStudentLeaveByClassAndParent(Long classes_id, Long parent_id);
    GetStudentLeaveResponse getStudentLeaveBySectionAndStudent(Long section_id, Long student_id);
    Long createStudentLeave(CreateStudentLeaveRequest createStudentLeaveRequest);
    Long removeStudentLeaveById(Long student_id, Long section_id);
    Long updateStudentLeaveById(Long student_id, Long section_id, CreateStudentLeaveRequest createStudentLeaveRequest);
    Long updateStatusStudentLeaveById(Long student_id, Long section_id, CreateReviewStudentLeaveRequest createReviewStudentLeaveRequest);
}
