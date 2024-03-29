package com.app.kidsdrawing.service;

import java.util.Map;


import com.app.kidsdrawing.dto.CreateChangePassowrdRequest;
import com.app.kidsdrawing.dto.CreateTeacherRequest;
import com.app.kidsdrawing.dto.CreateTeacherUserRequest;
import com.app.kidsdrawing.dto.CreateUserRequest;
import com.app.kidsdrawing.dto.CreateUserStatusRequest;
import com.app.kidsdrawing.dto.GetUserInfoResponse;
import com.app.kidsdrawing.entity.User;

import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Map<String, Object>> getAllStudents(String role_name);

    ResponseEntity<Map<String, Object>> getTotalStudents();

    ResponseEntity<Map<String, Object>> getAllUser();

    ResponseEntity<Map<String, Object>> getAllParents(String role_name);

    ResponseEntity<Map<String, Object>> getTotalParents();

    ResponseEntity<Map<String, Object>> getReportUserNew(int year, String role_name);

    ResponseEntity<Map<String, Object>> getAllTeacher();

    ResponseEntity<Map<String, Object>> getTotalTeachers();

    GetUserInfoResponse getUserInfoById(Long id);

    GetUserInfoResponse getUserInfoByUsername(String username);

    GetUserInfoResponse getUserInfoStudentById(Long id);

    GetUserInfoResponse getUserInfoStudentByUsername(String username);

    GetUserInfoResponse getUserInfo();

    Long createUser(CreateUserRequest createUserRequest);

    Long createTeacher(CreateTeacherRequest createTeacherRequest);

    Long updateUser(Long id, CreateUserRequest createUserRequest);

    Long updateTeacher(Long id, CreateTeacherUserRequest createTeacherUserRequest);

    Long updateUserStatus(Long id, CreateUserStatusRequest createUserStatusRequest);
    
    Long updatePassword(Long id, CreateChangePassowrdRequest createChangePassowrdRequest);

    Long removeUser(Long id);

    void changeUserPassword(User user, String password);

    ResponseEntity<Map<String, Object>> getAllChildForParentId(Long id);
}
