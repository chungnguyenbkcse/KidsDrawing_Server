package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateTeacherTeachSemesterRequest;
import com.app.kidsdrawing.dto.GetTeacherTeachSemesterResponse;

public interface UserRegisterTeachSemesterService {
    ResponseEntity<Map<String, Object>> getAllTeacherTeachSemester();
    ResponseEntity<Map<String, Object>> getAllTeacherTeachSemesterBySemesterClassSchedule(UUID id);
    GetTeacherTeachSemesterResponse getTeacherTeachSemesterById(UUID id);
    UUID createTeacherTeachSemester(CreateTeacherTeachSemesterRequest createTeacherTeachSemesterRequest);
    UUID removeTeacherTeachSemesterById(UUID id);
    UUID updateTeacherTeachSemesterById(UUID id, CreateTeacherTeachSemesterRequest createTeacherTeachSemesterRequest);
}
