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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.kidsdrawing.dto.CreateScheduleItemRequest;
import com.app.kidsdrawing.dto.GetScheduleItemResponse;
import com.app.kidsdrawing.service.ScheduleItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/schedule-item")
public class ScheduleItemController {
    private final ScheduleItemService scheduleItemService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createScheduleItem(@RequestBody CreateScheduleItemRequest createScheduleItemRequest) {
        Long scheduleItemId = scheduleItemService.createScheduleItem(createScheduleItemRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{scheduleItemId}")
                .buildAndExpand(scheduleItemId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateScheduleItem(@PathVariable Long id, @RequestBody CreateScheduleItemRequest createScheduleItemRequest) {
        Long scheduleItemId = scheduleItemService.updateScheduleItemById(id,createScheduleItemRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(scheduleItemId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllScheduleItems(@RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(scheduleItemService.getAllScheduleItem(page, size));
    } 

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetScheduleItemResponse> getScheduleItemById(@PathVariable Long id) {
        return ResponseEntity.ok().body(scheduleItemService.getScheduleItemById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteScheduleItemById(@PathVariable Long id) {
        Long scheduleItemId = scheduleItemService.removeScheduleItemById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(scheduleItemId).toUri();
        return ResponseEntity.created(location).build();
    }
}
