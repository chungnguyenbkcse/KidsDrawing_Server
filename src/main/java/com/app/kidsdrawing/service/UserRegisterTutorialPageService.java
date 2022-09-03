package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserRegisterTutorialPageRequest;
import com.app.kidsdrawing.dto.GetUserRegisterTutorialPageResponse;
public interface UserRegisterTutorialPageService {
    ResponseEntity<Map<String, Object>> getAllUserRegisterTutorialPage();
    ResponseEntity<Map<String, Object>> getAllUserRegisterTutorialPageByUserRegiseterTutorial(Long id);
    GetUserRegisterTutorialPageResponse getUserRegisterTutorialPageById(Long id);
    Long createUserRegisterTutorialPage(CreateUserRegisterTutorialPageRequest createUserRegisterTutorialPageRequest);
    Long removeUserRegisterTutorialPageById(Long id);
    Long updateUserRegisterTutorialPageById(Long id, CreateUserRegisterTutorialPageRequest createUserRegisterTutorialPageRequest);
}
