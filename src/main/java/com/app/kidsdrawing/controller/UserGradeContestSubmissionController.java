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
    @GetMapping(value = "/teacher/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeContestSubmissionByTeacherId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userGradeContestSubmissionService.getAllUserGradeContestSubmissionByTeacherId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/student/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeContestSubmissionByStudentId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userGradeContestSubmissionService.getAllUserGradeContestSubmissionByStudentId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/contest/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeContestSubmissionByContestId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userGradeContestSubmissionService.getAllUserGradeContestSubmissionByContestId(id));
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
    @PutMapping(value = "/{teacher_id}/{contest_submission_id}")
    public ResponseEntity<String> updateUserGradeContestSubmission(@PathVariable("teacher_id") Long teacher_id, @PathVariable("contest_submission_id") Long contest_submission_id, @RequestBody CreateUserGradeContestSubmissionRequest createUserGradeContestSubmissionRequest) {
        Long userGradeContestSubmissionId = userGradeContestSubmissionService.updateUserGradeContestSubmissionById(teacher_id, contest_submission_id, createUserGradeContestSubmissionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userGradeContestSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{teacher_id}/{contest_submission_id}")
    public ResponseEntity<GetUserGradeContestSubmissionResponse> getUserGradeContestSubmissionById(@PathVariable("teacher_id") Long teacher_id, @PathVariable("contest_submission_id") Long contest_submission_id) {
        return ResponseEntity.ok().body(userGradeContestSubmissionService.getUserGradeContestSubmissionById(teacher_id, contest_submission_id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{teacher_id}/{contest_submission_id}")
    public ResponseEntity<String> deleteUserGradeContestSubmissionById(@PathVariable("teacher_id") Long teacher_id, @PathVariable("contest_submission_id") Long contest_submission_id) {
        Long userGradeContestSubmissionId = userGradeContestSubmissionService.removeUserGradeContestSubmissionById(teacher_id, contest_submission_id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userGradeContestSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
