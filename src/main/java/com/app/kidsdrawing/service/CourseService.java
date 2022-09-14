package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateCourseRequest;
import com.app.kidsdrawing.dto.GetCourseResponse;

public interface CourseService {
    ResponseEntity<Map<String, Object>> getAllCourse();
    ResponseEntity<Map<String, Object>> getAllCourseByArtTypeId(Long id);
    ResponseEntity<Map<String, Object>> getAllCourseByArtAgeId(Long id);
    ResponseEntity<Map<String, Object>> getAllCourseByArtLevelId(Long id);
    ResponseEntity<Map<String, Object>> getAllCourseByTeacherId(Long id);
    ResponseEntity<Map<String, Object>> getAllCourseByParentId(Long id);
    ResponseEntity<Map<String, Object>> getReportCourse(int year);
    GetCourseResponse getCourseByName(String name);
    GetCourseResponse getCourseById(Long id);
    Long createCourse(CreateCourseRequest createCourseRequest);
    Long removeCourseById(Long id);
    Long updateCourseById(Long id, CreateCourseRequest createCourseRequest);
}