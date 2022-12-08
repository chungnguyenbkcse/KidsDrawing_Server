package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateTutorialPageRequest;
import com.app.kidsdrawing.dto.GetTutorialPageResponse;

public interface TutorialPageService {
    ResponseEntity<Map<String, Object>> getAllTutorialPage();
    ResponseEntity<Map<String, Object>> getAllTutorialPageBySectionId(Long id);
    ResponseEntity<Map<String, Object>> getAllTutorialTemplatePageBySectionId(Long id);
    GetTutorialPageResponse getTutorialPageById(Long id);
    Long createTutorialPage(CreateTutorialPageRequest createTutorialPageRequest);
    Long removeTutorialPageById(Long id);
    Long updateTutorialPageById(Long id, CreateTutorialPageRequest createTutorialPageRequest);
}
