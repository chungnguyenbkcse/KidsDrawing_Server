package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateTutorialRequest;
import com.app.kidsdrawing.dto.GetTutorialResponse;

public interface TutorialService {
    ResponseEntity<Map<String, Object>> getAllTutorial();
    GetTutorialResponse getTutorialBySection(UUID id);
    ResponseEntity<Map<String, Object>> getAllTutorialByCreator(UUID id);
    ResponseEntity<Map<String, Object>> getAllTutorialByCreatorSection(UUID creator_id, UUID section_id);
    GetTutorialResponse getTutorialById(UUID id);
    GetTutorialResponse createTutorial(CreateTutorialRequest createTutorialRequest);
    UUID removeTutorialById(UUID id);
    UUID updateTutorial(UUID id, CreateTutorialRequest createTutorialRequest);
}
