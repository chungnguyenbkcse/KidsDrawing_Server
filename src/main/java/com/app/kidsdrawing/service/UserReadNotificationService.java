package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserReadNotificationRequest;

public interface UserReadNotificationService {
    ResponseEntity<Map<String, Object>> getAllUserReadNotification();
    ResponseEntity<Map<String, Object>> getAllUserReadNotificationByUserId(Long id);
    ResponseEntity<Map<String, Object>> getAllUserReadNotificationBynNotificationId(Long id);
    Long createUserReadNotification(CreateUserReadNotificationRequest createUserReadNotificationRequest);
    Long removeUserReadNotificationById(Long id);
    Long updateUserReadNotificationById(CreateUserReadNotificationRequest createUserReadNotificationRequest);
}
