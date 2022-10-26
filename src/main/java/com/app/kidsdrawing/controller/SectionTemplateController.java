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

import com.app.kidsdrawing.dto.CreateSectionTemplateRequest;
import com.app.kidsdrawing.dto.GetSectionTemplateResponse;
import com.app.kidsdrawing.service.SectionTemplateService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/section-template")
public class SectionTemplateController {
    private final SectionTemplateService  sectionTemplateService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSectionTemplate() {
        return ResponseEntity.ok().body(sectionTemplateService.getAllSectionTemplate());
    }
    
    @CrossOrigin
    @GetMapping(value = "/course/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSectionTemplateByCourseId(@PathVariable Long id) {
        return ResponseEntity.ok().body(sectionTemplateService.getAllSectionTemplateByCourseId(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<GetSectionTemplateResponse> createSectionTemplate(@RequestBody CreateSectionTemplateRequest createSectionTemplateRequest) {
        Long sectionTemplateId = sectionTemplateService.createSectionTemplate(createSectionTemplateRequest);
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{sectionTemplateId}")
                .buildAndExpand(sectionTemplateId).toUri();
        return ResponseEntity.ok().body(sectionTemplateService.getSectionTemplateById(sectionTemplateId));
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<GetSectionTemplateResponse> updateSectionTemplate(@PathVariable Long id, @RequestBody CreateSectionTemplateRequest createSectionTemplateRequest) {
        Long sectionTemplateId = sectionTemplateService.updateSectionTemplateById(id,createSectionTemplateRequest);
        ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(sectionTemplateId).toUri();
        return ResponseEntity.ok().body(sectionTemplateService.getSectionTemplateById(sectionTemplateId));
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetSectionTemplateResponse> getSectionTemplateById(@PathVariable Long id) {
        return ResponseEntity.ok().body(sectionTemplateService.getSectionTemplateById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteSectionTemplateById(@PathVariable Long id) {
        Long SectionTemplateId = sectionTemplateService.removeSectionTemplateById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(SectionTemplateId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
