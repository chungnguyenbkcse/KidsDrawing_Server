package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateClassHasRegisterJoinSemesterClassRequest;
import com.app.kidsdrawing.dto.GetClassHasRegisterJoinSemesterClassResponse;

public interface ClassHasRegisterJoinSemesterClassService {
    ResponseEntity<Map<String, Object>> getAllClassHasRegisterJoinSemesterClass();
    GetClassHasRegisterJoinSemesterClassResponse getClassHasRegisterJoinSemesterClassById(Long id);
    Long createClassHasRegisterJoinSemesterClass(CreateClassHasRegisterJoinSemesterClassRequest createClassHasRegisterJoinSemesterClassRequest);
    Long removeClassHasRegisterJoinSemesterClassById(Long classes_id, Long user_register_join_semester);
    Long updateClassHasRegisterJoinSemesterClassById(CreateClassHasRegisterJoinSemesterClassRequest createClassHasRegisterJoinSemesterClassRequest);
}
