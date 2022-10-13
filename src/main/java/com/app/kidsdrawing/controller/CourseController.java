package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

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
        UUID courseId = courseService.createCourse(createCourseRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{courseId}")
                .buildAndExpand(courseId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable UUID id, @RequestBody CreateCourseRequest createCourseRequest) {
        UUID courseId = courseService.updateCourseById(id,createCourseRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(courseId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllCourses() {
        return ResponseEntity.ok().body(courseService.getAllCourse());
    }

    @CrossOrigin
    @GetMapping(value = "/teacher-new/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllCourses(@PathVariable UUID id) {
        return ResponseEntity.ok().body(courseService.getAllCourseForTeacher(id));
    }

    @CrossOrigin
    @GetMapping(value = "/parent-new/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllCourseNewByParentId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(courseService.getAllCourseNewByParentId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/student/total/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getTotalCourseForStudent(@PathVariable UUID id) {
        return ResponseEntity.ok().body(courseService.getTotalCourseForStudent(id));
    }
    
    @CrossOrigin
    @GetMapping(value = "/art-type/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllCoursesByArtTypeid(@PathVariable UUID id) {
        return ResponseEntity.ok().body(courseService.getAllCourseByArtTypeId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/total")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getTotalCourse() {
        return ResponseEntity.ok().body(courseService.getTotalCourse());
    }

    @CrossOrigin
    @GetMapping(value = "/parent/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllCourseByParentId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(courseService.getAllCourseByParentId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/student/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllCourseByStudentId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(courseService.getAllCourseByStudentId(id));
    }


    @CrossOrigin
    @GetMapping(value = "/student-new/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllCourseNewByStudentId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(courseService.getAllCourseNewByStudentId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/report/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getReportCourse(@PathVariable int id) {
        return ResponseEntity.ok().body(courseService.getReportCourse(id));
    }

    @CrossOrigin
    @GetMapping(value = "/teacher/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllCoursesByTeacherid(@PathVariable UUID id) {
        return ResponseEntity.ok().body(courseService.getAllCourseByTeacherId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/art-level/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllCoursesByArtLevelid(@PathVariable UUID id) {
        return ResponseEntity.ok().body(courseService.getAllCourseByArtLevelId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/art-age/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllCoursesByArtAgeid(@PathVariable UUID id) {
        return ResponseEntity.ok().body(courseService.getAllCourseByArtAgeId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/id/{id}")
    public ResponseEntity<GetCourseResponse> getCourseById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(courseService.getCourseById(id));
    }

    @CrossOrigin
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<GetCourseResponse> getCourseById(@PathVariable String name) {
        return ResponseEntity.ok().body(courseService.getCourseByName(name));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCourseById(@PathVariable UUID id) {
        UUID courseId = courseService.removeCourseById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(courseId).toUri();
        return ResponseEntity.created(location).build();
    }
}
