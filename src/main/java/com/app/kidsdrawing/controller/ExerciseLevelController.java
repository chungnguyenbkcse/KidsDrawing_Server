package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

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

import com.app.kidsdrawing.dto.CreateExerciseLevelRequest;
import com.app.kidsdrawing.dto.GetExerciseLevelResponse;
import com.app.kidsdrawing.service.ExerciseLevelService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/exercise-level")
public class ExerciseLevelController {
    private final ExerciseLevelService  exerciseLevelService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllExerciseLevel() {
        return ResponseEntity.ok().body(exerciseLevelService.getAllExerciseLevel());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createExerciseLevel(@RequestBody CreateExerciseLevelRequest createExerciseLevelRequest) {
        UUID exerciseLevelId = exerciseLevelService.createExerciseLevel(createExerciseLevelRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{exerciseLevelId}")
                .buildAndExpand(exerciseLevelId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateExerciseLevel(@PathVariable UUID id, @RequestBody CreateExerciseLevelRequest createExerciseLevelRequest) {
        UUID exerciseLevelId = exerciseLevelService.updateExerciseLevelById(id,createExerciseLevelRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(exerciseLevelId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetExerciseLevelResponse> getExerciseLevelById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(exerciseLevelService.getExerciseLevelById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteExerciseLevelById(@PathVariable UUID id) {
        UUID exerciseLevelId = exerciseLevelService.removeExerciseLevelById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(exerciseLevelId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
