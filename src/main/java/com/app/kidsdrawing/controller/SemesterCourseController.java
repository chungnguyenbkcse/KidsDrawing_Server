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

import com.app.kidsdrawing.dto.CreateSemesterCourseRequest;
import com.app.kidsdrawing.dto.GetSemesterCourseResponse;
import com.app.kidsdrawing.service.SemesterCourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/semester-course")
public class SemesterCourseController {
    private final SemesterCourseService semesterCourseService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createSemesterCourse(@RequestBody CreateSemesterCourseRequest createSemesterCourseRequest) {
        Long semesterCourseId = semesterCourseService.createSemesterCourse(createSemesterCourseRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{semesterCourseId}")
                .buildAndExpand(semesterCourseId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateSemesterCourse(@PathVariable Long id, @RequestBody CreateSemesterCourseRequest createSemesterCourseRequest) {
        Long semesterCourseId = semesterCourseService.updateSemesterCourseById(id,createSemesterCourseRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(semesterCourseId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSemesterCourses(@RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(semesterCourseService.getAllSemesterCourse(page, size));
    } 

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetSemesterCourseResponse> getSemesterCourseById(@PathVariable Long id) {
        return ResponseEntity.ok().body(semesterCourseService.getSemesterCourseById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteSemesterCourseById(@PathVariable Long id) {
        Long SemesterCourseId = semesterCourseService.removeSemesterCourseById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(SemesterCourseId).toUri();
        return ResponseEntity.created(location).build();
    }
}
