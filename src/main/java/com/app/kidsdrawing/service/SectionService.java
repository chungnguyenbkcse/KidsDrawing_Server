package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateSectionRequest;
import com.app.kidsdrawing.dto.GetSectionResponse;

public interface SectionService {
    ResponseEntity<Map<String, Object>> getAllSection();
    ResponseEntity<Map<String, Object>> getAllSectionByClassId(UUID id);
    GetSectionResponse getSectionById(UUID id);
    UUID createSection(CreateSectionRequest createSectionRequest);
    UUID removeSectionById(UUID id);
    UUID updateSectionById(UUID id, CreateSectionRequest createSectionRequest);
}
