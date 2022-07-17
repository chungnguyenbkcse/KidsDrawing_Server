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

import com.app.kidsdrawing.dto.CreateUserGradeExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetUserGradeExerciseSubmissionResponse;
import com.app.kidsdrawing.service.UserGradeExerciseSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user-grade-exercise-submission")
public class UserGradeExerciseSubmissionController {
    private final UserGradeExerciseSubmissionService  userGradeExerciseSubmissionService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeExerciseSubmission() {
        return ResponseEntity.ok().body(userGradeExerciseSubmissionService.getAllUserGradeExerciseSubmission());
    }
    
    @CrossOrigin
    @GetMapping(value = "/student/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeExerciseSubmissionByStudentId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userGradeExerciseSubmissionService.getAllUserGradeExerciseSubmissionByStudentId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/exercise-submission/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeExerciseSubmissionByExerciseSubmissionId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userGradeExerciseSubmissionService.getAllUserGradeExerciseSubmissionByExerciseSubmissionId(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createUserGradeExerciseSubmission(@RequestBody CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest) {
        Long userGradeExerciseSubmissionId = userGradeExerciseSubmissionService.createUserGradeExerciseSubmission(createUserGradeExerciseSubmissionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userGradeExerciseSubmissionId}")
                .buildAndExpand(userGradeExerciseSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{student_id}/{submission_id}")
    public ResponseEntity<String> updateUserGradeExerciseSubmission(@PathVariable("student_id") Long student_id, @PathVariable("submission_id") Long submission_id, @RequestBody CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest) {
        Long userGradeExerciseSubmissionId = userGradeExerciseSubmissionService.updateUserGradeExerciseSubmissionById(student_id, submission_id, createUserGradeExerciseSubmissionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userGradeExerciseSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserGradeExerciseSubmissionResponse> getUserGradeExerciseSubmissionById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userGradeExerciseSubmissionService.getUserGradeExerciseSubmissionById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserGradeExerciseSubmissionById(@PathVariable Long id) {
        Long userGradeExerciseSubmissionId = userGradeExerciseSubmissionService.removeUserGradeExerciseSubmissionById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userGradeExerciseSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
