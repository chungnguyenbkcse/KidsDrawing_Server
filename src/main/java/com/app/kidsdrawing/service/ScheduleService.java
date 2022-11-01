package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateScheduleRequest;
import com.app.kidsdrawing.dto.GetScheduleResponse;

public interface ScheduleService {
    ResponseEntity<Map<String, Object>> getAllSchedule();
    ResponseEntity<Map<String, Object>> getAllScheduleBySemesterClassId(Long id);
    GetScheduleResponse getScheduleById(Long id);
    Long createSchedule(CreateScheduleRequest createScheduleRequest);
    Long removeScheduleById(Long id);
    Long updateScheduleById(Long id, CreateScheduleRequest createScheduleRequest);
    Long removeScheduleBySemesterClass(Long id);
}
