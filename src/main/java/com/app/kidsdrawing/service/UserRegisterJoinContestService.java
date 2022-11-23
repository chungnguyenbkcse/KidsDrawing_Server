package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserRegisterJoinContestRequest;
import com.app.kidsdrawing.dto.GetUserRegisterJoinContestResponse;

public interface UserRegisterJoinContestService {
    ResponseEntity<Map<String, Object>> getAllUserRegisterJoinContestByStudentId(Long id);
    ResponseEntity<Map<String, Object>> getAllUserRegisterJoinContestByContestId(Long id);
    GetUserRegisterJoinContestResponse getUserRegisterJoinContestById(Long id);
    Long createUserRegisterJoinContest(CreateUserRegisterJoinContestRequest createUserRegisterJoinContestRequest);
    Long removeUserRegisterJoinContestById(Long id);
    Long removeUserRegisterJoinContestByContestAndStudent(Long contest_id, Long student_id);
    Long updateUserRegisterJoinContestById(Long id, CreateUserRegisterJoinContestRequest createUserRegisterJoinContestRequest);
}
