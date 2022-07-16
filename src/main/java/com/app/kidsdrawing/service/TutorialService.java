package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateTutorialAdminRequest;
import com.app.kidsdrawing.dto.CreateTutorialRequest;
import com.app.kidsdrawing.dto.GetTutorialResponse;

public interface TutorialService {
    ResponseEntity<Map<String, Object>> getAllTutorial();
    ResponseEntity<Map<String, Object>> getAllTutorialByAdminSection(Long id);
    ResponseEntity<Map<String, Object>> getAllTutorialByTeacherSection(Long id);
    ResponseEntity<Map<String, Object>> getAllTutorialByCreatorSection(Long creator_id, Long section_id);
    ResponseEntity<Map<String, Object>> getAllTutorialBySection(Long id);
    GetTutorialResponse getTutorialById(Long id);
    Long createTutorial(CreateTutorialRequest createTutorialRequest);
    Long createTutorial(CreateTutorialAdminRequest createTutorialAdminRequest);
    Long removeTutorialById(Long id);
    Long updateTutorialById(Long id, CreateTutorialRequest createTutorialRequest);
}
