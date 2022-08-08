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

import com.app.kidsdrawing.dto.CreateTeacherRegisterQualificationRequest;
import com.app.kidsdrawing.dto.GetTeacherRegisterQualificationResponse;
import com.app.kidsdrawing.service.TeacherRegisterQualificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/teacher-register-level")
public class TeacherRegisterQualificationController {
    private final TeacherRegisterQualificationService  teacherRegisterQualificationService;

    @CrossOrigin
    @GetMapping(value = "/teacher/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTeacherRegisterQualificationByTeacherId(@PathVariable Long id) {
        return ResponseEntity.ok().body(teacherRegisterQualificationService.getAllTeacherRegisterQualificationByTeacherId(id));
    }
    
    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTeacherRegisterQualification() {
        return ResponseEntity.ok().body(teacherRegisterQualificationService.getAllTeacherRegisterQualification());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createTeacherRegisterQualification(@RequestBody CreateTeacherRegisterQualificationRequest createTeacherRegisterQualificationRequest) {
        Long teacherRegisterQualificationId = teacherRegisterQualificationService.createTeacherRegisterQualification(createTeacherRegisterQualificationRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{teacherRegisterQualificationId}")
                .buildAndExpand(teacherRegisterQualificationId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateTeacherRegisterQualification(@PathVariable Long id, @RequestBody CreateTeacherRegisterQualificationRequest createTeacherRegisterQualificationRequest) {
        Long teacherRegisterQualificationId = teacherRegisterQualificationService.updateTeacherRegisterQualificationById(id,createTeacherRegisterQualificationRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(teacherRegisterQualificationId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetTeacherRegisterQualificationResponse> getTeacherRegisterQualificationById(@PathVariable Long id) {
        return ResponseEntity.ok().body(teacherRegisterQualificationService.getTeacherRegisterQualificationById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTeacherRegisterQualificationById(@PathVariable Long id) {
        Long teacherRegisterQualificationId = teacherRegisterQualificationService.removeTeacherRegisterQualificationById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(teacherRegisterQualificationId).toUri();
        return ResponseEntity.created(location).build();
    }
}
