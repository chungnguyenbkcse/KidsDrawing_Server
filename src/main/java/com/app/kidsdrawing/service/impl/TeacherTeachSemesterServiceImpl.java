package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateReviewTeacherTeachSemesterRequest;
import com.app.kidsdrawing.dto.CreateTeacherTeachSemesterRequest;
import com.app.kidsdrawing.dto.GetTeacherTeachSemesterResponse;
import com.app.kidsdrawing.entity.SemesterCourse;
import com.app.kidsdrawing.entity.TeacherTeachSemester;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.SemesterCourseRepository;
import com.app.kidsdrawing.repository.TeacherTeachSemesterRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.TeacherTeachSemesterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TeacherTeachSemesterServiceImpl implements TeacherTeachSemesterService{
    
    private final TeacherTeachSemesterRepository teacherTeachSemesterRepository;
    private final SemesterCourseRepository semesterCourseRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacherTeachSemester() {
        List<GetTeacherTeachSemesterResponse> allTeacherTeachSemesterResponses = new ArrayList<>();
        List<TeacherTeachSemester> listTeacherTeachSemester = teacherTeachSemesterRepository.findAll();
        listTeacherTeachSemester.forEach(content -> {
            GetTeacherTeachSemesterResponse teacherTeachSemesterResponse = GetTeacherTeachSemesterResponse.builder()
                .id(content.getId())
                .teacher_id(content.getTeacher().getId())
                .reviewer_id(content.getReviewer().getId())
                .semester_course_id(content.getSemesterCourse().getId())
                .status(content.getStatus())
                .time(content.getTime())
                .build();
            allTeacherTeachSemesterResponses.add(teacherTeachSemesterResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_semester", allTeacherTeachSemesterResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetTeacherTeachSemesterResponse getTeacherTeachSemesterById(Long id) {
        Optional<TeacherTeachSemester> teacherTeachSemesterOpt = teacherTeachSemesterRepository.findById(id);
        TeacherTeachSemester teacherTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherTeachSemester.not_found");
        });

        return GetTeacherTeachSemesterResponse.builder()
            .id(teacherTeachSemester.getId())
            .teacher_id(teacherTeachSemester.getTeacher().getId())
            .reviewer_id(teacherTeachSemester.getReviewer().getId())
            .semester_course_id(teacherTeachSemester.getSemesterCourse().getId())
            .status(teacherTeachSemester.getStatus())
            .time(teacherTeachSemester.getTime())
            .build();
    }

    @Override
    public Long createTeacherTeachSemester(CreateTeacherTeachSemesterRequest createTeacherTeachSemesterRequest) {
        Optional <SemesterCourse> semester_courseOpt = semesterCourseRepository.findById(createTeacherTeachSemesterRequest.getSemester_course_id());
        SemesterCourse semesterCouse = semester_courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_course.not_found");
        });

        Optional <User> teacherOpt = userRepository.findById(createTeacherTeachSemesterRequest.getTeacher_id());
        User teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });
        
        TeacherTeachSemester savedTeacherTeachSemester = TeacherTeachSemester.builder()
                .semesterCourse(semesterCouse)
                .teacher(teacher)
                .build();
        teacherTeachSemesterRepository.save(savedTeacherTeachSemester);

        return savedTeacherTeachSemester.getId();
    }

    @Override
    public Long removeTeacherTeachSemesterById(Long id) {
        Optional<TeacherTeachSemester> teacherTeachSemesterOpt = teacherTeachSemesterRepository.findById(id);
        teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherTeachSemester.not_found");
        });

        teacherTeachSemesterRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateTeacherTeachSemesterById(Long id, CreateTeacherTeachSemesterRequest createTeacherTeachSemesterRequest) {
        Optional<TeacherTeachSemester> teacherTeachSemesterOpt = teacherTeachSemesterRepository.findById(id);
        TeacherTeachSemester updatedTeacherTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherTeachSemester.not_found");
        });

        Optional <SemesterCourse> semester_courseOpt = semesterCourseRepository.findById(createTeacherTeachSemesterRequest.getSemester_course_id());
        SemesterCourse semesterCouse = semester_courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_course.not_found");
        });

        Optional <User> teacherOpt = userRepository.findById(createTeacherTeachSemesterRequest.getTeacher_id());
        User teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });

        updatedTeacherTeachSemester.setSemesterCourse(semesterCouse);
        updatedTeacherTeachSemester.setTeacher(teacher);

        return updatedTeacherTeachSemester.getId();
    }

    @Override
    public Long updateStatusTeacherTeachSemesterById(Long id, CreateReviewTeacherTeachSemesterRequest createReviewTeacherTeachSemesterRequest) {
        Optional<TeacherTeachSemester> teacherTeachSemesterOpt = teacherTeachSemesterRepository.findById(id);
        TeacherTeachSemester updatedTeacherTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherTeachSemester.not_found");
        });


        Optional <User> reviewerOpt = userRepository.findById(createReviewTeacherTeachSemesterRequest.getReviewer_id());
        User reviewer = reviewerOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_reviewer.not_found");
        });
        updatedTeacherTeachSemester.setReviewer(reviewer);
        updatedTeacherTeachSemester.setStatus(createReviewTeacherTeachSemesterRequest.getStatus());

        return updatedTeacherTeachSemester.getId();
    }
}
