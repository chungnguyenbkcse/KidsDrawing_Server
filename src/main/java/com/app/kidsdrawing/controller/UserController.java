package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.Map;

import com.app.kidsdrawing.dto.CreateUserRequest;
import com.app.kidsdrawing.dto.GetUserInfoResponse;
import com.app.kidsdrawing.service.UserService;

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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
    private final UserService userService;
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest createUserRequest) {
        Long userId = userService.createUser(createUserRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}")
                .buildAndExpand(userId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/teacher")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllTeacher() {
        return ResponseEntity.ok().body(userService.getAllTeacher());
    } 

    @CrossOrigin
    @GetMapping(value = "/student")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllStudent() {
        Long role_id = (long) 2;
        return ResponseEntity.ok().body(userService.getAllUsers(role_id));
    } 

    @CrossOrigin
    @GetMapping(value = "/parent")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllParent() {
        Long role_id = (long) 3;
        return ResponseEntity.ok().body(userService.getAllUsers(role_id));
    } 

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserInfoResponse> getUserInfoById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getUserInfoById(id));
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateUserInfoById(@PathVariable Long id, @RequestBody CreateUserRequest createUserRequest) {
        Long userId = userService.updateUser(id, createUserRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        Long userId = userService.removeUser(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userId).toUri();
        return ResponseEntity.created(location).build();
    }
}
