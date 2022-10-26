package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateHolidayRequest;
import com.app.kidsdrawing.dto.CreateSemesterRequest;
import com.app.kidsdrawing.dto.GetSemesterResponse;

public interface SemesterService {
    ResponseEntity<Map<String, Object>> getAllSemester();
    ResponseEntity<Map<String, Object>> getAllSemesterNext();
    Long setClassForSemester(Long id, int partion, int min, int max, CreateHolidayRequest createHolidayResquest);
    GetSemesterResponse getSemesterById(Long id);
    Long createSemester(CreateSemesterRequest createSemesterRequest);
    Long removeSemesterById(Long id);
    Long updateSemesterById(Long id, CreateSemesterRequest createSemesterRequest);
}
