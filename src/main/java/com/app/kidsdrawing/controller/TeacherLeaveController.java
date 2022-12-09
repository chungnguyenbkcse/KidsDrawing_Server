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

import com.app.kidsdrawing.dto.CreateReviewTeacherLeaveRequest;
import com.app.kidsdrawing.dto.CreateTeacherLeaveRequest;
import com.app.kidsdrawing.dto.GetTeacherLeaveResponse;
import com.app.kidsdrawing.service.TeacherLeaveService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/teacher-leave")
public class TeacherLeaveController {
    private final TeacherLeaveService  teacherLeaveService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTeacherLeave() {
        return ResponseEntity.ok().body(teacherLeaveService.getAllTeacherLeave());
    } 

    @CrossOrigin
    @GetMapping(value = "/class/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTeacherLeave(@PathVariable Long id) {
        return ResponseEntity.ok().body(teacherLeaveService.getTeacherLeaveByClassId(id));
    }
    
    @CrossOrigin
    @GetMapping(value = "/substitute-teacher/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTeacherLeaveByTeacher(@PathVariable Long id) {
        return ResponseEntity.ok().body(teacherLeaveService.getTeacherLeaveByTeacherSubstitute(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<GetTeacherLeaveResponse> createTeacherLeave(@RequestBody CreateTeacherLeaveRequest createTeacherLeaveRequest) {
        return ResponseEntity.ok().body(teacherLeaveService.createTeacherLeave(createTeacherLeaveRequest));
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateTeacherLeave(@PathVariable Long id, @RequestBody CreateTeacherLeaveRequest createTeacherLeaveRequest) {
        Long TeacherLeaveId = teacherLeaveService.updateTeacherLeaveById(id,createTeacherLeaveRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(TeacherLeaveId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/admin/{id}")
    public ResponseEntity<GetTeacherLeaveResponse> updateStatusTeacherLeave(@PathVariable Long id, @RequestBody CreateReviewTeacherLeaveRequest createReviewTeacherLeaveRequest) {
        return ResponseEntity.ok().body(teacherLeaveService.updateStatusTeacherLeaveById(id, createReviewTeacherLeaveRequest));
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetTeacherLeaveResponse> getTeacherLeaveById(@PathVariable Long id) {
        return ResponseEntity.ok().body(teacherLeaveService.getTeacherLeaveById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTeacherLeaveById(@PathVariable Long id) {
        Long TeacherLeaveId = teacherLeaveService.removeTeacherLeaveById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(TeacherLeaveId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
