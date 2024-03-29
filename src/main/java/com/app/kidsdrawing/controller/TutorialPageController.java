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

import com.app.kidsdrawing.dto.CreateTutorialPageRequest;
import com.app.kidsdrawing.dto.GetTutorialPageResponse;
import com.app.kidsdrawing.service.TutorialPageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/tutorial-page")
public class TutorialPageController {
    private final TutorialPageService  tutorialPageService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialPage() {
        return ResponseEntity.ok().body(tutorialPageService.getAllTutorialPage());
    }
    
    @CrossOrigin
    @GetMapping(value = "/section/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialPageBySectionId(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialPageService.getAllTutorialPageBySectionId(id));
    }


    @CrossOrigin
    @GetMapping(value = "/section-not-approve/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialPageBySectionIdNotApproved(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialPageService.getAllTutorialPageBySectionIdNotApproved(id));
    }

    @CrossOrigin
    @GetMapping(value = "/section-not-approve-now/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialPageBySectionNotApproveNow(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialPageService.getAllTutorialPageBySectionNotApproveNow(id));
    }


    @CrossOrigin
    @GetMapping(value = "/section-check/{id}")
    public ResponseEntity<GetTutorialPageResponse> checkTutorialPageBySectionId(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialPageService.checkTutorialPageBySectionId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/section-x/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialTemplatePageBySectionId(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialPageService.getAllTutorialTemplatePageBySectionId(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createTutorialPage(@RequestBody CreateTutorialPageRequest createTutorialPageRequest) {
        Long tutorialPageId = tutorialPageService.createTutorialPage(createTutorialPageRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{tutorialPageId}")
                .buildAndExpand(tutorialPageId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateTutorialPage(@PathVariable Long id, @RequestBody CreateTutorialPageRequest createTutorialPageRequest) {
        Long tutorialPageId = tutorialPageService.updateTutorialPageById(id,createTutorialPageRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(tutorialPageId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetTutorialPageResponse> getTutorialPageById(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialPageService.getTutorialPageById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTutorialPageById(@PathVariable Long id) {
        Long tutorialPageId = tutorialPageService.removeTutorialPageById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(tutorialPageId).toUri();
        return ResponseEntity.created(location).build();
    }


    @CrossOrigin
    @DeleteMapping(value = "/section/{id}")
    public ResponseEntity<String> removeTutorialPageBySection(@PathVariable Long id) {
        Long tutorialPageId = tutorialPageService.removeTutorialPageBySection(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(tutorialPageId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
