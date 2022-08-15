package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateSemesterClassRequest;
import com.app.kidsdrawing.dto.GetSemesterClassResponse;

public interface SemesterClassService {
    ResponseEntity<Map<String, Object>> getAllSemesterClass(int page, int size);
    ResponseEntity<Map<String, Object>> getAllSemesterClassBySemester(int page, int size, Long id);
    ResponseEntity<Map<String, Object>> getAllSemesterClassByCourse(int page, int size, Long id);
    GetSemesterClassResponse getSemesterClassById(Long id);
    Long createSemesterClass(CreateSemesterClassRequest createSemesterClassRequest);
    Long removeSemesterClassById(Long id);
    Long updateSemesterClassById(Long id, CreateSemesterClassRequest createSemesterClassRequest);
}