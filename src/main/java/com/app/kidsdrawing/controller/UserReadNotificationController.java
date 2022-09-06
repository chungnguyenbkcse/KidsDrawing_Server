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

import com.app.kidsdrawing.dto.CreateUserReadNotificationRequest;
import com.app.kidsdrawing.service.UserReadNotificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user-read-notification")
public class UserReadNotificationController {
    private final UserReadNotificationService  userReadNotificationService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserReadNotification() {
        return ResponseEntity.ok().body(userReadNotificationService.getAllUserReadNotification());
    }
    
    @CrossOrigin
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserReadNotificationByStudentId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userReadNotificationService.getAllUserReadNotificationByUserId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/notification/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserReadNotificationByNotificationId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userReadNotificationService.getAllUserReadNotificationBynNotificationId(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createUserReadNotification(@RequestBody CreateUserReadNotificationRequest createUserReadNotificationRequest) {
        Long userReadNotificationId = userReadNotificationService.createUserReadNotification(createUserReadNotificationRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userReadNotificationId}")
                .buildAndExpand(userReadNotificationId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping
    public ResponseEntity<String> updateUserReadNotification(@RequestBody CreateUserReadNotificationRequest createUserReadNotificationRequest) {
        Long userReadNotificationId = userReadNotificationService.updateUserReadNotificationById(createUserReadNotificationRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userReadNotificationId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserReadNotificationById(@PathVariable Long id) {
        Long userReadNotificationId = userReadNotificationService.removeUserReadNotificationById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userReadNotificationId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}
