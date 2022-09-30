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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.kidsdrawing.dto.CreateUserRegisterJoinContestRequest;
import com.app.kidsdrawing.dto.GetUserRegisterJoinContestResponse;
import com.app.kidsdrawing.service.UserRegisterJoinContestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user-register-join-contest")
public class UserRegisterJoinContestController {
    private final UserRegisterJoinContestService  userRegisterJoinContestService;

    @CrossOrigin
    @GetMapping(value = "/student/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserRegisterJoinContestByTeacherId(@PathVariable UUID id, @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(userRegisterJoinContestService.getAllUserRegisterJoinContestByTeacherId(page, size, id));
    } 

    @CrossOrigin
    @GetMapping(value = "/contest/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserRegisterJoinContestByContestId(@PathVariable UUID id, @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(userRegisterJoinContestService.getAllUserRegisterJoinContestByTeacherId(page, size, id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createUserRegisterJoinContest(@RequestBody CreateUserRegisterJoinContestRequest createUserRegisterJoinContestRequest) {
        UUID userRegisterJoinContestId = userRegisterJoinContestService.createUserRegisterJoinContest(createUserRegisterJoinContestRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userRegisterJoinContestId}")
                .buildAndExpand(userRegisterJoinContestId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateUserRegisterJoinContest(@PathVariable UUID id, @RequestBody CreateUserRegisterJoinContestRequest createUserRegisterJoinContestRequest) {
        UUID userRegisterJoinContestId = userRegisterJoinContestService.updateUserRegisterJoinContestById(id,createUserRegisterJoinContestRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userRegisterJoinContestId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserRegisterJoinContestResponse> getUserRegisterJoinContestById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(userRegisterJoinContestService.getUserRegisterJoinContestById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserRegisterJoinContestById(@PathVariable UUID id) {
        UUID userRegisterJoinContestId = userRegisterJoinContestService.removeUserRegisterJoinContestById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userRegisterJoinContestId).toUri();
        return ResponseEntity.created(location).build();
    }
}
