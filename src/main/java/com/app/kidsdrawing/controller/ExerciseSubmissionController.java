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

import com.app.kidsdrawing.dto.CreateExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetExerciseSubmissionResponse;
import com.app.kidsdrawing.service.ExerciseSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/exercise-submission")
public class ExerciseSubmissionController {
    private final ExerciseSubmissionService  exerciseSubmissionService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllExerciseSubmission() {
        return ResponseEntity.ok().body(exerciseSubmissionService.getAllExerciseSubmission());
    }
    
    @CrossOrigin
    @GetMapping(value = "/student/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllExerciseSubmissionByStudentId(@PathVariable Long id) {
        return ResponseEntity.ok().body(exerciseSubmissionService.getAllExerciseSubmissionByStudentId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/exercise/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllExerciseSubmissionByExerciseId(@PathVariable Long id) {
        return ResponseEntity.ok().body(exerciseSubmissionService.getAllExerciseSubmissionByExerciseId(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createExerciseSubmission(@RequestBody CreateExerciseSubmissionRequest createExerciseSubmissionRequest) {
        Long exerciseSubmissionId = exerciseSubmissionService.createExerciseSubmission(createExerciseSubmissionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{exerciseSubmissionId}")
                .buildAndExpand(exerciseSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateExerciseSubmission(@PathVariable Long id, @RequestBody CreateExerciseSubmissionRequest createExerciseSubmissionRequest) {
        Long exerciseSubmissionId = exerciseSubmissionService.updateExerciseSubmissionById(id,createExerciseSubmissionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(exerciseSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetExerciseSubmissionResponse> getExerciseSubmissionById(@PathVariable Long id) {
        return ResponseEntity.ok().body(exerciseSubmissionService.getExerciseSubmissionById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteExerciseSubmissionById(@PathVariable Long id) {
        Long exerciseSubmissionId = exerciseSubmissionService.removeExerciseSubmissionById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(exerciseSubmissionId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
