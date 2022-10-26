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

import com.app.kidsdrawing.dto.CreateTeacherTeachSemesterRequest;
import com.app.kidsdrawing.dto.GetTeacherTeachSemesterResponse;
import com.app.kidsdrawing.service.UserRegisterTeachSemesterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/teacher-teach-semester")
public class TeacherTeachSemesterController {
    private final UserRegisterTeachSemesterService  teacherTeachSemesterService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTeacherTeachSemester() {
        return ResponseEntity.ok().body(teacherTeachSemesterService.getAllTeacherTeachSemester());
    } 


    @CrossOrigin
    @GetMapping(value = "/semester-class/schedule-class/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTeacherTeachSemesterBySemesterClassSchedule(@PathVariable Long id) {
        return ResponseEntity.ok().body(teacherTeachSemesterService.getAllTeacherTeachSemesterBySemesterClassSchedule(id));
    } 

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createTeacherTeachSemester(@RequestBody CreateTeacherTeachSemesterRequest createTeacherTeachSemesterRequest) {
        Long teacherTeachSemesterId = teacherTeachSemesterService.createTeacherTeachSemester(createTeacherTeachSemesterRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{teacherTeachSemesterId}")
                .buildAndExpand(teacherTeachSemesterId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateTeacherTeachSemester(@PathVariable Long id, @RequestBody CreateTeacherTeachSemesterRequest createTeacherTeachSemesterRequest) {
        Long teacherTeachSemesterId = teacherTeachSemesterService.updateTeacherTeachSemesterById(id,createTeacherTeachSemesterRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(teacherTeachSemesterId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetTeacherTeachSemesterResponse> getTeacherTeachSemesterById(@PathVariable Long id) {
        return ResponseEntity.ok().body(teacherTeachSemesterService.getTeacherTeachSemesterById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTeacherTeachSemesterById(@PathVariable Long id) {
        Long teacherTeachSemesterId = teacherTeachSemesterService.removeTeacherTeachSemesterById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(teacherTeachSemesterId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
