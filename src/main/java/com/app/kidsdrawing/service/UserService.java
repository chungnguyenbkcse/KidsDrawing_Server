package com.app.kidsdrawing.service;

import java.util.Map;

import com.app.kidsdrawing.dto.CreateUserRequest;
import com.app.kidsdrawing.dto.GetUserInfoResponse;

import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Map<String, Object>> getAllUsers(int page, int size);

    GetUserInfoResponse getUserInfoById(Long id);

    GetUserInfoResponse getUserInfoByUsername(String username);

    GetUserInfoResponse getUserInfo();

    Long createUser(CreateUserRequest createUserRequest);
}
