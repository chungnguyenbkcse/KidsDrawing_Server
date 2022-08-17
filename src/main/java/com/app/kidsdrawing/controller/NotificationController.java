package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.kidsdrawing.dto.CreateNotificationRequest;
import com.app.kidsdrawing.dto.GetNotificationResponse;
import com.app.kidsdrawing.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/notification-database")
public class NotificationController {
    private final NotificationService notificationService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody CreateNotificationRequest createNotificationRequest) {
        Long notificationId = notificationService.createNotification(createNotificationRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{notificationId}")
                .buildAndExpand(notificationId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateNotification(@PathVariable Long id, @RequestBody CreateNotificationRequest createNotificationRequest) {
        Long notificationId = notificationService.updateNotificationById(id,createNotificationRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(notificationId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllNotifications() {
        return ResponseEntity.ok().body(notificationService.getAllNotification());
    } 

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetNotificationResponse> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok().body(notificationService.getNotificationById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteNotificationById(@PathVariable Long id) {
        Long notificationId = notificationService.removeNotificationById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(notificationId).toUri();
        return ResponseEntity.created(location).build();
    }
}
