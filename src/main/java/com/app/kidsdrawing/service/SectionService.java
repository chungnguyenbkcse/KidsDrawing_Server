package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateSectionRequest;
import com.app.kidsdrawing.dto.GetSectionResponse;

public interface SectionService {
    ResponseEntity<Map<String, Object>> getAllSection();
    ResponseEntity<Map<String, Object>> getAllSectionByClassId(Long id);
    GetSectionResponse getSectionById(Long id);
    Long createSection(CreateSectionRequest createSectionRequest);
    Long removeSectionById(Long id);
    Long updateSectionById(Long id, CreateSectionRequest createSectionRequest);
}
