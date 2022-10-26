package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateHolidayRequest;
import com.app.kidsdrawing.dto.GetHolidayResponse;

public interface HolidayService {
    ResponseEntity<Map<String, Object>> getAllHoliday();
    GetHolidayResponse getHolidayById(Long id);
    Long createHoliday(CreateHolidayRequest createHolidayRequest);
    Long removeHolidayById(Long id);
    Long updateHolidayById(Long id, CreateHolidayRequest createHolidayRequest);
}
