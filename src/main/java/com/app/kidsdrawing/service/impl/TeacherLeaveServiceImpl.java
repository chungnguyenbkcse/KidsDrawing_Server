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

import com.app.kidsdrawing.dto.CreateReviewTeacherLeaveRequest;
import com.app.kidsdrawing.dto.CreateTeacherLeaveRequest;
import com.app.kidsdrawing.dto.GetTeacherLeaveResponse;
import com.app.kidsdrawing.entity.Class;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.TeacherLeave;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.repository.TeacherLeaveRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.TeacherLeaveService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TeacherLeaveServiceImpl implements TeacherLeaveService{
    
    private final TeacherLeaveRepository teacherLeaveRepository;
    private final SectionRepository sectionRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacherLeave() {
        List<GetTeacherLeaveResponse> allTeacherLeaveResponses = new ArrayList<>();
        List<TeacherLeave> listTeacherLeave = teacherLeaveRepository.findAll();
        listTeacherLeave.forEach(content -> {
            GetTeacherLeaveResponse TeacherLeaveResponse = GetTeacherLeaveResponse.builder()
                .id(content.getId())
                .teacher_id(content.getTeacher().getId())
                .teacher_name(content.getTeacher().getFirstName() + " " + content.getTeacher().getLastName())
                .reviewer_id(content.getReviewer().getId())
                .section_id(content.getSection().getId())
                .section_name(content.getSection().getName())
                .class_id(content.getClass1().getId())
                .class_name(content.getClass1().getName())
                .substitute_teacher_id(content.getSubstitute_teacher().getId())
                .substitute_teacher_name(content.getSubstitute_teacher().getFirstName() + " " + content.getSubstitute_teacher().getLastName())
                .status(content.getStatus())
                .description(content.getDescription())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allTeacherLeaveResponses.add(TeacherLeaveResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_leave", allTeacherLeaveResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getTeacherLeaveByClassId(Long id) {
        List<GetTeacherLeaveResponse> allTeacherLeaveResponses = new ArrayList<>();
        List<TeacherLeave> listTeacherLeave = teacherLeaveRepository.findAll();
        listTeacherLeave.forEach(content -> {
            if (content.getClass1().getId() == id) {
                GetTeacherLeaveResponse TeacherLeaveResponse = GetTeacherLeaveResponse.builder()
                    .id(content.getId())
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getFirstName() + " " + content.getTeacher().getLastName())
                    .reviewer_id(content.getReviewer().getId())
                    .section_id(content.getSection().getId())
                    .section_name(content.getSection().getName())
                    .class_id(content.getClass1().getId())
                    .class_name(content.getClass1().getName())
                    .substitute_teacher_id(content.getSubstitute_teacher().getId())
                    .substitute_teacher_name(content.getSubstitute_teacher().getFirstName() + " " + content.getSubstitute_teacher().getLastName())
                    .status(content.getStatus())
                    .description(content.getDescription())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allTeacherLeaveResponses.add(TeacherLeaveResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_leave", allTeacherLeaveResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetTeacherLeaveResponse getTeacherLeaveById(Long id) {
        Optional<TeacherLeave> teacherLeaveOpt = teacherLeaveRepository.findById(id);
        TeacherLeave teacherLeave = teacherLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherLeave.not_found");
        });

        return GetTeacherLeaveResponse.builder()
            .id(teacherLeave.getId())
            .teacher_id(teacherLeave.getTeacher().getId())
            .teacher_name(teacherLeave.getTeacher().getFirstName() + " " + teacherLeave.getTeacher().getLastName())
            .reviewer_id(teacherLeave.getReviewer().getId())
            .section_id(teacherLeave.getSection().getId())
            .section_name(teacherLeave.getSection().getName())
            .class_id(teacherLeave.getClass1().getId())
            .class_name(teacherLeave.getClass1().getName())
            .substitute_teacher_id(teacherLeave.getSubstitute_teacher().getId())
            .substitute_teacher_name(teacherLeave.getSubstitute_teacher().getFirstName()  + " " + teacherLeave.getSubstitute_teacher().getLastName())
            .status(teacherLeave.getStatus())
            .description(teacherLeave.getDescription())
            .create_time(teacherLeave.getCreate_time())
            .update_time(teacherLeave.getUpdate_time())
            .build();
    }

    @Override
    public Long createTeacherLeave(CreateTeacherLeaveRequest createTeacherLeaveRequest) {

        Optional <User> teacherOpt = userRepository.findById(createTeacherLeaveRequest.getTeacher_id());
        User teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });

        Optional <User> substitute_teacherOpt = userRepository.findById(createTeacherLeaveRequest.getTeacher_id());
        User substitute_teacher = substitute_teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_substitute_teacher.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById(createTeacherLeaveRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <Class> classOpt = classRepository.findById(createTeacherLeaveRequest.getClass_id());
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.class.not_found");
        });
        
        TeacherLeave savedTeacherLeave = TeacherLeave.builder()
                .class1(classes)
                .section(section)
                .teacher(teacher)
                .substitute_teacher(substitute_teacher)
                .description(createTeacherLeaveRequest.getDescription())
                .build();
        teacherLeaveRepository.save(savedTeacherLeave);

        return savedTeacherLeave.getId();
    }

    @Override
    public Long removeTeacherLeaveById(Long id) {
        Optional<TeacherLeave> teacherLeaveOpt = teacherLeaveRepository.findById(id);
        teacherLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherLeave.not_found");
        });

        teacherLeaveRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateTeacherLeaveById(Long id, CreateTeacherLeaveRequest createTeacherLeaveRequest) {
        Optional<TeacherLeave> TeacherLeaveOpt = teacherLeaveRepository.findById(id);
        TeacherLeave updatedTeacherLeave = TeacherLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherLeave.not_found");
        });

        Optional <User> teacherOpt = userRepository.findById(createTeacherLeaveRequest.getTeacher_id());
        User teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });

        Optional <User> substitute_teacherOpt = userRepository.findById(createTeacherLeaveRequest.getTeacher_id());
        User substitute_teacher = substitute_teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_substitute_teacher.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById(createTeacherLeaveRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <Class> classOpt = classRepository.findById(createTeacherLeaveRequest.getClass_id());
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.class.not_found");
        });

        updatedTeacherLeave.setClass1(classes);
        updatedTeacherLeave.setSection(section);
        updatedTeacherLeave.setTeacher(teacher);
        updatedTeacherLeave.setSubstitute_teacher(substitute_teacher);
        updatedTeacherLeave.setDescription(createTeacherLeaveRequest.getDescription());

        return updatedTeacherLeave.getId();
    }

    @Override
    public Long updateStatusTeacherLeaveById(Long id, CreateReviewTeacherLeaveRequest createReviewTeacherLeaveRequest) {
        Optional<TeacherLeave> TeacherLeaveOpt = teacherLeaveRepository.findById(id);
        TeacherLeave updatedTeacherLeave = TeacherLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherLeave.not_found");
        });

        Optional <User> reviewerOpt = userRepository.findById(createReviewTeacherLeaveRequest.getReviewer_id());
        User reviewer = reviewerOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_reviewer.not_found");
        });

        updatedTeacherLeave.setStatus(createReviewTeacherLeaveRequest.getStatus());
        updatedTeacherLeave.setReviewer(reviewer);

        return updatedTeacherLeave.getId();
    }
}
