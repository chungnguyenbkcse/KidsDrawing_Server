package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.Map;


import org.springframework.format.annotation.DateTimeFormat;
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

import com.app.kidsdrawing.dto.CreateContestRequest;
import com.app.kidsdrawing.dto.GetContestResponse;
import com.app.kidsdrawing.service.ContestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/contest")
public class ContestController {
    private final ContestService contestService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<GetContestResponse> createContest(@RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) CreateContestRequest createContestRequest) {
        return ResponseEntity.ok().body(contestService.createContest(createContestRequest));
    }

    @CrossOrigin
    @GetMapping(value = "/art-type/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllContestsByArtTypeid(@PathVariable Long id, @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(contestService.getAllContestByArtTypeId(page, size, id));
    }

    @CrossOrigin
    @GetMapping(value = "/teacher/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllContestsByTeacher(@PathVariable Long id) {
        return ResponseEntity.ok().body(contestService.getAllContestByTeacher(id));
    }

    @CrossOrigin
    @GetMapping(value = "/parent-new/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getContestNewByParent(@PathVariable Long id) {
        return ResponseEntity.ok().body(contestService.getContestNewByParent(id));
    }

    @CrossOrigin
    @GetMapping(value = "/total")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getTotalContest() {
        return ResponseEntity.ok().body(contestService.getTotalContest());
    }

    @CrossOrigin
    @GetMapping(value = "/student/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllContestByStudent(@PathVariable Long id) {
        return ResponseEntity.ok().body(contestService.getAllContestByStudent(id));
    }

    @CrossOrigin
    @GetMapping(value = "/student/total/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getTotalContestForStudent(@PathVariable Long id) {
        return ResponseEntity.ok().body(contestService.getTotalContestForStudent(id));
    }

    @CrossOrigin
    @GetMapping(value = "/parent/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllContestByParent(@PathVariable Long id) {
        return ResponseEntity.ok().body(contestService.getAllContestByParent(id));
    }

    @CrossOrigin
    @GetMapping(value = "/art-age/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllContestsByArtAgeid(@PathVariable Long id, @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(contestService.getAllContestByArtAgeId(page, size, id));
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateContest(@PathVariable Long id, @RequestBody CreateContestRequest createContestRequest) {
        Long contestId = contestService.updateContestById(id,createContestRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(contestId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllContests(@RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok().body(contestService.getAllContest(page, size));
    } 

    @CrossOrigin
    @GetMapping(value = "/id/{id}")
    public ResponseEntity<GetContestResponse> getContestById(@PathVariable Long id) {
        return ResponseEntity.ok().body(contestService.getContestById(id));
    }

    @CrossOrigin
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<GetContestResponse> getContestByName(@PathVariable String name) {
        return ResponseEntity.ok().body(contestService.getContestByName(name));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteContestById(@PathVariable Long id) {
        Long contestId = contestService.removeContestById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(contestId).toUri();
        return ResponseEntity.created(location).build();
    }
}
