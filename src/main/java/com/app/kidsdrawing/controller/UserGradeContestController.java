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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.kidsdrawing.dto.CreateUserGradeContestRequest;
import com.app.kidsdrawing.dto.GetUserGradeContestResponse;
import com.app.kidsdrawing.service.UserGradeContestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user-grade-contest")
public class UserGradeContestController {
    private final UserGradeContestService  userGradeContestService;

    @CrossOrigin
    @GetMapping(value = "/teacher/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeContestByTeacherId(@PathVariable Long id, @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(userGradeContestService.getAllUserGradeContestByTeacherId(page, size, id));
    } 

    @CrossOrigin
    @GetMapping(value = "/contest/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllUserGradeContestByContestId(@PathVariable Long id, @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(userGradeContestService.getAllUserGradeContestByTeacherId(page, size, id));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createUserGradeContest(@RequestBody CreateUserGradeContestRequest createUserGradeContestRequest) {
        Long userGradeContestId = userGradeContestService.createUserGradeContest(createUserGradeContestRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userGradeContestId}")
                .buildAndExpand(userGradeContestId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateUserGradeContest(@PathVariable Long id, @RequestBody CreateUserGradeContestRequest createUserGradeContestRequest) {
        Long UserGradeContestId = userGradeContestService.updateUserGradeContestById(id,createUserGradeContestRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(UserGradeContestId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserGradeContestResponse> getUserGradeContestById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userGradeContestService.getUserGradeContestById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserGradeContestById(@PathVariable Long id) {
        Long UserGradeContestId = userGradeContestService.removeUserGradeContestById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(UserGradeContestId).toUri();
        return ResponseEntity.created(location).build();
    }
}
