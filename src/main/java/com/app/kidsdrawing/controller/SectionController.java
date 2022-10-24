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

import com.app.kidsdrawing.dto.CreateSectionRequest;
import com.app.kidsdrawing.dto.GetSectionResponse;
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
    @GetMapping(value = "/class/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSectionByClassId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(sectionService.getAllSectionByClassId(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createSection(@RequestBody CreateSectionRequest createSectionRequest) {
        UUID sectionId = sectionService.createSection(createSectionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{sectionId}")
                .buildAndExpand(sectionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateSection(@PathVariable UUID id, @RequestBody CreateSectionRequest createSectionRequest) {
        UUID sectionId = sectionService.updateSectionById(id,createSectionRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(sectionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetSectionResponse> getSectionById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(sectionService.getSectionById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteSectionById(@PathVariable UUID id) {
        UUID SectionId = sectionService.removeSectionById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(SectionId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
