package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateHolidayRequest;
import com.app.kidsdrawing.dto.CreateSemesterRequest;
import com.app.kidsdrawing.dto.GetSemesterResponse;

public interface SemesterService {
    ResponseEntity<Map<String, Object>> getAllSemester();
    ResponseEntity<Map<String, Object>> getAllSemesterNext();
    UUID setCalenderForSemester(UUID id, CreateHolidayRequest createHolidayResquest);
    UUID setClassForSemester(UUID id, int partion, int min, int max, CreateHolidayRequest createHolidayResquest);
    GetSemesterResponse getSemesterById(UUID id);
    UUID createSemester(CreateSemesterRequest createSemesterRequest);
    UUID removeSemesterById(UUID id);
    UUID updateSemesterById(UUID id, CreateSemesterRequest createSemesterRequest);
}
