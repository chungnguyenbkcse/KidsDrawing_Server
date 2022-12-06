package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateSectionRequest;
import com.app.kidsdrawing.dto.GetSectionStudentResponse;

public interface SectionService {
    ResponseEntity<Map<String, Object>> getAllSection();
    ResponseEntity<Map<String, Object>> getAllSectionStudentByClassId(Long class_id, Long student_id);
    ResponseEntity<Map<String, Object>> getAllSectionParentByClassId(Long class_id, Long parent_id, int total_child_in_class);
    ResponseEntity<Map<String, Object>> getAllSectionTeacherByClassId(Long id);
    GetSectionStudentResponse getSectionById(Long id);
    Long createSection(CreateSectionRequest createSectionRequest);
    Long removeSectionById(Long id);
    Long updateSectionById(Long id, CreateSectionRequest createSectionRequest);
}
