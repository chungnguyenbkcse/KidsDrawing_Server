package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateClassRequest;
import com.app.kidsdrawing.dto.GetClassResponse;

public interface ClassesService {
    ResponseEntity<Map<String, Object>> getAllClass();
    ResponseEntity<Map<String, Object>> getInforDetailAllClass();
    ResponseEntity<Map<String, Object>> getInforDetailOfClass(Long id);
    ResponseEntity<Map<String, Object>> getInforScheduleAllClass();
    ResponseEntity<Map<String, Object>> getInforScheduleAllChild(Long parent_id);
    ResponseEntity<Map<String, Object>> getInforScheduleChild(Long child_id);
    ResponseEntity<Map<String, Object>> getInforDetailOfClassByTeacherId(Long id);
    GetClassResponse getClassById(Long id);
    Long createClass(CreateClassRequest createClassRequest);
    Long removeClassById(Long id);
    Long updateClassById(Long id, CreateClassRequest createClassRequest);
}
