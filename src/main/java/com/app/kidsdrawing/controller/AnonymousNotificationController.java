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

import com.app.kidsdrawing.dto.CreateAnonymousNotificationRequest;
import com.app.kidsdrawing.dto.GetAnonymousNotificationResponse;
import com.app.kidsdrawing.service.AnonymousNotificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/anonymous-notification")
public class AnonymousNotificationController {
    private final AnonymousNotificationService anonymousNotificationService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createAnonymousNotification(@RequestBody CreateAnonymousNotificationRequest createAnonymousNotificationRequest) {
        Long anonymousNotificationId = anonymousNotificationService.createAnonymousNotification(createAnonymousNotificationRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{anonymousNotificationId}")
                .buildAndExpand(anonymousNotificationId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateAnonymousNotification(@PathVariable Long id, @RequestBody CreateAnonymousNotificationRequest createAnonymousNotificationRequest) {
        Long anonymousNotificationId = anonymousNotificationService.updateAnonymousNotificationById(createAnonymousNotificationRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(anonymousNotificationId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllAnonymousNotifications() {
        return ResponseEntity.ok().body(anonymousNotificationService.getAllAnonymousNotification());
    } 

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetAnonymousNotificationResponse> getAnonymousNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok().body(anonymousNotificationService.getAnonymousNotificationById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAnonymousNotificationById(@PathVariable Long id) {
        Long anonymousNotificationId = anonymousNotificationService.removeAnonymousNotificationByNotificationId(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(anonymousNotificationId).toUri();
        return ResponseEntity.created(location).build();
    }
}
