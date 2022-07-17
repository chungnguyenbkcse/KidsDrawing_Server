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

import com.app.kidsdrawing.dto.CreateUserGradeContestSubmissionRequest;
import com.app.kidsdrawing.dto.GetUserGradeContestSubmissionResponse;
import com.app.kidsdrawing.service.UserGradeContestSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user-grade-contest-submission")
public class UserGradeContestSubmissionController {
    private final UserGradeContestSubmissionService  userGradeContestSubmissionService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeContestSubmission() {
        return ResponseEntity.ok().body(userGradeContestSubmissionService.getAllUserGradeContestSubmission());
    }
    
    @CrossOrigin
    @GetMapping(value = "/student/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeContestSubmissionByStudentId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userGradeContestSubmissionService.getAllUserGradeContestSubmissionByStudentId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/contest-submission/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeContestSubmissionByContestSubmissionId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userGradeContestSubmissionService.getAllUserGradeContestSubmissionByContestSubmissionId(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createUserGradeContestSubmission(@RequestBody CreateUserGradeContestSubmissionRequest createUserGradeContestSubmissionRequest) {
        Long userGradeContestSubmissionId = userGradeContestSubmissionService.createUserGradeContestSubmission(createUserGradeContestSubmissionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userGradeContestSubmissionId}")
                .buildAndExpand(userGradeContestSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{student_id}/{submission_id}")
    public ResponseEntity<String> updateUserGradeContestSubmission(@PathVariable("student_id") Long student_id, @PathVariable("submission_id") Long submission_id, @RequestBody CreateUserGradeContestSubmissionRequest createUserGradeContestSubmissionRequest) {
        Long userGradeContestSubmissionId = userGradeContestSubmissionService.updateUserGradeContestSubmissionById(student_id, submission_id, createUserGradeContestSubmissionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userGradeContestSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserGradeContestSubmissionResponse> getUserGradeContestSubmissionById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userGradeContestSubmissionService.getUserGradeContestSubmissionById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserGradeContestSubmissionById(@PathVariable Long id) {
        Long userGradeContestSubmissionId = userGradeContestSubmissionService.removeUserGradeContestSubmissionById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userGradeContestSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
