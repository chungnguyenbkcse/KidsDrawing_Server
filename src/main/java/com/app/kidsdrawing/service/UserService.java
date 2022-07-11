package com.app.kidsdrawing.service;

import java.util.Map;

import com.app.kidsdrawing.dto.CreateUserRequest;
import com.app.kidsdrawing.dto.GetUserInfoResponse;

import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Map<String, Object>> getAllUsers(Long role_id);

    GetUserInfoResponse getUserInfoById(Long id);

    GetUserInfoResponse getUserInfoByUsername(String username);

    GetUserInfoResponse getUserInfo();

    Long createUser(CreateUserRequest createUserRequest);

    Long updateUser(Long id, CreateUserRequest createUserRequest);

    Long removeUser(Long id);
}
