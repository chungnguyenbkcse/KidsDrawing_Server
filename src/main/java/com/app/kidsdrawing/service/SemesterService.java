package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateSemesterRequest;
import com.app.kidsdrawing.dto.GetSemesterResponse;

public interface SemesterService {
    ResponseEntity<Map<String, Object>> getAllSemester(int page, int size);
    GetSemesterResponse getSemesterById(Long id);
    Long createSemester(CreateSemesterRequest createSemesterRequest);
    Long removeSemesterById(Long id);
    Long updateSemesterById(Long id, CreateSemesterRequest createSemesterRequest);
}
