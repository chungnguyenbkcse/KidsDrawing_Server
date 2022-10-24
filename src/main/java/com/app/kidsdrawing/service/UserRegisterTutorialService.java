package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserRegisterTutorialRequest;
import com.app.kidsdrawing.dto.GetUserRegisterTutorialResponse;
public interface UserRegisterTutorialService {
    ResponseEntity<Map<String, Object>> getAllUserRegisterTutorial();
    ResponseEntity<Map<String, Object>> getAllUserRegisterTutorialBySection(UUID id);
    GetUserRegisterTutorialResponse getUserRegisterTutorialById(UUID id);
    GetUserRegisterTutorialResponse createUserRegisterTutorial(CreateUserRegisterTutorialRequest createUserRegisterTutorialRequest);
    UUID removeUserRegisterTutorialById(UUID id);
    UUID updateUserRegisterTutorialById(UUID id, CreateUserRegisterTutorialRequest createUserRegisterTutorialRequest);
}
