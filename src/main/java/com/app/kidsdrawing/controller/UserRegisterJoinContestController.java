package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.kidsdrawing.dto.CreateUserRegisterJoinContestRequest;
import com.app.kidsdrawing.dto.GetUserRegisterJoinContestResponse;
import com.app.kidsdrawing.service.UserRegisterJoinContestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user-register-join-contest")
public class UserRegisterJoinContestController {
    private final UserRegisterJoinContestService  userRegisterJoinContestService;

    @CrossOrigin
    @GetMapping(value = "/student/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserRegisterJoinContestByTeacherId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userRegisterJoinContestService.getAllUserRegisterJoinContestByStudentId(id));
    } 

    @CrossOrigin
    @GetMapping(value = "/contest-parent/{contest_id}/{parent_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserRegisterJoinContestByContestIdAndParentId(@PathVariable("contest_id") Long contest_id, @PathVariable("parent_id") Long parent_id) {
        return ResponseEntity.ok().body(userRegisterJoinContestService.getAllUserRegisterJoinContestByContestIdAndParentId(contest_id, parent_id));
    } 

    @CrossOrigin
    @GetMapping(value = "/contest/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserRegisterJoinContestByContestId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userRegisterJoinContestService.getAllUserRegisterJoinContestByContestId(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createUserRegisterJoinContest(@RequestBody CreateUserRegisterJoinContestRequest createUserRegisterJoinContestRequest) {
        Long userRegisterJoinContestId = userRegisterJoinContestService.createUserRegisterJoinContest(createUserRegisterJoinContestRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userRegisterJoinContestId}")
                .buildAndExpand(userRegisterJoinContestId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateUserRegisterJoinContest(@PathVariable Long id, @RequestBody CreateUserRegisterJoinContestRequest createUserRegisterJoinContestRequest) {
        Long userRegisterJoinContestId = userRegisterJoinContestService.updateUserRegisterJoinContestById(id,createUserRegisterJoinContestRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userRegisterJoinContestId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserRegisterJoinContestResponse> getUserRegisterJoinContestById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userRegisterJoinContestService.getUserRegisterJoinContestById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserRegisterJoinContestById(@PathVariable Long id) {
        Long userRegisterJoinContestId = userRegisterJoinContestService.removeUserRegisterJoinContestById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userRegisterJoinContestId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @DeleteMapping(value = "/contest-student/{contest_id}/{student_id}")
    public ResponseEntity<String> deleteUserRegisterJoinContestById(@PathVariable("contest_id") Long contest_id, @PathVariable("student_id") Long student_id) {
        Long userRegisterJoinContestId = userRegisterJoinContestService.removeUserRegisterJoinContestByContestAndStudent(contest_id, student_id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userRegisterJoinContestId).toUri();
        return ResponseEntity.created(location).build();
    }
}
