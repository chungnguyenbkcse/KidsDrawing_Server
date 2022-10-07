package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateCourseRequest;
import com.app.kidsdrawing.dto.GetCourseResponse;

public interface CourseService {
    ResponseEntity<Map<String, Object>> getAllCourse();
    ResponseEntity<Map<String, Object>> getAllCourseForTeacher(UUID teacher_id);
    ResponseEntity<Map<String, Object>> getTotalCourse();
    ResponseEntity<Map<String, Object>> getAllCourseByArtTypeId(UUID id);
    ResponseEntity<Map<String, Object>> getAllCourseByArtAgeId(UUID id);
    ResponseEntity<Map<String, Object>> getAllCourseByArtLevelId(UUID id);
    ResponseEntity<Map<String, Object>> getAllCourseByTeacherId(UUID id);
    ResponseEntity<Map<String, Object>> getAllCourseByParentId(UUID id);
    ResponseEntity<Map<String, Object>> getAllCourseByStudentId(UUID id);
    ResponseEntity<Map<String, Object>> getAllCourseNewByStudentId(UUID id);
    ResponseEntity<Map<String, Object>> getReportCourse(int year);
    GetCourseResponse getCourseByName(String name);
    GetCourseResponse getCourseById(UUID id);
    UUID createCourse(CreateCourseRequest createCourseRequest);
    UUID removeCourseById(UUID id);
    UUID updateCourseById(UUID id, CreateCourseRequest createCourseRequest);
}