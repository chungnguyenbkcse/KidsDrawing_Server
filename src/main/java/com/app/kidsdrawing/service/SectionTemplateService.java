package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateSectionTemplateRequest;
import com.app.kidsdrawing.dto.GetSectionTemplateResponse;

public interface SectionTemplateService {
    ResponseEntity<Map<String, Object>> getAllSectionTemplate();
    ResponseEntity<Map<String, Object>> getAllSectionTemplateByCourseId(UUID id);
    GetSectionTemplateResponse getSectionTemplateById(UUID id);
    UUID createSectionTemplate(CreateSectionTemplateRequest createSectionTemplateRequest);
    UUID removeSectionTemplateById(UUID id);
    UUID updateSectionTemplateById(UUID id, CreateSectionTemplateRequest createSectionTemplateRequest);
}
