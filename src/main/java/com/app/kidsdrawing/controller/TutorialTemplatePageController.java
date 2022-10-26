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

import com.app.kidsdrawing.dto.CreateTutorialTemplatePageRequest;
import com.app.kidsdrawing.dto.GetTutorialTemplatePageResponse;
import com.app.kidsdrawing.service.TutorialTemplatePageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/tutorial-template-page")
public class TutorialTemplatePageController {
    private final TutorialTemplatePageService  tutorialTemplate;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialTemplatePage() {
        return ResponseEntity.ok().body(tutorialTemplate.getAllTutorialTemplatePage());
    }
    
    @CrossOrigin
    @GetMapping(value = "/tutorial-template/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialTemplatePageByClassId(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialTemplate.getAllTutorialTemplatePageByTutorialTemplateId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/section-template/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialTemplatePageBySectionId(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialTemplate.getAllTutorialTemplatePageBySectionTemplateId(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createTutorialTemplatePage(@RequestBody CreateTutorialTemplatePageRequest createTutorialTemplatePageRequest) {
        Long tutorialPageId = tutorialTemplate.createTutorialTemplatePage(createTutorialTemplatePageRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{tutorialPageId}")
                .buildAndExpand(tutorialPageId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateTutorialTemplatePage(@PathVariable Long id, @RequestBody CreateTutorialTemplatePageRequest createTutorialTemplatePageRequest) {
        Long tutorialPageId = tutorialTemplate.updateTutorialTemplatePageById(id,createTutorialTemplatePageRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(tutorialPageId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetTutorialTemplatePageResponse> getTutorialTemplatePageById(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialTemplate.getTutorialTemplatePageById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTutorialTemplatePageById(@PathVariable Long id) {
        Long tutorialPageId = tutorialTemplate.removeTutorialTemplatePageById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(tutorialPageId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
