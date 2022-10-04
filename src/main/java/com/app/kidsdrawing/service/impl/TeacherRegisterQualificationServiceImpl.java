package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateTeacherRegisterQualificationAdminRequest;
import com.app.kidsdrawing.dto.CreateTeacherRegisterQualificationRequest;
import com.app.kidsdrawing.dto.GetTeacherRegisterQualificationResponse;
import com.app.kidsdrawing.dto.GetTeacherRegisterQuanlificationTeacherResponse;
import com.app.kidsdrawing.entity.Course;
import com.app.kidsdrawing.entity.TeacherRegisterQualification;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.CourseRepository;
import com.app.kidsdrawing.repository.TeacherRegisterQualificationRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.TeacherRegisterQualificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TeacherRegisterQualificationServiceImpl implements TeacherRegisterQualificationService{
    
    private final TeacherRegisterQualificationRepository teacherRegisterQualificationRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacherRegisterQualificationApprovedByTeacherId(UUID id) {
        List<GetTeacherRegisterQuanlificationTeacherResponse> allTeacherRegisterQualificationApprovedResponses = new ArrayList<>();
        List<GetTeacherRegisterQuanlificationTeacherResponse> allTeacherRegisterQualificationNotApprovedResponses = new ArrayList<>();
        List<GetTeacherRegisterQuanlificationTeacherResponse> allTeacherRegisterQualificationNotApproveNowResponses = new ArrayList<>();
        List<TeacherRegisterQualification> pageTeacherRegisterQualification = teacherRegisterQualificationRepository.findAll();
        pageTeacherRegisterQualification.forEach(ele -> {
            if (ele.getTeacher().getId().compareTo(id) == 0){
                if (ele.getStatus().equals("Approved")){
                    GetTeacherRegisterQuanlificationTeacherResponse teacherRegisterQuanlificationTeacherResponse = GetTeacherRegisterQuanlificationTeacherResponse.builder()
                        .id(ele.getId())
                        .teacher_id(ele.getTeacher().getId())
                        .teacher_name(ele.getTeacher().getUsername())
                        .reviewer_id(ele.getReviewer().getId())
                        .course_id(ele.getCourse().getId())
                        .course_name(ele.getCourse().getName())
                        .art_age_name(ele.getCourse().getArtAges().getName())
                        .art_level_name(ele.getCourse().getArtLevels().getName())
                        .art_type_name(ele.getCourse().getArtTypes().getName())
                        .degree_photo_url(ele.getDegree_photo_url())
                        .status(ele.getStatus())
                        .build();
                    allTeacherRegisterQualificationApprovedResponses.add(teacherRegisterQuanlificationTeacherResponse);
                }
                else if (ele.getStatus().equals("Not approved")){
                    GetTeacherRegisterQuanlificationTeacherResponse teacherRegisterQuanlificationTeacherResponse = GetTeacherRegisterQuanlificationTeacherResponse.builder()
                        .id(ele.getId())
                        .teacher_id(ele.getTeacher().getId())
                        .teacher_name(ele.getTeacher().getUsername())
                        .reviewer_id(ele.getReviewer().getId())
                        .course_id(ele.getCourse().getId())
                        .course_name(ele.getCourse().getName())
                        .art_age_name(ele.getCourse().getArtAges().getName())
                        .art_level_name(ele.getCourse().getArtLevels().getName())
                        .art_type_name(ele.getCourse().getArtTypes().getName())
                        .degree_photo_url(ele.getDegree_photo_url())
                        .status(ele.getStatus())
                        .build();
                    allTeacherRegisterQualificationNotApprovedResponses.add(teacherRegisterQuanlificationTeacherResponse);
                }
                else if (ele.getStatus().equals("Not approved now")){
                    GetTeacherRegisterQuanlificationTeacherResponse teacherRegisterQuanlificationTeacherResponse = GetTeacherRegisterQuanlificationTeacherResponse.builder()
                        .id(ele.getId())
                        .teacher_id(ele.getTeacher().getId())
                        .teacher_name(ele.getTeacher().getUsername())
                        .reviewer_id(ele.getReviewer().getId())
                        .course_id(ele.getCourse().getId())
                        .course_name(ele.getCourse().getName())
                        .art_age_name(ele.getCourse().getArtAges().getName())
                        .art_level_name(ele.getCourse().getArtLevels().getName())
                        .art_type_name(ele.getCourse().getArtTypes().getName())
                        .degree_photo_url(ele.getDegree_photo_url())
                        .status(ele.getStatus())
                        .build();
                    allTeacherRegisterQualificationNotApproveNowResponses.add(teacherRegisterQuanlificationTeacherResponse);
                }
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
                .reviewer_id(content.getReviewer().getId())
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
    public ResponseEntity<Map<String, Object>> getAllTeacherRegisterQualificationByTeacherId(UUID id) {
        List<GetTeacherRegisterQualificationResponse> allTeacherRegisterQualificationResponses = new ArrayList<>();
        List<TeacherRegisterQualification> pageTeacherRegisterQualification = teacherRegisterQualificationRepository.findAll();
        pageTeacherRegisterQualification.forEach(content -> {
            if (content.getTeacher().getId().compareTo(id) == 0){
                GetTeacherRegisterQualificationResponse teacherRegisterQualificationResponse = GetTeacherRegisterQualificationResponse.builder()
                    .id(content.getId())
                    .teacher_id(content.getTeacher().getId())
                    .reviewer_id(content.getReviewer().getId())
                    .course_id(content.getCourse().getId())
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
    public GetTeacherRegisterQualificationResponse getTeacherRegisterQualificationById(UUID id) {
        Optional<TeacherRegisterQualification> teacherRegisterQualificationOpt = teacherRegisterQualificationRepository.findById(id);
        TeacherRegisterQualification teacherRegisterQualification = teacherRegisterQualificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterQualification.not_found");
        });

        return GetTeacherRegisterQualificationResponse.builder()
                .id(teacherRegisterQualification.getId())
                .teacher_id(teacherRegisterQualification.getTeacher().getId())
                .reviewer_id(teacherRegisterQualification.getReviewer().getId())
                .course_id(teacherRegisterQualification.getCourse().getId())
                .degree_photo_url(teacherRegisterQualification.getDegree_photo_url())
                .status(teacherRegisterQualification.getStatus())
                .build();
    }

    @Override
    public UUID createTeacherRegisterQualification(CreateTeacherRegisterQualificationRequest createTeacherRegisterQualificationRequest) {
        Course course = courseRepository.getById(createTeacherRegisterQualificationRequest.getCourse_id());
        User teacher = userRepository.getById(createTeacherRegisterQualificationRequest.getTeacher_id());
        Optional <User> adminOpt = userRepository.findByUsername1("admin");
        User reviewer = adminOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });
        TeacherRegisterQualification savedTeacherRegisterQualification = TeacherRegisterQualification.builder()
                .teacher(teacher)
                .reviewer(reviewer)
                .course(course)
                .status(createTeacherRegisterQualificationRequest.getStatus())
                .degree_photo_url(createTeacherRegisterQualificationRequest.getDegree_photo_url())
                .build();
        teacherRegisterQualificationRepository.save(savedTeacherRegisterQualification);

        return savedTeacherRegisterQualification.getId();
    }

    @Override
    public UUID removeTeacherRegisterQualificationById(UUID id) {
        Optional<TeacherRegisterQualification> teacherRegisterQualificationOpt = teacherRegisterQualificationRepository.findById(id);
        teacherRegisterQualificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterQualification.not_found");
        });

        teacherRegisterQualificationRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateTeacherRegisterQualificationById(UUID id, CreateTeacherRegisterQualificationRequest createTeacherRegisterQualificationRequest) {
        Optional<TeacherRegisterQualification> teacherRegisterQualificationOpt = teacherRegisterQualificationRepository.findById(id);
        TeacherRegisterQualification updatedTeacherRegisterQualification = teacherRegisterQualificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterQualification.not_found");
        });
        Course course = courseRepository.getById(createTeacherRegisterQualificationRequest.getCourse_id());
        User teacher = userRepository.getById(createTeacherRegisterQualificationRequest.getTeacher_id());
        Optional <User> adminOpt = userRepository.findByUsername1("admin");
        User reviewer = adminOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });
        
        updatedTeacherRegisterQualification.setCourse(course);
        updatedTeacherRegisterQualification.setTeacher(teacher);
        updatedTeacherRegisterQualification.setReviewer(reviewer);
        updatedTeacherRegisterQualification.setDegree_photo_url(createTeacherRegisterQualificationRequest.getDegree_photo_url());
        teacherRegisterQualificationRepository.save(updatedTeacherRegisterQualification);

        return updatedTeacherRegisterQualification.getId();
    }

    @Override
    public UUID updateTeacherRegisterQualificationByAdmin(UUID id, CreateTeacherRegisterQualificationAdminRequest createTeacherRegisterQualificationAdminRequest) {
        Optional<TeacherRegisterQualification> teacherRegisterQualificationOpt = teacherRegisterQualificationRepository.findById(id);
        TeacherRegisterQualification updatedTeacherRegisterQualification = teacherRegisterQualificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterQualification.not_found");
        });

        updatedTeacherRegisterQualification.setStatus(createTeacherRegisterQualificationAdminRequest.getStatus());
        teacherRegisterQualificationRepository.save(updatedTeacherRegisterQualification);
        return updatedTeacherRegisterQualification.getId();
    }
}
