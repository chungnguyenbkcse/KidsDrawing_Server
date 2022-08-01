package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateSemesterCourseRequest;
import com.app.kidsdrawing.dto.GetSemesterCourseResponse;

public interface SemesterCourseService {
    ResponseEntity<Map<String, Object>> getAllSemesterCourse(int page, int size);
    ResponseEntity<Map<String, Object>> getAllSemesterCourseBySemester(int page, int size, Long id);
    ResponseEntity<Map<String, Object>> getAllSemesterCourseByCourse(int page, int size, Long id);
    GetSemesterCourseResponse getSemesterCourseById(Long id);
    Long createSemesterCourse(CreateSemesterCourseRequest createSemesterCourseRequest);
    Long removeSemesterCourseById(Long id);
    Long updateSemesterCourseById(Long id, CreateSemesterCourseRequest createSemesterCourseRequest);
}