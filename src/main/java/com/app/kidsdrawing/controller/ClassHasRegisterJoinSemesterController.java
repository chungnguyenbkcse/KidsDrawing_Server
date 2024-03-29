package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.kidsdrawing.dto.CreateClassHasRegisterJoinSemesterClassStudentRequest;
import com.app.kidsdrawing.dto.CreateClassHasRegisterJoinSemesterClassTeacherRequest;
import com.app.kidsdrawing.dto.GetClassHasRegisterJoinSemesterClassResponse;
import com.app.kidsdrawing.dto.GetReviewStarForClassResponse;
import com.app.kidsdrawing.service.ClassHasRegisterJoinSemesterClassService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/class-has-register-join-semester")
public class ClassHasRegisterJoinSemesterController {
    private final ClassHasRegisterJoinSemesterClassService classHasRegisterJoinSemesterClassService;

    @CrossOrigin
    @PutMapping(value = "/student")
    public ResponseEntity<String> updateClassHasRegisterJoinSemesterClassForStudent(@RequestBody CreateClassHasRegisterJoinSemesterClassStudentRequest createClassHasRegisterJoinSemesterClassStudentRequest) {
        Long classId = classHasRegisterJoinSemesterClassService.updateClassHasRegisterJoinSemesterClassForStudent(createClassHasRegisterJoinSemesterClassStudentRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(classId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/teacher")
    public ResponseEntity<String> updateClassHasRegisterJoinSemesterClassForTeacher(@RequestBody CreateClassHasRegisterJoinSemesterClassTeacherRequest createClassHasRegisterJoinSemesterClassTeacherRequest) {
        Long classId = classHasRegisterJoinSemesterClassService.updateClassHasRegisterJoinSemesterClassForTeacher(createClassHasRegisterJoinSemesterClassTeacherRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(classId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value="/classes/{class_id}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllClassHasRegisterJoinSemesterClassByClass(@PathVariable("class_id") Long class_id) {
        return ResponseEntity.ok().body(classHasRegisterJoinSemesterClassService.getAllClassHasRegisterJoinSemesterClassByClass(class_id));
    }

    @CrossOrigin
    @GetMapping(value = "/{classes_id}/{student_id}")
    public ResponseEntity<GetClassHasRegisterJoinSemesterClassResponse> getClassHasRegisterJoinSemesterClassByClassesAndStudent(@PathVariable("classes_id") Long classes_id, @PathVariable("student_id") Long student_id) {
        return ResponseEntity.ok().body(classHasRegisterJoinSemesterClassService.getClassHasRegisterJoinSemesterClassByClassesAndStudent(classes_id, student_id));
    }

    @CrossOrigin
    @GetMapping(value = "/review-star/{classes_id}")
    public ResponseEntity<GetReviewStarForClassResponse> getClassHasRegisterJoinSemesterClassByClassesAndStudent(@PathVariable("classes_id") Long classes_id) {
        return ResponseEntity.ok().body(classHasRegisterJoinSemesterClassService.getReviewStarForClass(classes_id));
    }
}
