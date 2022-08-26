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

import com.app.kidsdrawing.dto.CreateExerciseRequest;
import com.app.kidsdrawing.dto.GetExerciseResponse;
import com.app.kidsdrawing.service.ExerciseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/exercises")
public class ExerciseController {
    private final ExerciseService  exerciseService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllExercise() {
        return ResponseEntity.ok().body(exerciseService.getAllExercise());
    }
    
    @CrossOrigin
    @GetMapping(value = "/class-student/{class_id}/{student_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllExerciseBySectionId(@PathVariable("class_id") Long class_id, @PathVariable("student_id") Long student_id) {
        return ResponseEntity.ok().body(exerciseService.getAllExerciseByClassAndStudent(class_id, student_id));
    }

    @CrossOrigin
    @GetMapping(value = "/section/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllExerciseBySectionId(@PathVariable Long id) {
        return ResponseEntity.ok().body(exerciseService.getAllExerciseBySectionId(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createExercise(@RequestBody CreateExerciseRequest createExerciseRequest) {
        Long exerciseId = exerciseService.createExercise(createExerciseRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{exerciseId}")
                .buildAndExpand(exerciseId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateExercise(@PathVariable Long id, @RequestBody CreateExerciseRequest createExerciseRequest) {
        Long exerciseId = exerciseService.updateExerciseById(id,createExerciseRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(exerciseId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetExerciseResponse> getExerciseById(@PathVariable Long id) {
        return ResponseEntity.ok().body(exerciseService.getExerciseById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteExerciseById(@PathVariable Long id) {
        Long exerciseId = exerciseService.removeExerciseById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(exerciseId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
