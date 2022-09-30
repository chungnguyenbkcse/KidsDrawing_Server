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
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialBySectionId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(tutorialService.getAllTutorialBySection(id));
    }

    @CrossOrigin
    @GetMapping(value = "/teacher/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialByCreatorId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(tutorialService.getAllTutorialByCreator(id));
    }


    @CrossOrigin
    @GetMapping(value = "/section/teacher/{section_id}/{creator_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTutorialByTeacherSection(@PathVariable("section_id") UUID section_id, @PathVariable("creator_id") UUID creator_id) {
        return ResponseEntity.ok().body(tutorialService.getAllTutorialByCreatorSection(creator_id, section_id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<GetTutorialResponse> createTutorial(@RequestBody CreateTutorialRequest createTutorialRequest) {
        return ResponseEntity.ok().body(tutorialService.createTutorial(createTutorialRequest));
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateTutorial(@PathVariable UUID id, @RequestBody CreateTutorialRequest createTutorialRequest) {
        UUID tutorialId = tutorialService.updateTutorial(id,createTutorialRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(tutorialId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetTutorialResponse> getTutorialById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(tutorialService.getTutorialById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTutorialById(@PathVariable UUID id) {
        UUID tutorialId = tutorialService.removeTutorialById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(tutorialId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
