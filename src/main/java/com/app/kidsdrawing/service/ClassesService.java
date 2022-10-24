package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateClassRequest;
import com.app.kidsdrawing.dto.GetClassResponse;
import java.util.UUID;

public interface ClassesService {
    ResponseEntity<Map<String, Object>> getAllClass();
    ResponseEntity<Map<String, Object>> getInforDetailAllClass();
    ResponseEntity<Map<String, Object>> getInforDetailOfClass(UUID id);
    ResponseEntity<Map<String, Object>> getInforScheduleAllClass();
    ResponseEntity<Map<String, Object>> getInforScheduleAllChild(UUID parent_id);
    ResponseEntity<Map<String, Object>> getInforScheduleChild(UUID child_id);
    ResponseEntity<Map<String, Object>> getInforDetailOfClassByTeacherId(UUID id);
    ResponseEntity<Map<String, Object>> getClassesForStudentId(UUID id);
    ResponseEntity<Map<String, Object>> getClassesStudentForStudentId(UUID id);
    ResponseEntity<Map<String, Object>> getClassesStudentForParentId(UUID parent_id);
    GetClassResponse getClassById(UUID id);
    UUID createClass(CreateClassRequest createClassRequest);
    UUID removeClassById(UUID id);
    UUID updateClassById(UUID id, CreateClassRequest createClassRequest);
}
