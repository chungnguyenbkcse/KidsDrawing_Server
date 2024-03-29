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

import com.app.kidsdrawing.dto.CreateSectionRequest;
import com.app.kidsdrawing.dto.CreateTutorialTemplatePageRequest;
import com.app.kidsdrawing.dto.GetSectionStudentResponse;
import com.app.kidsdrawing.service.SectionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/section")
public class SectionController {
    private final SectionService  sectionService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSection() {
        return ResponseEntity.ok().body(sectionService.getAllSection());
    }

    @CrossOrigin
    @GetMapping(value = "/admin")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSectionByAdmin() {
        return ResponseEntity.ok().body(sectionService.getAllSectionByAdmin());
    }
    
    @CrossOrigin
    @GetMapping(value = "/class-teacher/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSectionTeacherByClassId(@PathVariable Long id) {
        return ResponseEntity.ok().body(sectionService.getAllSectionTeacherByClassId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/class-student/{classes_id}/{student_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSectionStudentByClassId(@PathVariable("classes_id") Long classes_id, @PathVariable("student_id") Long student_id) {
        return ResponseEntity.ok().body(sectionService.getAllSectionStudentByClassId(classes_id, student_id));
    }


    @CrossOrigin
    @GetMapping(value = "/class-parent/{classes_id}/{parent_id}/{total}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSectionParentByClassId(@PathVariable("classes_id") Long classes_id, @PathVariable("parent_id") Long parent_id, @PathVariable("total") int total) {
        return ResponseEntity.ok().body(sectionService.getAllSectionParentByClassId(classes_id, parent_id, total));
    }

    @CrossOrigin
    @PostMapping(value = "/tutorial-template-page/{id}")
    public ResponseEntity<String> createTutorialPageBySection(@PathVariable Long id, @RequestBody CreateTutorialTemplatePageRequest createSectionRequest) {
        Long sectionId = sectionService.createTutorialPageBySection(id, createSectionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{sectionId}")
                .buildAndExpand(sectionId).toUri();
        return ResponseEntity.created(location).build();
    }


    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createSection(@RequestBody CreateSectionRequest createSectionRequest) {
        Long sectionId = sectionService.createSection(createSectionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{sectionId}")
                .buildAndExpand(sectionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @DeleteMapping(value = "/tutorial-template-page/{id}")
    public ResponseEntity<String> deleteTutorialTemplatePageBySection(@PathVariable Long id) {
        Long sectionId = sectionService.deleteTutorialTemplatePageBySection(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{sectionId}")
                .buildAndExpand(sectionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateSection(@PathVariable Long id, @RequestBody CreateSectionRequest createSectionRequest) {
        Long sectionId = sectionService.updateSectionById(id,createSectionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(sectionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/admin/{id}")
    public ResponseEntity<String> updateSectionByAdmin(@PathVariable Long id, @RequestBody CreateSectionRequest createSectionRequest) {
        Long sectionId = sectionService.updateSectionByAdmin(id,createSectionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(sectionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetSectionStudentResponse> getSectionById(@PathVariable Long id) {
        return ResponseEntity.ok().body(sectionService.getSectionById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteSectionById(@PathVariable Long id) {
        Long SectionId = sectionService.removeSectionById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(SectionId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
