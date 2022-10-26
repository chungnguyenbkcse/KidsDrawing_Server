package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateTutorialTemplateRequest;
import com.app.kidsdrawing.dto.GetTutorialTemplateResponse;

public interface TutorialTemplateService {
    ResponseEntity<Map<String, Object>> getAllTutorialTemplate();
    GetTutorialTemplateResponse getTutorialTemplateBySectionTemplate(Long id);
    GetTutorialTemplateResponse getTutorialTemplateById(Long id);
    Long createTutorialTemplate(CreateTutorialTemplateRequest createTutorialTemplateRequest);
    Long removeTutorialTemplateById(Long id);
    Long updateTutorialTemplateById(Long id, CreateTutorialTemplateRequest createTutorialTemplateRequest);
}
