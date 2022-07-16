package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateStatusTeacherRegisterTutorialRequest;
import com.app.kidsdrawing.dto.CreateTeacherRegisterTutorialRequest;
import com.app.kidsdrawing.dto.GetTeacherRegisterTutorialResponse;

public interface TeacherRegisterTutorialService {
    ResponseEntity<Map<String, Object>> getAllTeacherRegisterTutorial();
    ResponseEntity<Map<String, Object>> getAllTeacherRegisterTutorialByTeacherId(Long id);
    ResponseEntity<Map<String, Object>> getAllTeacherRegisterTutorialByTutorialId(Long id);
    GetTeacherRegisterTutorialResponse getTeacherRegisterTutorialById(Long id);
    Long createTeacherRegisterTutorial(CreateTeacherRegisterTutorialRequest createTeacherRegisterTutorialRequest);
    Long removeTeacherRegisterTutorialById(Long id);
    Long updateTeacherRegisterTutorialById(Long id, CreateTeacherRegisterTutorialRequest createTeacherRegisterTutorialRequest);
    Long updateStatusTeacherRegisterTutorialById(Long id, CreateStatusTeacherRegisterTutorialRequest createStatusTeacherRegisterTutorialRequest);
}
