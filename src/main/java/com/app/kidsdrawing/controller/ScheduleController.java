package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.kidsdrawing.dto.CreateScheduleRequest;
import com.app.kidsdrawing.dto.GetScheduleResponse;
import com.app.kidsdrawing.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<GetScheduleResponse> createSchedule(@RequestBody CreateScheduleRequest createScheduleRequest) {
        Long scheduleId = scheduleService.createSchedule(createScheduleRequest);
        return ResponseEntity.ok().body(scheduleService.getScheduleById(scheduleId));
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<GetScheduleResponse> updateSchedule(@PathVariable Long id, @RequestBody CreateScheduleRequest createScheduleRequest) {
        Long scheduleId = scheduleService.updateScheduleById(id,createScheduleRequest);
        ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(scheduleId).toUri();
        return ResponseEntity.ok().body(scheduleService.getScheduleById(scheduleId));
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSchedules() {
        return ResponseEntity.ok().body(scheduleService.getAllSchedule());
    }
    
    @CrossOrigin
    @GetMapping(value = "/semester-class/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSchedulesBySemesterClass(@PathVariable Long id) {
        return ResponseEntity.ok().body(scheduleService.getAllScheduleBySemesterClassId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetScheduleResponse> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok().body(scheduleService.getScheduleById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteScheduleById(@PathVariable Long id) {
        Long scheduleId = scheduleService.removeScheduleById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(scheduleId).toUri();
        return ResponseEntity.created(location).build();
    }
}
