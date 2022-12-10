package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserGradeContestRequest;

public interface UserGradeContestService {
    ResponseEntity<Map<String, Object>> getAllUserGradeContestByTeacherId(Long id);
    ResponseEntity<Map<String, Object>> getAllUserGradeContestByContestId(Long id);
    Long createUserGradeContest(CreateUserGradeContestRequest createUserGradeContestRequest);
    Long removeUserGradeContestById(Long contest_id, Long student_id);
    Long removeUserGradeContestByContest(Long id);
    Long updateUserGradeContestById(CreateUserGradeContestRequest createUserGradeContestRequest);
}
