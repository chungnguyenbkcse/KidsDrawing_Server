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

import com.app.kidsdrawing.dto.CreateSemesterRequest;
import com.app.kidsdrawing.dto.GetSemesterResponse;
import com.app.kidsdrawing.service.SemesterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/semester")
public class SemesterController {
    private final SemesterService semesterService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createSemester(@RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) CreateSemesterRequest createSemesterRequest) {
        Long semesterId = semesterService.createSemester(createSemesterRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{semesterId}")
                .buildAndExpand(semesterId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PostMapping(value = "/schedule-class/{id}")
    public ResponseEntity<String> createClassBySemester(@PathVariable Long id, @RequestParam(defaultValue = "6") int partion,
    @RequestParam(defaultValue = "5") int min, @RequestParam(defaultValue = "8") int max) {
        Long semesterId = semesterService.setClassForSemester(id, partion, min, max);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{semesterId}")
                .buildAndExpand(semesterId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateSemester(@PathVariable Long id, @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) CreateSemesterRequest createSemesterRequest) {
        Long semesterId = semesterService.updateSemesterById(id,createSemesterRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(semesterId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value="/next")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSemestersNext() {
        return ResponseEntity.ok().body(semesterService.getAllSemesterNext());
    }

    @CrossOrigin
    @GetMapping(value="/calender/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getCalendarForSemster(@PathVariable Long id) {
        return ResponseEntity.ok().body(semesterService.setCalenderForSemester(id));
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSemesters() {
        return ResponseEntity.ok().body(semesterService.getAllSemester());
    } 

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetSemesterResponse> getSemesterById(@PathVariable Long id) {
        return ResponseEntity.ok().body(semesterService.getSemesterById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteSemesterById(@PathVariable Long id) {
        Long semesterId = semesterService.removeSemesterById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(semesterId).toUri();
        return ResponseEntity.created(location).build();
    }
}
