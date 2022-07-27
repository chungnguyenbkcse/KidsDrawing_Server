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

import com.app.kidsdrawing.dto.CreateTeacherRegisterTutorialRequest;
import com.app.kidsdrawing.dto.GetTeacherRegisterTutorialResponse;
import com.app.kidsdrawing.service.TeacherRegisterTutorialService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/teacher-register-tutorial")
public class TeacherRegisterTutorialController {
    private final TeacherRegisterTutorialService  teacherRegisterTutorialService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTeacherRegisterTutorial() {
        return ResponseEntity.ok().body(teacherRegisterTutorialService.getAllTeacherRegisterTutorial());
    } 

    @CrossOrigin
    @GetMapping(value = "teacher/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTeacherRegisterTutorialByTeacherId(@PathVariable Long id) {
        return ResponseEntity.ok().body(teacherRegisterTutorialService.getAllTeacherRegisterTutorialByTeacherId(id));
    }

    @CrossOrigin
    @GetMapping(value = "tutorial/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTeacherRegisterTutorialByTutorialId(@PathVariable Long id) {
        return ResponseEntity.ok().body(teacherRegisterTutorialService.getAllTeacherRegisterTutorialByTutorialId(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createTeacherRegisterTutorial(@RequestBody CreateTeacherRegisterTutorialRequest createTeacherRegisterTutorialRequest) {
        Long teacherRegisterTutorialId = teacherRegisterTutorialService.createTeacherRegisterTutorial(createTeacherRegisterTutorialRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{teacherRegisterTutorialId}")
                .buildAndExpand(teacherRegisterTutorialId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateTeacherRegisterTutorial(@PathVariable Long id, @RequestBody CreateTeacherRegisterTutorialRequest createTeacherRegisterTutorialRequest) {
        Long teacherRegisterTutorialId = teacherRegisterTutorialService.updateTeacherRegisterTutorialById(id,createTeacherRegisterTutorialRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(teacherRegisterTutorialId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetTeacherRegisterTutorialResponse> getTeacherRegisterTutorialById(@PathVariable Long id) {
        return ResponseEntity.ok().body(teacherRegisterTutorialService.getTeacherRegisterTutorialById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTeacherRegisterTutorialById(@PathVariable Long id) {
        Long teacherRegisterTutorialId = teacherRegisterTutorialService.removeTeacherRegisterTutorialById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(teacherRegisterTutorialId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
