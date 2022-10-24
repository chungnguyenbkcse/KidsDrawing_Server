package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateScheduleRequest;
import com.app.kidsdrawing.dto.GetScheduleResponse;

public interface ScheduleService {
    ResponseEntity<Map<String, Object>> getAllSchedule();
    ResponseEntity<Map<String, Object>> getAllScheduleBySemesterClassId(UUID id);
    GetScheduleResponse getScheduleById(UUID id);
    UUID createSchedule(CreateScheduleRequest createScheduleRequest);
    UUID removeScheduleById(UUID id);
    UUID updateScheduleById(UUID id, CreateScheduleRequest createScheduleRequest);
}
