package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.UUID;

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
        UUID classId = classHasRegisterJoinSemesterClassService.updateClassHasRegisterJoinSemesterClassForStudent(createClassHasRegisterJoinSemesterClassStudentRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(classId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/teacher")
    public ResponseEntity<String> updateClassHasRegisterJoinSemesterClassForTeacher(@RequestBody CreateClassHasRegisterJoinSemesterClassTeacherRequest createClassHasRegisterJoinSemesterClassTeacherRequest) {
        UUID classId = classHasRegisterJoinSemesterClassService.updateClassHasRegisterJoinSemesterClassForTeacher(createClassHasRegisterJoinSemesterClassTeacherRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(classId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{classes_id}/{user_register_join_semester_id}")
    public ResponseEntity<GetClassHasRegisterJoinSemesterClassResponse> getClassHasRegisterJoinSemesterClassByClassesAndUserRegisterJoinSemester(@PathVariable("classes_id") UUID classes_id, @PathVariable("user_register_join_semester_id") UUID user_register_join_semester_id) {
        return ResponseEntity.ok().body(classHasRegisterJoinSemesterClassService.getClassHasRegisterJoinSemesterClassByClassesAndUserRegisterJoinSemester(classes_id, user_register_join_semester_id));
    }
}
