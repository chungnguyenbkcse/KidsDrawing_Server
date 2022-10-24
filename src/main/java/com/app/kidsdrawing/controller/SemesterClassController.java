package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

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

import com.app.kidsdrawing.dto.CreateSemesterClassRequest;
import com.app.kidsdrawing.dto.GetSemesterClassResponse;
import com.app.kidsdrawing.service.SemesterClassService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/semester-class")
public class SemesterClassController {
    private final SemesterClassService semesterCourseService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<GetSemesterClassResponse> createSemesterClass(@RequestBody CreateSemesterClassRequest createSemesterClassRequest) {
        return ResponseEntity.ok().body(semesterCourseService.createSemesterClass(createSemesterClassRequest));
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateSemesterClass(@PathVariable UUID id, @RequestBody CreateSemesterClassRequest createSemesterClassRequest) {
        UUID semesterCourseId = semesterCourseService.updateSemesterClassById(id,createSemesterClassRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(semesterCourseId).toUri();
        return ResponseEntity.created(location).build();
    }
    
    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSemesterClasssBySemester() {
        return ResponseEntity.ok().body(semesterCourseService.getAllSemesterClass());
    }

    @CrossOrigin
    @GetMapping(value = "/history/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSemesterClassHistoryOfStudent(@PathVariable UUID id) {
        return ResponseEntity.ok().body(semesterCourseService.getAllSemesterClassHistoryOfStudent(id));
    }

    @CrossOrigin
    @GetMapping(value = "/present/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSemesterClassPresentOfStudent(@PathVariable UUID id) {
        return ResponseEntity.ok().body(semesterCourseService.getAllSemesterClassPresentOfStudent(id));
    }

    @CrossOrigin
    @GetMapping(value = "/new")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSemesterClassNew() {
        return ResponseEntity.ok().body(semesterCourseService.getAllSemesterClassNew());
    }

    @CrossOrigin
    @GetMapping(value = "/semester/schedule-class/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSemesterClassBySemesterScheduleClass(@PathVariable UUID id) {
        return ResponseEntity.ok().body(semesterCourseService.getAllSemesterClassBySemesterScheduleClass(id));
    }

    @CrossOrigin
    @GetMapping(value = "/semester/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSemesterClasssBySemesterBySemester(@PathVariable UUID id) {
        return ResponseEntity.ok().body(semesterCourseService.getAllSemesterClassBySemester(id));
    }

    @CrossOrigin
    @GetMapping(value = "/course/{id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSemesterClasssBySemesterByCourse(@PathVariable UUID id) {
        return ResponseEntity.ok().body(semesterCourseService.getAllSemesterClassByCourse(id));
    }

    @CrossOrigin
    @GetMapping(value = "/student-course/{student_id}/{course_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSemesterClassNewByStudentAndCourse(@PathVariable("student_id") UUID student_id, @PathVariable("course_id") UUID course_id) {
        return ResponseEntity.ok().body(semesterCourseService.getAllSemesterClassNewByStudentAndCourse(student_id, course_id));
    }

    @CrossOrigin
    @GetMapping(value = "/parent-course/{parent_id}/{course_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSemesterClassNewByParentAndCourse(@PathVariable("parent_id") UUID parent_id, @PathVariable("course_id") UUID course_id) {
        return ResponseEntity.ok().body(semesterCourseService.getAllSemesterClassNewByParentAndCourse(parent_id, course_id));
    }

    @CrossOrigin
    @GetMapping(value = "/teacher-course/{teacher_id}/{course_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllSemesterClassNewByTeacherAndCourse(@PathVariable("teacher_id") UUID teacher_id, @PathVariable("course_id") UUID course_id) {
        return ResponseEntity.ok().body(semesterCourseService.getAllSemesterClassNewByTeacherAndCourse(teacher_id, course_id));
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetSemesterClassResponse> getSemesterClassById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(semesterCourseService.getSemesterClassById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteSemesterClassById(@PathVariable UUID id) {
        UUID SemesterClassId = semesterCourseService.removeSemesterClassById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(SemesterClassId).toUri();
        return ResponseEntity.created(location).build();
    }
}
