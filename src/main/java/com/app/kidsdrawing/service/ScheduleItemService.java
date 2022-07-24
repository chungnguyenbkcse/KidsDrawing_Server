package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateScheduleItemRequest;
import com.app.kidsdrawing.dto.GetScheduleItemResponse;

public interface ScheduleItemService {
    ResponseEntity<Map<String, Object>> getAllScheduleItem(int page, int size);
    ResponseEntity<Map<String, Object>> getAllScheduleItemByScheduleId(int page, int size, Long id);
    GetScheduleItemResponse getScheduleItemById(Long id);
    Long createScheduleItem(CreateScheduleItemRequest createScheduleItemRequest);
    Long removeScheduleItemById(Long id);
    Long updateScheduleItemById(Long id, CreateScheduleItemRequest createScheduleItemRequest);
}
