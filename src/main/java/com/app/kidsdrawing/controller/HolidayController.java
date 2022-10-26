package com.app.kidsdrawing.controller;

import java.net.URI;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.kidsdrawing.dto.CreateHolidayRequest;
import com.app.kidsdrawing.service.HolidayService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/holiday")
public class HolidayController {
    private final HolidayService HolidayService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createHoliday(@RequestBody CreateHolidayRequest createHolidayRequest) {
        Long HolidayId = HolidayService.createHoliday(createHolidayRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{HolidayId}")
                .buildAndExpand(HolidayId).toUri();
        return ResponseEntity.created(location).build();
    }
}
