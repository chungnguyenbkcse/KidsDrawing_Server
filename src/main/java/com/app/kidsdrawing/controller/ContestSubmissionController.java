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

import com.app.kidsdrawing.dto.CreateContestSubmissionRequest;
import com.app.kidsdrawing.dto.GetContestSubmissionResponse;
import com.app.kidsdrawing.service.ContestSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/contest-submission")
public class ContestSubmissionController {
    private final ContestSubmissionService  contestSubmissionService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllContestSubmission() {
        return ResponseEntity.ok().body(contestSubmissionService.getAllContestSubmission());
    }
    
    @CrossOrigin
    @GetMapping(value = "/student/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllContestSubmissionByStudentId(@PathVariable Long id) {
        return ResponseEntity.ok().body(contestSubmissionService.getAllContestSubmissionByStudentId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/contest/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllContestSubmissionByContestId(@PathVariable Long id) {
        return ResponseEntity.ok().body(contestSubmissionService.getAllContestSubmissionByContestId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/contest-teacher/{contest_id}/{teacher_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllContestSubmissionByContestId(@PathVariable("contest_id") Long contest_id, @PathVariable("teacher_id") Long teacher_id) {
        return ResponseEntity.ok().body(contestSubmissionService.getAllContestSubmissionByTeacherAndContest(teacher_id, contest_id));
    }

    @CrossOrigin
    @GetMapping(value = "/contest-student/{contest_id}/{student_id}")
    public ResponseEntity<GetContestSubmissionResponse> getContestSubmissionByConetestAndStudent(@PathVariable("contest_id") Long contest_id, @PathVariable("student_id") Long student_id) {
        return ResponseEntity.ok().body(contestSubmissionService.getContestSubmissionByConetestAndStudent(contest_id, student_id));
    }

    @CrossOrigin
    @GetMapping(value = "/check-generation/{id}")
    public ResponseEntity<String> checkGenerationContestSubmissionForTeacher(@PathVariable Long id) {
        Long contestSubmissionId = contestSubmissionService.checkGenerationContestSubmissionForTeacher(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{contestSubmissionId}")
                .buildAndExpand(contestSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createContestSubmission(@RequestBody CreateContestSubmissionRequest createContestSubmissionRequest) {
        Long contestSubmissionId = contestSubmissionService.createContestSubmission(createContestSubmissionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{contestSubmissionId}")
                .buildAndExpand(contestSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }


    @CrossOrigin
    @PostMapping(value = "/contest/{id}")
    public ResponseEntity<String> createContestSubmission(@PathVariable Long id) {
        Long contestSubmissionId = contestSubmissionService.generationContestSubmissionForTeacher(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{contestSubmissionId}")
                .buildAndExpand(contestSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/student")
    public ResponseEntity<String> updateContestSubmission(@RequestBody CreateContestSubmissionRequest createContestSubmissionRequest) {
        Long contestSubmissionId = contestSubmissionService.updateContestSubmissionByStudent(createContestSubmissionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(contestSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/teacher")
    public ResponseEntity<String> updateContestSubmissionTeacher(@RequestBody CreateContestSubmissionRequest createContestSubmissionRequest) {
        Long contestSubmissionId = contestSubmissionService.updateContestSubmissionByTeacher(createContestSubmissionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(contestSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @DeleteMapping(value = "/{contest_id}/{student_id}")
    public ResponseEntity<String> deleteContestSubmissionById(@PathVariable("contest_id") Long contest_id, @PathVariable("student_id") Long student_id) {
        Long contestSubmissionId = contestSubmissionService.removeContestSubmissionById(contest_id, student_id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(contestSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
