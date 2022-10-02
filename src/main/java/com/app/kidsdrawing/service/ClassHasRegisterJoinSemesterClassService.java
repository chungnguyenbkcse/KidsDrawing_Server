package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import java.util.UUID;

import com.app.kidsdrawing.dto.CreateClassHasRegisterJoinSemesterClassRequest;
import com.app.kidsdrawing.dto.CreateClassHasRegisterJoinSemesterClassStudentRequest;
import com.app.kidsdrawing.dto.CreateClassHasRegisterJoinSemesterClassTeacherRequest;
import com.app.kidsdrawing.dto.GetClassHasRegisterJoinSemesterClassResponse;

public interface ClassHasRegisterJoinSemesterClassService {
    ResponseEntity<Map<String, Object>> getAllClassHasRegisterJoinSemesterClass();
    GetClassHasRegisterJoinSemesterClassResponse getClassHasRegisterJoinSemesterClassByClassesAndUserRegisterJoinSemester(UUID class_id, UUID user_register_join_semester_id);
    UUID createClassHasRegisterJoinSemesterClass(CreateClassHasRegisterJoinSemesterClassRequest createClassHasRegisterJoinSemesterClassRequest);
    UUID removeClassHasRegisterJoinSemesterClassById(UUID classes_id, UUID user_register_join_semester);
    UUID updateClassHasRegisterJoinSemesterClassById(CreateClassHasRegisterJoinSemesterClassRequest createClassHasRegisterJoinSemesterClassRequest);
    UUID updateClassHasRegisterJoinSemesterClassForStudent(CreateClassHasRegisterJoinSemesterClassStudentRequest createClassHasRegisterJoinSemesterClassStudentRequest);
    UUID updateClassHasRegisterJoinSemesterClassForTeacher(CreateClassHasRegisterJoinSemesterClassTeacherRequest createClassHasRegisterJoinSemesterClassTeacherRequest);
}
