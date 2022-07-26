package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateSectionTemplateRequest;
import com.app.kidsdrawing.dto.GetSectionTemplateResponse;

public interface SectionTemplateService {
    ResponseEntity<Map<String, Object>> getAllSectionTemplate();
    ResponseEntity<Map<String, Object>> getAllSectionTemplateByCourseId(Long id);
    GetSectionTemplateResponse getSectionTemplateById(Long id);
    Long createSectionTemplate(CreateSectionTemplateRequest createSectionTemplateRequest);
    Long removeSectionTemplateById(Long id);
    Long updateSectionTemplateById(Long id, CreateSectionTemplateRequest createSectionTemplateRequest);
}
