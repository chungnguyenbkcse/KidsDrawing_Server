package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateTeacherTeachSemesterRequest;
import com.app.kidsdrawing.dto.GetTeacherTeachSemesterResponse;

public interface UserRegisterTeachSemesterService {
    ResponseEntity<Map<String, Object>> getAllTeacherTeachSemester();
    ResponseEntity<Map<String, Object>> getAllTeacherTeachSemesterBySemesterClassSchedule(Long id);
    GetTeacherTeachSemesterResponse getTeacherTeachSemesterById(Long id);
    Long createTeacherTeachSemester(CreateTeacherTeachSemesterRequest createTeacherTeachSemesterRequest);
    Long removeTeacherTeachSemesterById(Long id);
    Long removeTeacherTeachSemesterBySemesterClassAndTeacher(Long semester_class_id, Long teacher_id);
    Boolean checkScheduleForTeacher(Long teacher_id, Long semester_class_id);
    Long updateTeacherTeachSemesterById(Long id, CreateTeacherTeachSemesterRequest createTeacherTeachSemesterRequest);
}
