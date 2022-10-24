package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserRegisterTutorialPageRequest;
import com.app.kidsdrawing.dto.GetUserRegisterTutorialPageResponse;
public interface UserRegisterTutorialPageService {
    ResponseEntity<Map<String, Object>> getAllUserRegisterTutorialPage();
    ResponseEntity<Map<String, Object>> getAllUserRegisterTutorialPageByUserRegiseterTutorial(UUID id);
    GetUserRegisterTutorialPageResponse getUserRegisterTutorialPageById(UUID id);
    UUID createUserRegisterTutorialPage(CreateUserRegisterTutorialPageRequest createUserRegisterTutorialPageRequest);
    UUID removeUserRegisterTutorialPageById(UUID id);
    UUID updateUserRegisterTutorialPageById(UUID id, CreateUserRegisterTutorialPageRequest createUserRegisterTutorialPageRequest);
}
