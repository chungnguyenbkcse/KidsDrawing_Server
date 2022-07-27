package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;


import com.app.kidsdrawing.dto.CreateTutorialRequest;
import com.app.kidsdrawing.dto.GetTutorialResponse;

public interface TutorialService {
    ResponseEntity<Map<String, Object>> getAllTutorial();
    ResponseEntity<Map<String, Object>> getAllTutorialBySection(Long id);
    ResponseEntity<Map<String, Object>> getAllTutorialByCreator(Long id);
    ResponseEntity<Map<String, Object>> getAllTutorialByCreatorSection(Long creator_id, Long section_id);
    GetTutorialResponse getTutorialById(Long id);
    Long createTutorial(CreateTutorialRequest createTutorialRequest);
    Long removeTutorialById(Long id);
    Long updateTutorialById(Long id, CreateTutorialRequest createTutorialRequest);
}
