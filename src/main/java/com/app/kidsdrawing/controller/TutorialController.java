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

import com.app.kidsdrawing.dto.CreateTutorialAdminRequest;
import com.app.kidsdrawing.dto.CreateTutorialRequest;
import com.app.kidsdrawing.dto.GetTutorialResponse;
import com.app.kidsdrawing.service.TutorialService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/tutorial")
public class TutorialController {
    private final TutorialService  tutorialService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorial() {
        return ResponseEntity.ok().body(tutorialService.getAllTutorial());
    }
    
    @CrossOrigin
    @GetMapping(value = "/section/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialBySectionId(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialService.getAllTutorialBySection(id));
    }

    @CrossOrigin
    @GetMapping(value = "/section/admin/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialByAdminSection(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialService.getAllTutorialByAdminSection(id));
    }

    @CrossOrigin
    @GetMapping(value = "/section/teacher/{section_id}/{creator_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialByTeacherSection(@PathVariable("section_id") Long section_id, @PathVariable("creator_id") Long creator_id) {
        return ResponseEntity.ok().body(tutorialService.getAllTutorialByCreatorSection(creator_id, section_id));
    }

    @CrossOrigin
    @PostMapping(value = "/teacher")
    public ResponseEntity<String> createTutorial(@RequestBody CreateTutorialRequest createTutorialRequest) {
        Long tutorialId = tutorialService.createTutorial(createTutorialRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{tutorialId}")
                .buildAndExpand(tutorialId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PostMapping(value = "/admin")
    public ResponseEntity<String> createTutorial(@RequestBody CreateTutorialAdminRequest createTutorialAdminRequest) {
        Long tutorialId = tutorialService.createTutorial(createTutorialAdminRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{tutorialId}")
                .buildAndExpand(tutorialId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateTutorial(@PathVariable Long id, @RequestBody CreateTutorialRequest createTutorialRequest) {
        Long tutorialId = tutorialService.updateTutorialById(id,createTutorialRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(tutorialId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetTutorialResponse> getTutorialById(@PathVariable Long id) {
        return ResponseEntity.ok().body(tutorialService.getTutorialById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTutorialById(@PathVariable Long id) {
        Long tutorialId = tutorialService.removeTutorialById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(tutorialId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
