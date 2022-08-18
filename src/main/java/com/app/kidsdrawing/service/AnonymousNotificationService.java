package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateAnonymousNotificationRequest;
import com.app.kidsdrawing.dto.GetAnonymousNotificationResponse;

public interface AnonymousNotificationService {
    ResponseEntity<Map<String, Object>> getAllAnonymousNotification();
    GetAnonymousNotificationResponse getAnonymousNotificationById(Long id);
    Long createAnonymousNotification(CreateAnonymousNotificationRequest createAnonymousNotificationRequest);
    Long removeAnonymousNotificationByNotificationId(Long id);
    Long updateAnonymousNotificationById(CreateAnonymousNotificationRequest createAnonymousNotificationRequest);
}
