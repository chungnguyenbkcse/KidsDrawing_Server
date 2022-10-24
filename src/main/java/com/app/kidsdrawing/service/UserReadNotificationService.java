package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserReadNotificationRequest;

public interface UserReadNotificationService {
    ResponseEntity<Map<String, Object>> getAllUserReadNotification();
    ResponseEntity<Map<String, Object>> getAllUserReadNotificationByUserId(UUID id);
    ResponseEntity<Map<String, Object>> getAllUserReadNotificationBynNotificationId(UUID id);
    UUID createUserReadNotification(CreateUserReadNotificationRequest createUserReadNotificationRequest);
    UUID removeUserReadNotificationById(UUID id);
    UUID updateUserReadNotificationById(CreateUserReadNotificationRequest createUserReadNotificationRequest);
}
