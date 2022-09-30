package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateTutorialPageRequest;
import com.app.kidsdrawing.dto.GetTutorialPageResponse;

public interface TutorialPageService {
    ResponseEntity<Map<String, Object>> getAllTutorialPage();
    ResponseEntity<Map<String, Object>> getAllTutorialPageByTutorialId(UUID id);
    ResponseEntity<Map<String, Object>> getAllTutorialPageBySectionId(UUID id);
    GetTutorialPageResponse getTutorialPageById(UUID id);
    UUID createTutorialPage(CreateTutorialPageRequest createTutorialPageRequest);
    UUID removeTutorialPageById(UUID id);
    UUID updateTutorialPageById(UUID id, CreateTutorialPageRequest createTutorialPageRequest);
}
