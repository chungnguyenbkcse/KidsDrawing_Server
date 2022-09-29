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
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.TeacherLeave;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassesRepository;
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
    private final ClassesRepository classRepository;
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
                .section_number(content.getSection().getNumber())
                .section_name(content.getSection().getName())
                .classes_id(content.getClasses().getId())
                .class_name(content.getClasses().getName())
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
    public ResponseEntity<Map<String, Object>> getTeacherLeaveByTeacher(Long id) {
        List<GetTeacherLeaveResponse> allTeacherLeaveResponses = new ArrayList<>();
        List<TeacherLeave> listTeacherLeave = teacherLeaveRepository.findAll();
        listTeacherLeave.forEach(content -> {
            if (content.getSubstitute_teacher().getId() == id) {
                GetTeacherLeaveResponse TeacherLeaveResponse = GetTeacherLeaveResponse.builder()
                    .id(content.getId())
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getFirstName() + " " + content.getTeacher().getLastName())
                    .reviewer_id(content.getReviewer().getId())
                    .section_id(content.getSection().getId())
                    .section_number(content.getSection().getNumber())
                    .section_name(content.getSection().getName())
                    .classes_id(content.getClasses().getId())
                    .class_name(content.getClasses().getName())
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
    public ResponseEntity<Map<String, Object>> getTeacherLeaveByClassId(Long id) {
        List<GetTeacherLeaveResponse> allTeacherLeaveResponses = new ArrayList<>();
        List<TeacherLeave> listTeacherLeave = teacherLeaveRepository.findAll();
        listTeacherLeave.forEach(content -> {
            if (content.getClasses().getId() == id) {
                GetTeacherLeaveResponse TeacherLeaveResponse = GetTeacherLeaveResponse.builder()
                    .id(content.getId())
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getFirstName() + " " + content.getTeacher().getLastName())
                    .reviewer_id(content.getReviewer().getId())
                    .section_id(content.getSection().getId())
                    .section_number(content.getSection().getNumber())
                    .section_name(content.getSection().getName())
                    .classes_id(content.getClasses().getId())
                    .class_name(content.getClasses().getName())
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
            .classes_id(teacherLeave.getClasses().getId())
            .section_number(teacherLeave.getSection().getNumber())
            .class_name(teacherLeave.getClasses().getName())
            .substitute_teacher_id(teacherLeave.getSubstitute_teacher().getId())
            .substitute_teacher_name(teacherLeave.getSubstitute_teacher().getFirstName()  + " " + teacherLeave.getSubstitute_teacher().getLastName())
            .status(teacherLeave.getStatus())
            .description(teacherLeave.getDescription())
            .create_time(teacherLeave.getCreate_time())
            .update_time(teacherLeave.getUpdate_time())
            .build();
    }

    @Override
    public GetTeacherLeaveResponse createTeacherLeave(CreateTeacherLeaveRequest createTeacherLeaveRequest) {

        Optional <User> teacherOpt = userRepository.findById(createTeacherLeaveRequest.getTeacher_id());
        User teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });

        Optional <User> reivewerOpt = userRepository.findById(createTeacherLeaveRequest.getTeacher_id());
        User reviewer = reivewerOpt.orElseThrow(() -> {
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

        Optional <Classes> classOpt = classRepository.findById(createTeacherLeaveRequest.getClasses_id());
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.class.not_found");
        });
        
        TeacherLeave savedTeacherLeave = TeacherLeave.builder()
                .id((long) teacherLeaveRepository.findAll().size() + 1)
                .classes(classes)
                .section(section)
                .teacher(teacher)
                .reviewer(reviewer)
                .status("Not approved now")
                .substitute_teacher(substitute_teacher)
                .description(createTeacherLeaveRequest.getDescription())
                .build();
        teacherLeaveRepository.save(savedTeacherLeave);

        return GetTeacherLeaveResponse.builder()
        .id(savedTeacherLeave.getId())
        .teacher_id(savedTeacherLeave.getTeacher().getId())
        .teacher_name(savedTeacherLeave.getTeacher().getFirstName() + " " + savedTeacherLeave.getTeacher().getLastName())
        .reviewer_id(savedTeacherLeave.getReviewer().getId())
        .section_id(savedTeacherLeave.getSection().getId())
        .section_name(savedTeacherLeave.getSection().getName())
        .classes_id(savedTeacherLeave.getClasses().getId())
        .section_number(savedTeacherLeave.getSection().getNumber())
        .class_name(savedTeacherLeave.getClasses().getName())
        .substitute_teacher_id(savedTeacherLeave.getSubstitute_teacher().getId())
        .substitute_teacher_name(savedTeacherLeave.getSubstitute_teacher().getFirstName()  + " " + savedTeacherLeave.getSubstitute_teacher().getLastName())
        .status(savedTeacherLeave.getStatus())
        .description(savedTeacherLeave.getDescription())
        .create_time(savedTeacherLeave.getCreate_time())
        .update_time(savedTeacherLeave.getUpdate_time())
        .build();
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

        Optional <Classes> classOpt = classRepository.findById(createTeacherLeaveRequest.getClasses_id());
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.class.not_found");
        });

        updatedTeacherLeave.setClasses(classes);
        updatedTeacherLeave.setSection(section);
        updatedTeacherLeave.setTeacher(teacher);
        updatedTeacherLeave.setSubstitute_teacher(substitute_teacher);
        updatedTeacherLeave.setDescription(createTeacherLeaveRequest.getDescription());
        teacherLeaveRepository.save(updatedTeacherLeave);

        return updatedTeacherLeave.getId();
    }

    @Override
    public GetTeacherLeaveResponse updateStatusTeacherLeaveById(Long id, CreateReviewTeacherLeaveRequest createReviewTeacherLeaveRequest) {
        Optional<TeacherLeave> TeacherLeaveOpt = teacherLeaveRepository.findById(id);
        TeacherLeave updatedTeacherLeave = TeacherLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherLeave.not_found");
        });

        updatedTeacherLeave.setStatus(createReviewTeacherLeaveRequest.getStatus());
        teacherLeaveRepository.save(updatedTeacherLeave);

        return GetTeacherLeaveResponse.builder()
        .id(updatedTeacherLeave.getId())
        .teacher_id(updatedTeacherLeave.getTeacher().getId())
        .teacher_name(updatedTeacherLeave.getTeacher().getFirstName() + " " + updatedTeacherLeave.getTeacher().getLastName())
        .reviewer_id(updatedTeacherLeave.getReviewer().getId())
        .section_id(updatedTeacherLeave.getSection().getId())
        .section_name(updatedTeacherLeave.getSection().getName())
        .classes_id(updatedTeacherLeave.getClasses().getId())
        .section_number(updatedTeacherLeave.getSection().getNumber())
        .class_name(updatedTeacherLeave.getClasses().getName())
        .substitute_teacher_id(updatedTeacherLeave.getSubstitute_teacher().getId())
        .substitute_teacher_name(updatedTeacherLeave.getSubstitute_teacher().getFirstName()  + " " + updatedTeacherLeave.getSubstitute_teacher().getLastName())
        .status(updatedTeacherLeave.getStatus())
        .description(updatedTeacherLeave.getDescription())
        .create_time(updatedTeacherLeave.getCreate_time())
        .update_time(updatedTeacherLeave.getUpdate_time())
        .build();
    }
}
