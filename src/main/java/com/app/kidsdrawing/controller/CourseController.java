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

import com.app.kidsdrawing.dto.CreateCourseRequest;
import com.app.kidsdrawing.dto.GetCourseResponse;
import com.app.kidsdrawing.service.CourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/course")
public class CourseController {
    private final CourseService courseService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createCourse(@RequestBody CreateCourseRequest createCourseRequest) {
        Long courseId = courseService.createCourse(createCourseRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{courseId}")
                .buildAndExpand(courseId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable Long id, @RequestBody CreateCourseRequest createCourseRequest) {
        Long courseId = courseService.updateCourseById(id,createCourseRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(courseId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllCourses(@RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(courseService.getAllCourse(page, size));
    }
    
    @CrossOrigin
    @GetMapping(value = "/art-type/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllCoursesByArtTypeid(@PathVariable Long id, @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(courseService.getAllCourseByArtTypeId(page, size, id));
    }

    @CrossOrigin
    @GetMapping(value = "/art-level/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllCoursesByArtLevelid(@PathVariable Long id, @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(courseService.getAllCourseByArtLevelId(page, size, id));
    }

    @CrossOrigin
    @GetMapping(value = "/art-age/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllCoursesByArtAgeid(@PathVariable Long id, @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(courseService.getAllCourseByArtAgeId(page, size, id));
    }

    @CrossOrigin
    @GetMapping(value = "/id/{id}")
    public ResponseEntity<GetCourseResponse> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok().body(courseService.getCourseById(id));
    }

    @CrossOrigin
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<GetCourseResponse> getCourseById(@PathVariable String name) {
        return ResponseEntity.ok().body(courseService.getCourseByName(name));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCourseById(@PathVariable Long id) {
        Long courseId = courseService.removeCourseById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(courseId).toUri();
        return ResponseEntity.created(location).build();
    }
}