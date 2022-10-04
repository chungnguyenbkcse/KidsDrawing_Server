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

import com.app.kidsdrawing.dto.CreateReviewStudentLeaveRequest;
import com.app.kidsdrawing.dto.CreateStudentLeaveRequest;
import com.app.kidsdrawing.dto.GetStudentLeaveResponse;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.StudentLeave;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassesRepository;
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
    private final ClassesRepository classRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllStudentLeave() {
        List<GetStudentLeaveResponse> allStudentLeaveResponses = new ArrayList<>();
        List<StudentLeave> listStudentLeave = studentLeaveRepository.findAll();
        listStudentLeave.forEach(content -> {
            GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                .id(content.getId())
                .student_id(content.getStudent().getId())
                .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                .reviewer_id(content.getReviewer().getId())
                .section_id(content.getSection().getId())
                .section_number(content.getSection().getNumber())
                .section_name(content.getSection().getName())
                .classes_id(content.getClasses().getId())
                .class_name(content.getClasses().getName())
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
    public ResponseEntity<Map<String, Object>> getAllStudentLeaveByStudent(UUID id) {
        List<GetStudentLeaveResponse> allStudentLeaveApprovedResponses = new ArrayList<>();
        List<GetStudentLeaveResponse> allStudentLeaveNotApprovedResponses = new ArrayList<>();
        List<GetStudentLeaveResponse> allStudentLeaveNotApprovedNowResponses = new ArrayList<>();
        List<StudentLeave> listStudentLeave = studentLeaveRepository.findByStudentId(id);
        listStudentLeave.forEach(content -> {
            if (content.getStatus().equals("Approved")) {
                GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                    .id(content.getId())
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .reviewer_id(content.getReviewer().getId())
                    .section_id(content.getSection().getId())
                    .section_number(content.getSection().getNumber())
                    .section_name(content.getSection().getName())
                    .classes_id(content.getClasses().getId())
                    .class_name(content.getClasses().getName())
                    .status(content.getStatus())
                    .description(content.getDescription())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allStudentLeaveApprovedResponses.add(StudentLeaveResponse);
            }
            else if (content.getStatus().equals("Not approved")) {
                GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                    .id(content.getId())
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .reviewer_id(content.getReviewer().getId())
                    .section_id(content.getSection().getId())
                    .section_number(content.getSection().getNumber())
                    .section_name(content.getSection().getName())
                    .classes_id(content.getClasses().getId())
                    .class_name(content.getClasses().getName())
                    .status(content.getStatus())
                    .description(content.getDescription())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allStudentLeaveNotApprovedResponses.add(StudentLeaveResponse);
            }
            else {
                GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                    .id(content.getId())
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .reviewer_id(content.getReviewer().getId())
                    .section_id(content.getSection().getId())
                    .section_number(content.getSection().getNumber())
                    .section_name(content.getSection().getName())
                    .classes_id(content.getClasses().getId())
                    .class_name(content.getClasses().getName())
                    .status(content.getStatus())
                    .description(content.getDescription())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allStudentLeaveNotApprovedNowResponses.add(StudentLeaveResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("student_leave_approved", allStudentLeaveApprovedResponses);
        response.put("student_leave_not_approved", allStudentLeaveNotApprovedResponses);
        response.put("student_leave_not_approved_now", allStudentLeaveNotApprovedNowResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllStudentLeaveByParent(UUID id) {
        List<GetStudentLeaveResponse> allStudentLeaveApprovedResponses = new ArrayList<>();
        List<GetStudentLeaveResponse> allStudentLeaveNotApprovedResponses = new ArrayList<>();
        List<GetStudentLeaveResponse> allStudentLeaveNotApprovedNowResponses = new ArrayList<>();
        List<User> pageUser = userRepository.findByParentId(id);
        pageUser.forEach(student -> {
            List<StudentLeave> listStudentLeave = studentLeaveRepository.findByStudentId(student.getId());
            listStudentLeave.forEach(content -> {
                if (content.getStatus().equals("Approved")) {
                    GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                        .id(content.getId())
                        .student_id(content.getStudent().getId())
                        .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                        .reviewer_id(content.getReviewer().getId())
                        .section_id(content.getSection().getId())
                        .section_number(content.getSection().getNumber())
                        .section_name(content.getSection().getName())
                        .classes_id(content.getClasses().getId())
                        .class_name(content.getClasses().getName())
                        .status(content.getStatus())
                        .description(content.getDescription())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                    allStudentLeaveApprovedResponses.add(StudentLeaveResponse);
                }
                else if (content.getStatus().equals("Not approved")) {
                    GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                        .id(content.getId())
                        .student_id(content.getStudent().getId())
                        .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                        .reviewer_id(content.getReviewer().getId())
                        .section_id(content.getSection().getId())
                        .section_number(content.getSection().getNumber())
                        .section_name(content.getSection().getName())
                        .classes_id(content.getClasses().getId())
                        .class_name(content.getClasses().getName())
                        .status(content.getStatus())
                        .description(content.getDescription())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                    allStudentLeaveNotApprovedResponses.add(StudentLeaveResponse);
                }
                else {
                    GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                        .id(content.getId())
                        .student_id(content.getStudent().getId())
                        .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                        .reviewer_id(content.getReviewer().getId())
                        .section_id(content.getSection().getId())
                        .section_number(content.getSection().getNumber())
                        .section_name(content.getSection().getName())
                        .classes_id(content.getClasses().getId())
                        .class_name(content.getClasses().getName())
                        .status(content.getStatus())
                        .description(content.getDescription())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                    allStudentLeaveNotApprovedNowResponses.add(StudentLeaveResponse);
                }
            });
        });
        Map<String, Object> response = new HashMap<>();
        response.put("student_leave_approved", allStudentLeaveApprovedResponses);
        response.put("student_leave_not_approved", allStudentLeaveNotApprovedResponses);
        response.put("student_leave_not_approved_now", allStudentLeaveNotApprovedNowResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllStudentLeaveByClass(UUID id) {
        List<GetStudentLeaveResponse> allStudentLeaveResponses = new ArrayList<>();
        List<StudentLeave> listStudentLeave = studentLeaveRepository.findAll();
        listStudentLeave.forEach(content -> {
            if (content.getClasses().getId().compareTo(id) == 0) {
                GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                    .id(content.getId())
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .reviewer_id(content.getReviewer().getId())
                    .section_id(content.getSection().getId())
                    .section_number(content.getSection().getNumber())
                    .section_name(content.getSection().getName())
                    .classes_id(content.getClasses().getId())
                    .class_name(content.getClasses().getName())
                    .status(content.getStatus())
                    .description(content.getDescription())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allStudentLeaveResponses.add(StudentLeaveResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("student_leave", allStudentLeaveResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllStudentLeaveByTeacher(UUID id) {
        List<GetStudentLeaveResponse> allStudentLeaveResponses = new ArrayList<>();
        List<StudentLeave> listStudentLeave = studentLeaveRepository.findAll();
        listStudentLeave.forEach(content -> {
            if (content.getClasses().getUserRegisterTeachSemester().getTeacher().getId().compareTo(id) == 0) {
                GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                    .id(content.getId())
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .reviewer_id(content.getReviewer().getId())
                    .section_id(content.getSection().getId())
                    .section_number(content.getSection().getNumber())
                    .section_name(content.getSection().getName())
                    .classes_id(content.getClasses().getId())
                    .class_name(content.getClasses().getName())
                    .status(content.getStatus())
                    .description(content.getDescription())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allStudentLeaveResponses.add(StudentLeaveResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("student_leave", allStudentLeaveResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override 
    public ResponseEntity<Map<String, Object>> getAllStudentLeaveByClassAndStudent(UUID classes_id, UUID student_id) {
        List<GetStudentLeaveResponse> allStudentLeaveResponses = new ArrayList<>();
        List<StudentLeave> listStudentLeave = studentLeaveRepository.findAll();
        listStudentLeave.forEach(content -> {
            if (content.getClasses().getId().compareTo(classes_id) == 0 && content.getStudent().getId().compareTo(student_id) == 0) {
                GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                    .id(content.getId())
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .reviewer_id(content.getReviewer().getId())
                    .section_id(content.getSection().getId())
                    .section_number(content.getSection().getNumber())
                    .section_name(content.getSection().getName())
                    .classes_id(content.getClasses().getId())
                    .class_name(content.getClasses().getName())
                    .status(content.getStatus())
                    .description(content.getDescription())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allStudentLeaveResponses.add(StudentLeaveResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("student_leave", allStudentLeaveResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetStudentLeaveResponse getStudentLeaveById(UUID id) {
        Optional<StudentLeave> StudentLeaveOpt = studentLeaveRepository.findById(id);
        StudentLeave StudentLeave = StudentLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.StudentLeave.not_found");
        });

        return GetStudentLeaveResponse.builder()
            .id(StudentLeave.getId())
            .student_id(StudentLeave.getStudent().getId())
            .student_name(StudentLeave.getStudent().getFirstName() + " " + StudentLeave.getStudent().getLastName())
            .reviewer_id(StudentLeave.getReviewer().getId())
            .section_id(StudentLeave.getSection().getId())
            .section_number(StudentLeave.getSection().getNumber())
            .section_name(StudentLeave.getSection().getName())
            .classes_id(StudentLeave.getClasses().getId())
            .class_name(StudentLeave.getClasses().getName())
            .status(StudentLeave.getStatus())
            .description(StudentLeave.getDescription())
            .create_time(StudentLeave.getCreate_time())
            .update_time(StudentLeave.getUpdate_time())
            .build();
    }

    @Override
    public UUID createStudentLeave(CreateStudentLeaveRequest createStudentLeaveRequest) {

        Optional <User> studentOpt = userRepository.findById1(createStudentLeaveRequest.getStudent_id());
        User student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <User> adminOpt = userRepository.findByUsername1("admin");
        User admin = adminOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById(createStudentLeaveRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <Classes> classOpt = classRepository.findById1(createStudentLeaveRequest.getClasses_id());
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.class.not_found");
        });
        
        StudentLeave savedStudentLeave = StudentLeave.builder()
                .classes(classes)
                .section(section)
                .student(student)
                .reviewer(admin)
                .description(createStudentLeaveRequest.getDescription())
                .status("Not approved now")
                .build();
        studentLeaveRepository.save(savedStudentLeave);

        return savedStudentLeave.getId();
    }

    @Override
    public UUID removeStudentLeaveById(UUID id) {
        Optional<StudentLeave> studentLeaveOpt = studentLeaveRepository.findById(id);
        studentLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.StudentLeave.not_found");
        });

        studentLeaveRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateStudentLeaveById(UUID id, CreateStudentLeaveRequest createStudentLeaveRequest) {
        Optional<StudentLeave> StudentLeaveOpt = studentLeaveRepository.findById(id);
        StudentLeave updatedStudentLeave = StudentLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.StudentLeave.not_found");
        });

        Optional <User> studentOpt = userRepository.findById1(createStudentLeaveRequest.getStudent_id());
        User student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById(createStudentLeaveRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <Classes> classOpt = classRepository.findById1(createStudentLeaveRequest.getClasses_id());
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.class.not_found");
        });

        updatedStudentLeave.setClasses(classes);
        updatedStudentLeave.setSection(section);
        updatedStudentLeave.setStudent(student);
        updatedStudentLeave.setDescription(createStudentLeaveRequest.getDescription());

        return updatedStudentLeave.getId();
    }

    @Override
    public UUID updateStatusStudentLeaveById(UUID id, CreateReviewStudentLeaveRequest createReviewStudentLeaveRequest) {
        Optional<StudentLeave> studentLeaveOpt = studentLeaveRepository.findById(id);
        StudentLeave updatedStudentLeave = studentLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.StudentLeave.not_found");
        });

        updatedStudentLeave.setStatus(createReviewStudentLeaveRequest.getStatus());

        return updatedStudentLeave.getId();
    }
}
