package com.app.kidsdrawing.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateTeacherRegisterQualificationAdminRequest;
import com.app.kidsdrawing.dto.CreateTeacherRegisterQualificationRequest;
import com.app.kidsdrawing.dto.GetTeacherRegisterQualificationResponse;
import com.app.kidsdrawing.dto.GetTeacherRegisterQuanlificationTeacherResponse;
import com.app.kidsdrawing.entity.Course;
import com.app.kidsdrawing.entity.Teacher;
import com.app.kidsdrawing.entity.TeacherRegisterQualification;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.CourseRepository;
import com.app.kidsdrawing.repository.TeacherRegisterQualificationRepository;
import com.app.kidsdrawing.repository.TeacherRepository;
import com.app.kidsdrawing.service.TeacherRegisterQualificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TeacherRegisterQualificationServiceImpl implements TeacherRegisterQualificationService{
    
    private final TeacherRegisterQualificationRepository teacherRegisterQualificationRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacherRegisterQualificationApprovedByTeacherId(Long id) {
        List<GetTeacherRegisterQuanlificationTeacherResponse> allTeacherRegisterQualificationApprovedResponses = new ArrayList<>();
        List<GetTeacherRegisterQuanlificationTeacherResponse> allTeacherRegisterQualificationNotApprovedResponses = new ArrayList<>();
        List<GetTeacherRegisterQuanlificationTeacherResponse> allTeacherRegisterQualificationNotApproveNowResponses = new ArrayList<>();
        List<TeacherRegisterQualification> pageTeacherRegisterQualification = teacherRegisterQualificationRepository.findByTeacherId2(id);
        pageTeacherRegisterQualification.forEach(ele -> {
                if (ele.getStatus().equals("Approved")){
                    GetTeacherRegisterQuanlificationTeacherResponse teacherRegisterQuanlificationTeacherResponse = GetTeacherRegisterQuanlificationTeacherResponse.builder()
                        .id(ele.getId())
                        .time_approved(ele.getTime_approved())
                        .teacher_id(ele.getTeacher().getId())
                        .teacher_name(ele.getTeacher().getUser().getUsername() + " - " + ele.getTeacher().getUser().getFirstName() + " " + ele.getTeacher().getUser().getLastName())
                        .course_id(ele.getCourse().getId())
                        .course_name(ele.getCourse().getName())
                        .degree_photo_url(ele.getDegree_photo_url())
                        .status(ele.getStatus())
                        .build();
                    allTeacherRegisterQualificationApprovedResponses.add(teacherRegisterQuanlificationTeacherResponse);
                }
                else if (ele.getStatus().equals("Not approved")){
                    GetTeacherRegisterQuanlificationTeacherResponse teacherRegisterQuanlificationTeacherResponse = GetTeacherRegisterQuanlificationTeacherResponse.builder()
                        .id(ele.getId())
                        .teacher_id(ele.getTeacher().getId())
                        .teacher_name(ele.getTeacher().getUser().getUsername() + " - " + ele.getTeacher().getUser().getFirstName() + " " + ele.getTeacher().getUser().getLastName())
                        .time_approved(ele.getTime_approved())
                        .course_id(ele.getCourse().getId())
                        .course_name(ele.getCourse().getName())
                        .degree_photo_url(ele.getDegree_photo_url())
                        .status(ele.getStatus())
                        .build();
                    allTeacherRegisterQualificationNotApprovedResponses.add(teacherRegisterQuanlificationTeacherResponse);
                }
                else if (ele.getStatus().equals("Not approve now")){
                    GetTeacherRegisterQuanlificationTeacherResponse teacherRegisterQuanlificationTeacherResponse = GetTeacherRegisterQuanlificationTeacherResponse.builder()
                        .id(ele.getId())
                        .teacher_id(ele.getTeacher().getId())
                        .teacher_name(ele.getTeacher().getUser().getUsername() + " - " + ele.getTeacher().getUser().getFirstName() + " " + ele.getTeacher().getUser().getLastName())
                        .time_approved(ele.getTime_approved())
                        .course_id(ele.getCourse().getId())
                        .course_name(ele.getCourse().getName())
                        .degree_photo_url(ele.getDegree_photo_url())
                        .status(ele.getStatus())
                        .build();
                    allTeacherRegisterQualificationNotApproveNowResponses.add(teacherRegisterQuanlificationTeacherResponse);
                }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("approved", allTeacherRegisterQualificationApprovedResponses);
        response.put("not_approved", allTeacherRegisterQualificationNotApprovedResponses);
        response.put("not_approved_now", allTeacherRegisterQualificationNotApproveNowResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacherRegisterQualification() {
        List<GetTeacherRegisterQualificationResponse> allTeacherRegisterQualificationResponses = new ArrayList<>();
        List<TeacherRegisterQualification> pageTeacherRegisterQualification = teacherRegisterQualificationRepository.findAll();
        pageTeacherRegisterQualification.forEach(content -> {
            GetTeacherRegisterQualificationResponse teacherRegisterQualificationResponse = GetTeacherRegisterQualificationResponse.builder()
                .id(content.getId())
                .teacher_id(content.getTeacher().getId())
                .time_approved(content.getTime_approved())
                .course_id(content.getCourse().getId())
                .degree_photo_url(content.getDegree_photo_url())
                .status(content.getStatus())
                .build();
                allTeacherRegisterQualificationResponses.add(teacherRegisterQualificationResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_register_qualification", allTeacherRegisterQualificationResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacherRegisterQualificationByTeacherId(Long id) {
        List<GetTeacherRegisterQualificationResponse> allTeacherRegisterQualificationResponses = new ArrayList<>();
        List<TeacherRegisterQualification> pageTeacherRegisterQualification = teacherRegisterQualificationRepository.findAll();
        pageTeacherRegisterQualification.forEach(content -> {
            if (content.getTeacher().getId().compareTo(id) == 0){
                GetTeacherRegisterQualificationResponse teacherRegisterQualificationResponse = GetTeacherRegisterQualificationResponse.builder()
                    .id(content.getId())
                    .teacher_id(content.getTeacher().getId())
                    .course_id(content.getCourse().getId())
                    .time_approved(content.getTime_approved())
                    .degree_photo_url(content.getDegree_photo_url())
                    .status(content.getStatus())
                    .build();
                    allTeacherRegisterQualificationResponses.add(teacherRegisterQualificationResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_register_qualification", allTeacherRegisterQualificationResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetTeacherRegisterQualificationResponse getTeacherRegisterQualificationById(Long id) {
        Optional<TeacherRegisterQualification> teacherRegisterQualificationOpt = teacherRegisterQualificationRepository.findById2(id);
        TeacherRegisterQualification teacherRegisterQualification = teacherRegisterQualificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterQualification.not_found");
        });

        return GetTeacherRegisterQualificationResponse.builder()
                .id(teacherRegisterQualification.getId())
                .teacher_id(teacherRegisterQualification.getTeacher().getId())
                .time_approved(teacherRegisterQualification.getTime_approved())
                .course_id(teacherRegisterQualification.getCourse().getId())
                .degree_photo_url(teacherRegisterQualification.getDegree_photo_url())
                .status(teacherRegisterQualification.getStatus())
                .build();
    }

    @Override
    public Long createTeacherRegisterQualification(CreateTeacherRegisterQualificationRequest createTeacherRegisterQualificationRequest) {

        Optional <Course> courseOpt = courseRepository.findById1(createTeacherRegisterQualificationRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        Optional <Teacher> teacherOpt = teacherRepository.findById1(createTeacherRegisterQualificationRequest.getTeacher_id());
        Teacher teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.teacher.not_found");
        });

        TeacherRegisterQualification savedTeacherRegisterQualification = TeacherRegisterQualification.builder()
                .teacher(teacher)
                .course(course)
                .status(createTeacherRegisterQualificationRequest.getStatus())
                .degree_photo_url(createTeacherRegisterQualificationRequest.getDegree_photo_url())
                .build();
        teacherRegisterQualificationRepository.save(savedTeacherRegisterQualification);

        return savedTeacherRegisterQualification.getId();
    }

    @Override
    public Long removeTeacherRegisterQualificationById(Long id) {
        Optional<TeacherRegisterQualification> teacherRegisterQualificationOpt = teacherRegisterQualificationRepository.findById1(id);
        teacherRegisterQualificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterQualification.not_found");
        });

        teacherRegisterQualificationRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateTeacherRegisterQualificationById(Long id, CreateTeacherRegisterQualificationRequest createTeacherRegisterQualificationRequest) {
        Optional<TeacherRegisterQualification> teacherRegisterQualificationOpt = teacherRegisterQualificationRepository.findById1(id);
        TeacherRegisterQualification updatedTeacherRegisterQualification = teacherRegisterQualificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterQualification.not_found");
        });

        Optional <Course> courseOpt = courseRepository.findById1(createTeacherRegisterQualificationRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        Optional <Teacher> teacherOpt = teacherRepository.findById1(createTeacherRegisterQualificationRequest.getTeacher_id());
        Teacher teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.teacher.not_found");
        });
        
        updatedTeacherRegisterQualification.setCourse(course);
        updatedTeacherRegisterQualification.setTeacher(teacher);
        updatedTeacherRegisterQualification.setStatus(createTeacherRegisterQualificationRequest.getStatus());
        updatedTeacherRegisterQualification.setDegree_photo_url(createTeacherRegisterQualificationRequest.getDegree_photo_url());
        teacherRegisterQualificationRepository.save(updatedTeacherRegisterQualification);

        return updatedTeacherRegisterQualification.getId();
    }

    @Override
    public Long updateTeacherRegisterQualificationByAdmin(Long id, CreateTeacherRegisterQualificationAdminRequest createTeacherRegisterQualificationAdminRequest) {
        LocalDateTime time_now = LocalDateTime.now();
        Optional<TeacherRegisterQualification> teacherRegisterQualificationOpt = teacherRegisterQualificationRepository.findById1(id);
        TeacherRegisterQualification updatedTeacherRegisterQualification = teacherRegisterQualificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterQualification.not_found");
        });
        updatedTeacherRegisterQualification.setTime_approved(time_now);
        updatedTeacherRegisterQualification.setStatus(createTeacherRegisterQualificationAdminRequest.getStatus());
        
        teacherRegisterQualificationRepository.save(updatedTeacherRegisterQualification);
        return updatedTeacherRegisterQualification.getId();
    }
}
