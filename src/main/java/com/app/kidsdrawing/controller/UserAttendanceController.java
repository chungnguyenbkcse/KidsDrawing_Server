package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.kidsdrawing.dto.CreateUserAttendanceRequest;
import com.app.kidsdrawing.dto.GetCheckUserAttendanceResponse;
import com.app.kidsdrawing.dto.GetUserAttendanceResponse;
import com.app.kidsdrawing.service.UserAttendanceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user-attendance")
public class UserAttendanceController {
    private final UserAttendanceService  tutorialTemplate;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserAttendance() {
        return ResponseEntity.ok().body(tutorialTemplate.getAllUserAttendance());
    }
    
    @CrossOrigin
    @GetMapping(value = "/section/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserAttendanceBySectionId(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialTemplate.getAllUserAttendanceBySection(id));
    }

    @CrossOrigin
    @GetMapping(value = "/student/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserAttendanceByStudentId(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialTemplate.getAllUserAttendanceByStudent(id));
    }

    @CrossOrigin
    @GetMapping(value = "/classes/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserAttendanceByClassesId(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialTemplate.getAllUserAttendanceByClass(id));
    }

    @CrossOrigin
    @GetMapping(value = "/section-student/{section_id}/{student_id}")
    public ResponseEntity<GetUserAttendanceResponse> getAllUserAttendanceBySectionAndStudent(@PathVariable("section_id") Long section_id, @PathVariable("student_id") Long student_id) {
        return ResponseEntity.ok().body(tutorialTemplate.getAllUserAttendanceBySectionAndStudent(section_id, student_id));
    }

    @CrossOrigin
    @GetMapping(value = "/section-student-check/{section_id}/{student_id}")
    public ResponseEntity<GetCheckUserAttendanceResponse> checkUserAttendanceBySectionAndStudent(@PathVariable("section_id") Long section_id, @PathVariable("student_id") Long student_id) {
        return ResponseEntity.ok().body(tutorialTemplate.checkUserAttendanceBySectionAndStudent(section_id, student_id));
    }

    @CrossOrigin
    @PutMapping(value = "/section-student/{section_id}/{student_id}")
    public ResponseEntity<GetUserAttendanceResponse> putUserAttendanceBySectionAndStudent(@PathVariable("section_id") Long section_id, @PathVariable("student_id") Long student_id) {
        Long tutorialPageId = tutorialTemplate.updateUserAttendanceBySectionAndStudent(section_id, student_id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{tutorialPageId}")
                .buildAndExpand(tutorialPageId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/class-student/{class_id}/{student_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserAttendanceByClassAndStudent(@PathVariable("class_id") Long class_id, @PathVariable("student_id") Long student_id) {
        return ResponseEntity.ok().body(tutorialTemplate.getAllUserAttendanceByClassAndStudent(class_id, student_id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createUserAttendance(@RequestBody CreateUserAttendanceRequest createUserAttendanceRequest) {
        Long tutorialPageId = tutorialTemplate.createUserAttendance(createUserAttendanceRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{tutorialPageId}")
                .buildAndExpand(tutorialPageId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateUserAttendance(@RequestBody CreateUserAttendanceRequest createUserAttendanceRequest) {
        Long tutorialPageId = tutorialTemplate.updateUserAttendanceById(createUserAttendanceRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(tutorialPageId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
