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

import com.app.kidsdrawing.dto.CreateUserAttendanceRequest;
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
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserAttendanceBySectionId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(tutorialTemplate.getAllUserAttendanceBySection(id));
    }

    @CrossOrigin
    @GetMapping(value = "/student/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserAttendanceByStudentId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(tutorialTemplate.getAllUserAttendanceByStudent(id));
    }

    @CrossOrigin
    @GetMapping(value = "/classes/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserAttendanceByClassesId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(tutorialTemplate.getAllUserAttendanceByClass(id));
    }

    @CrossOrigin
    @GetMapping(value = "/section-student/{section_id}/{student_id}")
    public ResponseEntity<GetUserAttendanceResponse> getAllUserAttendanceBySectionAndStudent(@PathVariable("section_id") UUID section_id, @PathVariable("student_id") UUID student_id) {
        return ResponseEntity.ok().body(tutorialTemplate.getAllUserAttendanceBySectionAndStudent(section_id, student_id));
    }

    @CrossOrigin
    @GetMapping(value = "/class-student/{class_id}/{student_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserAttendanceByClassAndStudent(@PathVariable("class_id") UUID class_id, @PathVariable("student_id") UUID student_id) {
        return ResponseEntity.ok().body(tutorialTemplate.getAllUserAttendanceByClassAndStudent(class_id, student_id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createUserAttendance(@RequestBody CreateUserAttendanceRequest createUserAttendanceRequest) {
        UUID tutorialPageId = tutorialTemplate.createUserAttendance(createUserAttendanceRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{tutorialPageId}")
                .buildAndExpand(tutorialPageId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateUserAttendance(@PathVariable UUID id, @RequestBody CreateUserAttendanceRequest createUserAttendanceRequest) {
        UUID tutorialPageId = tutorialTemplate.updateUserAttendanceById(id,createUserAttendanceRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(tutorialPageId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserAttendanceResponse> getUserAttendanceById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(tutorialTemplate.getUserAttendanceById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserAttendanceById(@PathVariable UUID id) {
        UUID tutorialPageId = tutorialTemplate.removeUserAttendanceById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(tutorialPageId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
