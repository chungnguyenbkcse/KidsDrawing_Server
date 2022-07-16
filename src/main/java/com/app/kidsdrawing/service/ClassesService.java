package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateClassRequest;
import com.app.kidsdrawing.dto.GetClassResponse;

public interface ClassesService {
    ResponseEntity<Map<String, Object>> getAllClass();
    GetClassResponse getClassById(Long id);
    Long createClass(CreateClassRequest createClassRequest);
    Long removeClassById(Long id);
    Long updateClassById(Long id, CreateClassRequest createClassRequest);
}
