package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateTutorialTemplateRequest;
import com.app.kidsdrawing.dto.GetTutorialTemplateResponse;

public interface TutorialTemplateService {
    ResponseEntity<Map<String, Object>> getAllTutorialTemplate();
    GetTutorialTemplateResponse getTutorialTemplateBySectionTemplate(UUID id);
    GetTutorialTemplateResponse getTutorialTemplateById(UUID id);
    UUID createTutorialTemplate(CreateTutorialTemplateRequest createTutorialTemplateRequest);
    UUID removeTutorialTemplateById(UUID id);
    UUID updateTutorialTemplateById(UUID id, CreateTutorialTemplateRequest createTutorialTemplateRequest);
}
