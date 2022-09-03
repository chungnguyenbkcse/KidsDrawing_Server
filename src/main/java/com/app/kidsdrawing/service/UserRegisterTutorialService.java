package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserRegisterTutorialRequest;
import com.app.kidsdrawing.dto.GetUserRegisterTutorialResponse;
public interface UserRegisterTutorialService {
    ResponseEntity<Map<String, Object>> getAllUserRegisterTutorial();
    ResponseEntity<Map<String, Object>> getAllUserRegisterTutorialBySection(Long id);
    GetUserRegisterTutorialResponse getUserRegisterTutorialById(Long id);
    Long createUserRegisterTutorial(CreateUserRegisterTutorialRequest createUserRegisterTutorialRequest);
    Long removeUserRegisterTutorialById(Long id);
    Long updateUserRegisterTutorialById(Long id, CreateUserRegisterTutorialRequest createUserRegisterTutorialRequest);
}
