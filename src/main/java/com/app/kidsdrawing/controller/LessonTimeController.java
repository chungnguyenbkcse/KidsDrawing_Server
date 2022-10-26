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

import com.app.kidsdrawing.dto.CreateLessonTimeRequest;
import com.app.kidsdrawing.dto.GetLessonTimeResponse;
import com.app.kidsdrawing.service.LessonTimeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/lesson-time")
public class LessonTimeController {
    private final LessonTimeService lessonTimeService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createLessonTime(@RequestBody CreateLessonTimeRequest createLessonTimeRequest) {
        Long lessonTimeId = lessonTimeService.createLessonTime(createLessonTimeRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{lessonTimeId}")
                .buildAndExpand(lessonTimeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateLessonTime(@PathVariable Long id, @RequestBody CreateLessonTimeRequest createLessonTimeRequest) {
        Long lessonTimeId = lessonTimeService.updateLessonTimeById(id,createLessonTimeRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(lessonTimeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllLessonTimes() {
        return ResponseEntity.ok().body(lessonTimeService.getAllLessonTime());
    } 

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetLessonTimeResponse> getLessonTimeById(@PathVariable Long id) {
        return ResponseEntity.ok().body(lessonTimeService.getLessonTimeById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteLessonTimeById(@PathVariable Long id) {
        Long lessonTimeId = lessonTimeService.removeLessonTimeById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(lessonTimeId).toUri();
        return ResponseEntity.created(location).build();
    }
}
