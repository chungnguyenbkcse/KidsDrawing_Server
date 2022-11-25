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
    @GetMapping(value = "/class/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeExerciseSubmissionByClassId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userGradeExerciseSubmissionService.getAllUserGradeExerciseSubmissionByClassId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/exercise/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeExerciseSubmissionByExerciseId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userGradeExerciseSubmissionService.getAllUserGradeExerciseSubmissionByExerciseId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/exercise-submission/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeExerciseSubmissionByExerciseSubmissionId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userGradeExerciseSubmissionService.getAllUserGradeExerciseSubmissionByExerciseSubmissionId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/exercise-class/{exercise_id}/{classes_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeExerciseSubmissionByExerciseAndClass(@PathVariable("exercise_id") Long exercise_id, @PathVariable("classes_id") Long classes_id) {
        return ResponseEntity.ok().body(userGradeExerciseSubmissionService.getAllUserGradeExerciseSubmissionByExerciseAndClass(exercise_id, classes_id));
    }

    @CrossOrigin
    @GetMapping(value = "/class-student/{classes_id}/{teacher_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeExerciseSubmissionByStudentAndClass(@PathVariable("classes_id") Long classes_id, @PathVariable("teacher_id") Long teacher_id) {
        return ResponseEntity.ok().body(userGradeExerciseSubmissionService.getAllUserGradeExerciseSubmissionByStudentAndClass(classes_id, teacher_id));
    }

    @CrossOrigin
    @GetMapping(value = "/class-parent/{classes_id}/{parent_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getFinalGradeAndReviewForParentAndClasses(@PathVariable("classes_id") Long classes_id, @PathVariable("parent_id") Long parent_id) {
        return ResponseEntity.ok().body(userGradeExerciseSubmissionService.getFinalGradeAndReviewForParentAndClasses(parent_id, classes_id));
    }

    @CrossOrigin
    @GetMapping(value = "/exercise-student/{exercise_id}/{teacher_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeExerciseSubmissionByStudentAndExercise(@PathVariable("exercise_id") Long exercise_id, @PathVariable("teacher_id") Long teacher_id) {
        return ResponseEntity.ok().body(userGradeExerciseSubmissionService.getAllUserGradeExerciseSubmissionByStudentAndExercise(exercise_id, teacher_id));
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
    @PutMapping(value = "/{teacher_id}/{exercise_submission_id}")
    public ResponseEntity<String> updateUserGradeExerciseSubmission(@PathVariable("teacher_id") Long teacher_id, @PathVariable("exercise_submission_id") Long exercise_submission_id, @RequestBody CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest) {
        Long userGradeExerciseSubmissionId = userGradeExerciseSubmissionService.updateUserGradeExerciseSubmissionById(teacher_id, exercise_submission_id, createUserGradeExerciseSubmissionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userGradeExerciseSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{teacher_id}/{exercise_submission_id}")
    public ResponseEntity<GetUserGradeExerciseSubmissionResponse> getUserGradeExerciseSubmissionById(@PathVariable("teacher_id") Long teacher_id, @PathVariable("exercise_submission_id") Long exercise_submission_id) {
        return ResponseEntity.ok().body(userGradeExerciseSubmissionService.getUserGradeExerciseSubmissionById(teacher_id, exercise_submission_id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{teacher_id}/{exercise_submission_id}")
    public ResponseEntity<String> deleteUserGradeExerciseSubmissionById(@PathVariable("teacher_id") Long teacher_id, @PathVariable("exercise_submission_id") Long exercise_submission_id) {
        Long userGradeExerciseSubmissionId = userGradeExerciseSubmissionService.removeUserGradeExerciseSubmissionById(teacher_id, exercise_submission_id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userGradeExerciseSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
