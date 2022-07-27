package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateTutorialTemplatePageRequest;
import com.app.kidsdrawing.dto.GetTutorialTemplatePageResponse;

public interface TutorialTemplatePageService {
    ResponseEntity<Map<String, Object>> getAllTutorialTemplatePage();
    ResponseEntity<Map<String, Object>> getAllTutorialTemplatePageByTutorialTemplateId(Long id);
    GetTutorialTemplatePageResponse getTutorialTemplatePageById(Long id);
    Long createTutorialTemplatePage(CreateTutorialTemplatePageRequest createTutorialTemplatePageRequest);
    Long removeTutorialTemplatePageById(Long id);
    Long updateTutorialTemplatePageById(Long id, CreateTutorialTemplatePageRequest createTutorialTemplatePageRequest);
}
