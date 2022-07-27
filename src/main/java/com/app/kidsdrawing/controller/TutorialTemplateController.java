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

import com.app.kidsdrawing.dto.CreateTutorialTemplateRequest;
import com.app.kidsdrawing.dto.GetTutorialTemplateResponse;
import com.app.kidsdrawing.service.TutorialTemplateService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/tutorial-template")
public class TutorialTemplateController {
    private final TutorialTemplateService  tutorialTemplateService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialTemplate() {
        return ResponseEntity.ok().body(tutorialTemplateService.getAllTutorialTemplate());
    }
    
    @CrossOrigin
    @GetMapping(value = "/section-template/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialTemplateBySectionId(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialTemplateService.getAllTutorialTemplateBySectionTemplate(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<GetTutorialTemplateResponse> createTutorialTemplate(@RequestBody CreateTutorialTemplateRequest createTutorialTemplateRequest) {
        Long tutorialTemplateId = tutorialTemplateService.createTutorialTemplate(createTutorialTemplateRequest);
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{tutorialTemplateId}")
                .buildAndExpand(tutorialTemplateId).toUri();
        return ResponseEntity.ok().body(tutorialTemplateService.getTutorialTemplateById(tutorialTemplateId));
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<GetTutorialTemplateResponse> updateTutorialTemplate(@PathVariable Long id, @RequestBody CreateTutorialTemplateRequest createTutorialTemplateRequest) {
        Long tutorialTemplateId = tutorialTemplateService.updateTutorialTemplateById(id,createTutorialTemplateRequest);
        ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(tutorialTemplateId).toUri();
        return ResponseEntity.ok().body(tutorialTemplateService.getTutorialTemplateById(tutorialTemplateId));
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetTutorialTemplateResponse> getTutorialTemplateById(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialTemplateService.getTutorialTemplateById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTutorialTemplateById(@PathVariable Long id) {
        Long tutorialTemplateId = tutorialTemplateService.removeTutorialTemplateById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(tutorialTemplateId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
