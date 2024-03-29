package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;


import com.app.kidsdrawing.dto.CreateClassHasRegisterJoinSemesterClassRequest;
import com.app.kidsdrawing.dto.CreateClassHasRegisterJoinSemesterClassStudentRequest;
import com.app.kidsdrawing.dto.CreateClassHasRegisterJoinSemesterClassTeacherRequest;
import com.app.kidsdrawing.dto.GetClassHasRegisterJoinSemesterClassResponse;
import com.app.kidsdrawing.dto.GetReviewStarForClassResponse;

public interface ClassHasRegisterJoinSemesterClassService {
    ResponseEntity<Map<String, Object>> getAllClassHasRegisterJoinSemesterClass();
    ResponseEntity<Map<String, Object>> getAllClassHasRegisterJoinSemesterClassByClass(Long class_id);
    GetReviewStarForClassResponse getReviewStarForClass(Long class_id);
    GetClassHasRegisterJoinSemesterClassResponse getClassHasRegisterJoinSemesterClassByClassesAndStudent(Long class_id, Long user_register_join_semester_id);
    Long createClassHasRegisterJoinSemesterClass(CreateClassHasRegisterJoinSemesterClassRequest createClassHasRegisterJoinSemesterClassRequest);
    Long removeClassHasRegisterJoinSemesterClassById(Long classes_id, Long user_register_join_semester);
    Long updateClassHasRegisterJoinSemesterClassById(CreateClassHasRegisterJoinSemesterClassRequest createClassHasRegisterJoinSemesterClassRequest);
    Long updateClassHasRegisterJoinSemesterClassForStudent(CreateClassHasRegisterJoinSemesterClassStudentRequest createClassHasRegisterJoinSemesterClassStudentRequest);
    Long updateClassHasRegisterJoinSemesterClassForTeacher(CreateClassHasRegisterJoinSemesterClassTeacherRequest createClassHasRegisterJoinSemesterClassTeacherRequest);
}
