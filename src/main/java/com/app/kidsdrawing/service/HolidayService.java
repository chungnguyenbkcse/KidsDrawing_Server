package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateHolidayRequest;
import com.app.kidsdrawing.dto.GetHolidayResponse;

public interface HolidayService {
    ResponseEntity<Map<String, Object>> getAllHoliday();
    GetHolidayResponse getHolidayById(UUID id);
    UUID createHoliday(CreateHolidayRequest createHolidayRequest);
    UUID removeHolidayById(UUID id);
    UUID updateHolidayById(UUID id, CreateHolidayRequest createHolidayRequest);
}
