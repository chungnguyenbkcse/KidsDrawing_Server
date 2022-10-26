package com.app.kidsdrawing.controller;

import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.kidsdrawing.service.UserGradeExerciseSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/final-course")
public class FinalCourseController {
    private final UserGradeExerciseSubmissionService  userGradeExerciseSubmissionService;

    @CrossOrigin
    @GetMapping(value = "/{student_id}/{classes_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getFinalGradeAndReviewForStudentAndClasses(@PathVariable("student_id") Long student_id, @PathVariable("classes_id") Long classes_id) {
        return ResponseEntity.ok().body(userGradeExerciseSubmissionService.getFinalGradeAndReviewForStudentAndClasses(student_id, classes_id));
    }
}
