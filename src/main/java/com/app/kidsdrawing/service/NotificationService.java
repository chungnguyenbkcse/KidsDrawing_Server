package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateNotificationRequest;
import com.app.kidsdrawing.dto.GetNotificationResponse;

public interface NotificationService {
    ResponseEntity<Map<String, Object>> getAllNotification();
    GetNotificationResponse getNotificationById(UUID id);
    GetNotificationResponse createNotification(CreateNotificationRequest createNotificationRequest);
    UUID removeNotificationById(UUID id);
    UUID updateNotificationById(UUID id, CreateNotificationRequest createNotificationRequest);
}
