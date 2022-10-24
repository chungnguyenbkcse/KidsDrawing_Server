package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import com.app.kidsdrawing.dto.CreateChangePassowrdRequest;
import com.app.kidsdrawing.dto.CreateStudentRequest;
import com.app.kidsdrawing.dto.CreateTeacherRequest;
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

    GetUserInfoResponse getUserInfoById(UUID id);

    GetUserInfoResponse getUserInfoByUsername(String username);

    GetUserInfoResponse getUserInfo();

    UUID createUser(CreateUserRequest createUserRequest);

    UUID createTeacher(CreateTeacherRequest createTeacherRequest);

    UUID createStudent(CreateStudentRequest createStudentRequest);

    UUID updateUser(UUID id, CreateUserRequest createUserRequest);

    UUID updateUserStatus(UUID id, CreateUserStatusRequest createUserStatusRequest);
    
    UUID updatePassword(UUID id, CreateChangePassowrdRequest createChangePassowrdRequest);

    UUID removeUser(UUID id);

    void changeUserPassword(User user, String password);

    ResponseEntity<Map<String, Object>> getAllChildForParentId(UUID id);
}
