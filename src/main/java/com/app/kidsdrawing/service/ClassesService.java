package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateClassRequest;
import com.app.kidsdrawing.dto.GetClassResponse;

public interface ClassesService {
    ResponseEntity<Map<String, Object>> getAllClass();
    ResponseEntity<Map<String, Object>> getInforDetailAllClass();
    ResponseEntity<Map<String, Object>> getInforDetailOfClass(Long id);
    ResponseEntity<Map<String, Object>> getInforScheduleAllClass(Long id);
    ResponseEntity<Map<String, Object>> getInforDetailOfClassByTeacherId(Long id);
    GetClassResponse getClassById(Long id);
    Long createClass(CreateClassRequest createClassRequest);
    Long removeClassById(Long id);
    Long updateClassById(Long id, CreateClassRequest createClassRequest);
}
