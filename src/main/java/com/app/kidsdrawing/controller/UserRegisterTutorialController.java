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

import com.app.kidsdrawing.dto.CreateUserRegisterTutorialRequest;
import com.app.kidsdrawing.dto.GetUserRegisterTutorialResponse;
import com.app.kidsdrawing.service.UserRegisterTutorialService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user-register-tutorial")
public class UserRegisterTutorialController {
    private final UserRegisterTutorialService  userRegisterTutorialService;
    
    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserRegisterTutorial() {
        return ResponseEntity.ok().body(userRegisterTutorialService.getAllUserRegisterTutorial());
    }

    @CrossOrigin
    @GetMapping(value = "/section/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getReportUserRegisterTutorialBySection(@PathVariable Long id) {
        return ResponseEntity.ok().body(userRegisterTutorialService.getAllUserRegisterTutorialBySection(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<GetUserRegisterTutorialResponse> createUserRegisterTutorial(@RequestBody CreateUserRegisterTutorialRequest createUserRegisterTutorialRequest) {
        return ResponseEntity.ok().body(userRegisterTutorialService.createUserRegisterTutorial(createUserRegisterTutorialRequest));
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateUserRegisterTutorial(@PathVariable Long id, @RequestBody CreateUserRegisterTutorialRequest createUserRegisterTutorialRequest) {
        Long userRegisterTutorialId = userRegisterTutorialService.updateUserRegisterTutorialById(id,createUserRegisterTutorialRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userRegisterTutorialId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserRegisterTutorialResponse> getUserRegisterTutorialById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userRegisterTutorialService.getUserRegisterTutorialById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserRegisterTutorialById(@PathVariable Long id) {
        Long userRegisterTutorialId = userRegisterTutorialService.removeUserRegisterTutorialById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userRegisterTutorialId).toUri();
        return ResponseEntity.created(location).build();
    }
}
