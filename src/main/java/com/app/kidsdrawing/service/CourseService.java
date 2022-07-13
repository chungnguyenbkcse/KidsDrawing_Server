package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateCourseRequest;
import com.app.kidsdrawing.dto.GetCourseResponse;

public interface CourseService {
    ResponseEntity<Map<String, Object>> getAllCourse(int page, int size);
    ResponseEntity<Map<String, Object>> getAllCourseByArtTypeId(int page, int size, Long id);
    ResponseEntity<Map<String, Object>> getAllCourseByArtAgeId(int page, int size, Long id);
    ResponseEntity<Map<String, Object>> getAllCourseByArtLevelId(int page, int size, Long id);
    GetCourseResponse getCourseByName(String name);
    GetCourseResponse getCourseById(Long id);
    Long createCourse(CreateCourseRequest createCourseRequest);
    Long removeCourseById(Long id);
    Long updateCourseById(Long id, CreateCourseRequest createCourseRequest);
}