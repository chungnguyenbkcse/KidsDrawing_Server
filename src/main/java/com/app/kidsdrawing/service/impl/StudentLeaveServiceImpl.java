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

import com.app.kidsdrawing.dto.CreateReviewStudentLeaveRequest;
import com.app.kidsdrawing.dto.CreateStudentLeaveRequest;
import com.app.kidsdrawing.dto.GetStudentLeaveResponse;
import com.app.kidsdrawing.entity.Class;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.StudentLeave;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.repository.StudentLeaveRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.StudentLeaveService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class StudentLeaveServiceImpl implements StudentLeaveService{
    
    private final StudentLeaveRepository studentLeaveRepository;
    private final SectionRepository sectionRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllStudentLeave() {
        List<GetStudentLeaveResponse> allStudentLeaveResponses = new ArrayList<>();
        List<StudentLeave> listStudentLeave = studentLeaveRepository.findAll();
        listStudentLeave.forEach(content -> {
            GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                .id(content.getId())
                .student_id(content.getStudent().getId())
                .reviewer_id(content.getReviewer().getId())
                .section_id(content.getSection().getId())
                .class_id(content.getClass1().getId())
                .status(content.getStatus())
                .description(content.getDescription())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allStudentLeaveResponses.add(StudentLeaveResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("student_leave", allStudentLeaveResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetStudentLeaveResponse getStudentLeaveById(Long id) {
        Optional<StudentLeave> StudentLeaveOpt = studentLeaveRepository.findById(id);
        StudentLeave StudentLeave = StudentLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.StudentLeave.not_found");
        });

        return GetStudentLeaveResponse.builder()
            .id(StudentLeave.getId())
            .student_id(StudentLeave.getStudent().getId())
            .reviewer_id(StudentLeave.getReviewer().getId())
            .section_id(StudentLeave.getSection().getId())
            .class_id(StudentLeave.getClass1().getId())
            .status(StudentLeave.getStatus())
            .description(StudentLeave.getDescription())
            .create_time(StudentLeave.getCreate_time())
            .update_time(StudentLeave.getUpdate_time())
            .build();
    }

    @Override
    public Long createStudentLeave(CreateStudentLeaveRequest createStudentLeaveRequest) {

        Optional <User> studentOpt = userRepository.findById(createStudentLeaveRequest.getStudent_id());
        User student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById(createStudentLeaveRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <Class> classOpt = classRepository.findById(createStudentLeaveRequest.getClass_id());
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.class.not_found");
        });
        
        StudentLeave savedStudentLeave = StudentLeave.builder()
                .class1(classes)
                .section(section)
                .student(student)
                .description(createStudentLeaveRequest.getDescription())
                .build();
        studentLeaveRepository.save(savedStudentLeave);

        return savedStudentLeave.getId();
    }

    @Override
    public Long removeStudentLeaveById(Long id) {
        Optional<StudentLeave> studentLeaveOpt = studentLeaveRepository.findById(id);
        studentLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.StudentLeave.not_found");
        });

        studentLeaveRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateStudentLeaveById(Long id, CreateStudentLeaveRequest createStudentLeaveRequest) {
        Optional<StudentLeave> StudentLeaveOpt = studentLeaveRepository.findById(id);
        StudentLeave updatedStudentLeave = StudentLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.StudentLeave.not_found");
        });

        Optional <User> studentOpt = userRepository.findById(createStudentLeaveRequest.getStudent_id());
        User student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById(createStudentLeaveRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <Class> classOpt = classRepository.findById(createStudentLeaveRequest.getClass_id());
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.class.not_found");
        });

        updatedStudentLeave.setClass1(classes);
        updatedStudentLeave.setSection(section);
        updatedStudentLeave.setStudent(student);
        updatedStudentLeave.setDescription(createStudentLeaveRequest.getDescription());

        return updatedStudentLeave.getId();
    }

    @Override
    public Long updateStatusStudentLeaveById(Long id, CreateReviewStudentLeaveRequest createReviewStudentLeaveRequest) {
        Optional<StudentLeave> studentLeaveOpt = studentLeaveRepository.findById(id);
        StudentLeave updatedStudentLeave = studentLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.StudentLeave.not_found");
        });

        Optional <User> reviewerOpt = userRepository.findById(createReviewStudentLeaveRequest.getReviewer_id());
        User reviewer = reviewerOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_reviewer.not_found");
        });

        updatedStudentLeave.setStatus(createReviewStudentLeaveRequest.getStatus());
        updatedStudentLeave.setReviewer(reviewer);

        return updatedStudentLeave.getId();
    }
}
