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

import com.app.kidsdrawing.dto.CreateClassRequest;
import com.app.kidsdrawing.dto.GetClassResponse;
import com.app.kidsdrawing.service.ClassesService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/classes")
public class ClassesController {
    private final ClassesService classService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createClass(@RequestBody CreateClassRequest createClassRequest) {
        Long classId = classService.createClass(createClassRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{classId}")
                .buildAndExpand(classId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateClass(@PathVariable Long id, @RequestBody CreateClassRequest createClassRequest) {
        Long classId = classService.updateClassById(id,createClassRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(classId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value="/info")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getInforDetailAllClass() {
        return ResponseEntity.ok().body(classService.getInforDetailAllClass());
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllClasss() {
        return ResponseEntity.ok().body(classService.getAllClass());
    } 

    @CrossOrigin
    @GetMapping(value = "/info/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getInforDetailOfClass(@PathVariable Long id) {
        return ResponseEntity.ok().body(classService.getInforDetailOfClass(id));
    } 

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetClassResponse> getClassById(@PathVariable Long id) {
        return ResponseEntity.ok().body(classService.getClassById(id));
    }

    @CrossOrigin
    @GetMapping(value = "/teacher/{id}")
    public  ResponseEntity<ResponseEntity<Map<String, Object>>>  getInforDetailOfClassByTeacherId(@PathVariable Long id) {
        return ResponseEntity.ok().body(classService.getInforDetailOfClassByTeacherId(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteClassById(@PathVariable Long id) {
        Long classId = classService.removeClassById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(classId).toUri();
        return ResponseEntity.created(location).build();
    }
}
