package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

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

import com.app.kidsdrawing.dto.CreateUserRegisterTutorialPageRequest;
import com.app.kidsdrawing.dto.GetUserRegisterTutorialPageResponse;
import com.app.kidsdrawing.service.UserRegisterTutorialPageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user-register-tutorial-page")
public class UserRegisterTutorialPageController {
    private final UserRegisterTutorialPageService  userRegisterTutorialService;
    
    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserRegisterTutorialPage() {
        return ResponseEntity.ok().body(userRegisterTutorialService.getAllUserRegisterTutorialPage());
    }

    @CrossOrigin
    @GetMapping(value = "/tutorial/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getReportUserRegisterTutorialPageBySection(@PathVariable UUID id) {
        return ResponseEntity.ok().body(userRegisterTutorialService.getAllUserRegisterTutorialPageByUserRegiseterTutorial(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createUserRegisterTutorialPage(@RequestBody CreateUserRegisterTutorialPageRequest createUserRegisterTutorialPageRequest) {
        UUID userRegisterTutorialId = userRegisterTutorialService.createUserRegisterTutorialPage(createUserRegisterTutorialPageRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userRegisterTutorialId}")
                .buildAndExpand(userRegisterTutorialId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateUserRegisterTutorialPage(@PathVariable UUID id, @RequestBody CreateUserRegisterTutorialPageRequest createUserRegisterTutorialPageRequest) {
        UUID userRegisterTutorialId = userRegisterTutorialService.updateUserRegisterTutorialPageById(id,createUserRegisterTutorialPageRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userRegisterTutorialId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserRegisterTutorialPageResponse> getUserRegisterTutorialPageById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(userRegisterTutorialService.getUserRegisterTutorialPageById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserRegisterTutorialPageById(@PathVariable UUID id) {
        UUID userRegisterTutorialId = userRegisterTutorialService.removeUserRegisterTutorialPageById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userRegisterTutorialId).toUri();
        return ResponseEntity.created(location).build();
    }
}
