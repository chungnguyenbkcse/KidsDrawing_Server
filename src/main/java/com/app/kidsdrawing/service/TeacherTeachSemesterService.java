package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateReviewTeacherTeachSemesterRequest;
import com.app.kidsdrawing.dto.CreateTeacherTeachSemesterRequest;
import com.app.kidsdrawing.dto.GetTeacherTeachSemesterResponse;

public interface TeacherTeachSemesterService {
    ResponseEntity<Map<String, Object>> getAllTeacherTeachSemester();
    GetTeacherTeachSemesterResponse getTeacherTeachSemesterById(Long id);
    Long createTeacherTeachSemester(CreateTeacherTeachSemesterRequest createTeacherTeachSemesterRequest);
    Long removeTeacherTeachSemesterById(Long id);
    Long updateTeacherTeachSemesterById(Long id, CreateTeacherTeachSemesterRequest createTeacherTeachSemesterRequest);
    Long updateStatusTeacherTeachSemesterById(Long id, CreateReviewTeacherTeachSemesterRequest createReviewTeacherTeachSemesterRequest);
}
