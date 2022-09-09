package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateNotificationRequest;
import com.app.kidsdrawing.dto.GetNotificationResponse;

public interface NotificationService {
    ResponseEntity<Map<String, Object>> getAllNotification();
    GetNotificationResponse getNotificationById(Long id);
    GetNotificationResponse createNotification(CreateNotificationRequest createNotificationRequest);
    Long removeNotificationById(Long id);
    Long updateNotificationById(Long id, CreateNotificationRequest createNotificationRequest);
}
