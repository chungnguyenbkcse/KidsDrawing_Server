package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.kidsdrawing.dto.CreateMomoRequest;
import com.app.kidsdrawing.dto.CreateUserRegisterJoinSemesterRequest;
import com.app.kidsdrawing.dto.GetUserRegisterJoinSemesterResponse;
import com.app.kidsdrawing.service.UserRegisterJoinSemesterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user-register-join-semester")
public class UserRegisterJoinSemesterController {
    private final UserRegisterJoinSemesterService  userRegisterJoinSemesterService;
    
    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserRegisterJoinSemester() {
        return ResponseEntity.ok().body(userRegisterJoinSemesterService.getAllUserRegisterJoinSemester());
    }

    @CrossOrigin
    @GetMapping(value = "/semester-class/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserRegisterJoinSemesterBySemesterClass(@PathVariable Long id) {
        return ResponseEntity.ok().body(userRegisterJoinSemesterService.getAllUserRegisterJoinSemesterBySemesterClass(id));
    }

    @CrossOrigin
    @GetMapping(value = "/semester-class/schedule-class/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserRegisterJoinSemesterBySemesterClassScheduleClass(@PathVariable Long id) {
        return ResponseEntity.ok().body(userRegisterJoinSemesterService.getAllUserRegisterJoinSemesterBySemesterClassScheduleClass(id));
    }

    @CrossOrigin
    @GetMapping(value = "/payer/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserRegisterJoinSemesterByPayerId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userRegisterJoinSemesterService.getAllUserRegisterJoinSemesterByPayerId(id));
    }

    @CrossOrigin
    @GetMapping(value = "/total-money")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllMoneyUserRegisterJoinSemester() {
        return ResponseEntity.ok().body(userRegisterJoinSemesterService.getAllMoneyUserRegisterJoinSemester());
    }

    @CrossOrigin
    @GetMapping(value = "/report/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getReportUserRegisterJoinSemester(@PathVariable int id) {
        return ResponseEntity.ok().body(userRegisterJoinSemesterService.getReportUserRegisterJoinSemester(id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createUserRegisterJoinSemester(@RequestBody CreateUserRegisterJoinSemesterRequest createUserRegisterJoinSemesterRequest) {
        Long userRegisterJoinSemesterId = userRegisterJoinSemesterService.createUserRegisterJoinSemester(createUserRegisterJoinSemesterRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userRegisterJoinSemesterId}")
                .buildAndExpand(userRegisterJoinSemesterId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PostMapping("/payment")
    public ResponseEntity<String> updateStatusUserRegisterJoinSemester(@RequestParam List<Long> ids, @RequestBody CreateMomoRequest createMomoRequest) {
        Long userRegisterJoinSemesterId = userRegisterJoinSemesterService.updateStatusUserRegisterJoinSemester(ids, createMomoRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userRegisterJoinSemesterId}")
                .buildAndExpand(userRegisterJoinSemesterId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PostMapping("/payment/v2")
    public ResponseEntity<String> updateStatusUserRegisterJoinSemester(@RequestParam List<Long> ids) {
        Long userRegisterJoinSemesterId = userRegisterJoinSemesterService.updateStatusUserRegisterJoinSemester(ids);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userRegisterJoinSemesterId}")
                .buildAndExpand(userRegisterJoinSemesterId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateUserRegisterJoinSemester(@PathVariable Long id, @RequestBody CreateUserRegisterJoinSemesterRequest createUserRegisterJoinSemesterRequest) {
        Long userRegisterJoinSemesterId = userRegisterJoinSemesterService.updateUserRegisterJoinSemesterById(id,createUserRegisterJoinSemesterRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userRegisterJoinSemesterId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserRegisterJoinSemesterResponse> getUserRegisterJoinSemesterById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userRegisterJoinSemesterService.getUserRegisterJoinSemesterById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserRegisterJoinSemesterById(@PathVariable Long id) {
        Long userRegisterJoinSemesterId = userRegisterJoinSemesterService.removeUserRegisterJoinSemesterById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(userRegisterJoinSemesterId).toUri();
        return ResponseEntity.created(location).build();
    }
}
