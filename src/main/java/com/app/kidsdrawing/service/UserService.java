package com.app.kidsdrawing.service;

import java.util.Map;

import com.app.kidsdrawing.dto.CreateChangePassowrdRequest;
import com.app.kidsdrawing.dto.CreateStudentRequest;
import com.app.kidsdrawing.dto.CreateTeacherRequest;
import com.app.kidsdrawing.dto.CreateUserRequest;
import com.app.kidsdrawing.dto.CreateUserStatusRequest;
import com.app.kidsdrawing.dto.GetUserInfoResponse;
import com.app.kidsdrawing.entity.User;

import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Map<String, Object>> getAllStudents(Long role_id);

    ResponseEntity<Map<String, Object>> getAllUser();

    ResponseEntity<Map<String, Object>> getAllParents(Long role_id);

    ResponseEntity<Map<String, Object>> getReportUserNew(int year, Long role_id);

    ResponseEntity<Map<String, Object>> getAllTeacher();

    GetUserInfoResponse getUserInfoById(Long id);

    GetUserInfoResponse getUserInfoByUsername(String username);

    GetUserInfoResponse getUserInfo();

    Long createUser(CreateUserRequest createUserRequest);

    Long createTeacher(CreateTeacherRequest createTeacherRequest);

    Long createStudent(CreateStudentRequest createStudentRequest);

    Long updateUser(Long id, CreateUserRequest createUserRequest);

    Long updateUserStatus(Long id, CreateUserStatusRequest createUserStatusRequest);
    
    Long updatePassword(Long id, CreateChangePassowrdRequest createChangePassowrdRequest);

    Long removeUser(Long id);

    void changeUserPassword(User user, String password);

    ResponseEntity<Map<String, Object>> getAllChildForParentId(Long id);
}
