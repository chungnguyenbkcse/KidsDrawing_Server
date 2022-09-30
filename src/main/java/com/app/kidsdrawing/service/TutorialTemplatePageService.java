package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateTutorialTemplatePageRequest;
import com.app.kidsdrawing.dto.GetTutorialTemplatePageResponse;

public interface TutorialTemplatePageService {
    ResponseEntity<Map<String, Object>> getAllTutorialTemplatePage();
    ResponseEntity<Map<String, Object>> getAllTutorialTemplatePageByTutorialTemplateId(UUID id);
    ResponseEntity<Map<String, Object>> getAllTutorialTemplatePageBySectionTemplateId(UUID id);
    GetTutorialTemplatePageResponse getTutorialTemplatePageById(UUID id);
    UUID createTutorialTemplatePage(CreateTutorialTemplatePageRequest createTutorialTemplatePageRequest);
    UUID removeTutorialTemplatePageById(UUID id);
    UUID updateTutorialTemplatePageById(UUID id, CreateTutorialTemplatePageRequest createTutorialTemplatePageRequest);
}
