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
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.Student;
import com.app.kidsdrawing.entity.StudentLeave;
import com.app.kidsdrawing.exception.ArtAgeNotDeleteException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.repository.StudentLeaveRepository;
import com.app.kidsdrawing.repository.StudentRepository;
import com.app.kidsdrawing.service.StudentLeaveService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class StudentLeaveServiceImpl implements StudentLeaveService{
    
    private final StudentLeaveRepository studentLeaveRepository;
    private final SectionRepository sectionRepository;
    private final StudentRepository studentRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllStudentLeave() {
        List<GetStudentLeaveResponse> allStudentLeaveResponses = new ArrayList<>();
        List<StudentLeave> listStudentLeave = studentLeaveRepository.findAll();
        listStudentLeave.forEach(content -> {
            GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                .id(content.getId())
                .student_id(content.getStudent().getId())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                .section_id(content.getSection().getId())
                .section_number(content.getSection().getNumber())
                .section_name(content.getSection().getName())
                .classes_id(content.getSection().getClasses().getId())
                .class_name(content.getSection().getClasses().getName())
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
    public ResponseEntity<Map<String, Object>> getAllStudentLeaveByStudent(Long id) {
        List<GetStudentLeaveResponse> allStudentLeaveApprovedResponses = new ArrayList<>();
        List<GetStudentLeaveResponse> allStudentLeaveNotApprovedResponses = new ArrayList<>();
        List<GetStudentLeaveResponse> allStudentLeaveNotApprovedNowResponses = new ArrayList<>();
        List<StudentLeave> listStudentLeave = studentLeaveRepository.findByStudentId2(id);
        listStudentLeave.forEach(content -> {
            if (content.getStatus().equals("Approved")) {
                GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                    .id(content.getId())
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getUser().getUsername() + " - " +content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                    .section_id(content.getSection().getId())
                    .section_number(content.getSection().getNumber())
                    .section_name(content.getSection().getName())
                    .classes_id(content.getSection().getClasses().getId())
                    .class_name(content.getSection().getClasses().getName())
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
                    .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                    .section_id(content.getSection().getId())
                    .section_number(content.getSection().getNumber())
                    .section_name(content.getSection().getName())
                    .classes_id(content.getSection().getClasses().getId())
                    .class_name(content.getSection().getClasses().getName())
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
                    .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                    
                    .section_id(content.getSection().getId())
                    .section_number(content.getSection().getNumber())
                    .section_name(content.getSection().getName())
                    .classes_id(content.getSection().getClasses().getId())
                    .class_name(content.getSection().getClasses().getName())
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
    public ResponseEntity<Map<String, Object>> getAllStudentLeaveByParent(Long id) {
        List<GetStudentLeaveResponse> allStudentLeaveApprovedResponses = new ArrayList<>();
        List<GetStudentLeaveResponse> allStudentLeaveNotApprovedResponses = new ArrayList<>();
        List<GetStudentLeaveResponse> allStudentLeaveNotApprovedNowResponses = new ArrayList<>();
        List<Student> pageUser = studentRepository.findByParentId1(id);
        pageUser.forEach(student -> {
            List<StudentLeave> listStudentLeave = studentLeaveRepository.findByStudentId2(student.getId());
            listStudentLeave.forEach(content -> {
                if (content.getStatus().equals("Approved")) {
                    GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                        .id(content.getId())
                        .student_id(content.getStudent().getId())
                        .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                        
                        .section_id(content.getSection().getId())
                        .section_number(content.getSection().getNumber())
                        .section_name(content.getSection().getName())
                        .classes_id(content.getSection().getClasses().getId())
                        .class_name(content.getSection().getClasses().getName())
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
                        .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                        
                        .section_id(content.getSection().getId())
                        .section_number(content.getSection().getNumber())
                        .section_name(content.getSection().getName())
                        .classes_id(content.getSection().getClasses().getId())
                        .class_name(content.getSection().getClasses().getName())
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
                        .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                        
                        .section_id(content.getSection().getId())
                        .section_number(content.getSection().getNumber())
                        .section_name(content.getSection().getName())
                        .classes_id(content.getSection().getClasses().getId())
                        .class_name(content.getSection().getClasses().getName())
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
    public ResponseEntity<Map<String, Object>> getAllStudentLeaveByClass(Long id) {
        List<GetStudentLeaveResponse> allStudentLeaveResponses = new ArrayList<>();
        List<StudentLeave> listStudentLeave = studentLeaveRepository.findByClassesId2(id);
        listStudentLeave.forEach(content -> {
                GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                    .id(content.getId())
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                    
                    .section_id(content.getSection().getId())
                    .section_number(content.getSection().getNumber())
                    .section_name(content.getSection().getName())
                    .classes_id(content.getSection().getClasses().getId())
                    .class_name(content.getSection().getClasses().getName())
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
    public ResponseEntity<Map<String, Object>> getAllStudentLeaveByTeacher(Long id) {
        List<GetStudentLeaveResponse> allStudentLeaveResponses = new ArrayList<>();
        List<StudentLeave> listStudentLeave = studentLeaveRepository.findAllStudentLeaveByTeacher(id);
        listStudentLeave.forEach(content -> {
            GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                .id(content.getId())
                .student_id(content.getStudent().getId())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                
                .section_id(content.getSection().getId())
                .section_number(content.getSection().getNumber())
                .section_name(content.getSection().getName())
                .classes_id(content.getSection().getClasses().getId())
                .class_name(content.getSection().getClasses().getName())
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
    public ResponseEntity<Map<String, Object>> getAllStudentLeaveByClassAndParent(Long classes_id, Long parent_id) {
        List<GetStudentLeaveResponse> allStudentLeaveResponses = new ArrayList<>();
        List<Student> pageUser = studentRepository.findByParentId(parent_id);
        pageUser.forEach(student -> {
            List<StudentLeave> listStudentLeave = studentLeaveRepository.findByClassesAndStudent(classes_id,
                    student.getId());
            listStudentLeave.forEach(content -> {
                GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                        .id(content.getId())
                        .student_id(content.getStudent().getId())
                        .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                        
                        .section_id(content.getSection().getId())
                        .section_number(content.getSection().getNumber())
                        .section_name(content.getSection().getName())
                        .classes_id(content.getSection().getClasses().getId())
                        .class_name(content.getSection().getClasses().getName())
                        .status(content.getStatus())
                        .description(content.getDescription())
                        .create_time(content.getCreate_time())
                        .update_time(content.getUpdate_time())
                        .build();
                allStudentLeaveResponses.add(StudentLeaveResponse);
            });
        });
        Map<String, Object> response = new HashMap<>();
        response.put("student_leave", allStudentLeaveResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override 
    public ResponseEntity<Map<String, Object>> getAllStudentLeaveByClassAndStudent(Long classes_id, Long student_id) {
        List<GetStudentLeaveResponse> allStudentLeaveResponses = new ArrayList<>();
        List<StudentLeave> listStudentLeave = studentLeaveRepository.findByClassesAndStudent(classes_id, student_id);
        listStudentLeave.forEach(content -> {
            GetStudentLeaveResponse StudentLeaveResponse = GetStudentLeaveResponse.builder()
                .id(content.getId())
                .student_id(content.getStudent().getId())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                
                .section_id(content.getSection().getId())
                .section_number(content.getSection().getNumber())
                .section_name(content.getSection().getName())
                .classes_id(content.getSection().getClasses().getId())
                .class_name(content.getSection().getClasses().getName())
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
        Optional<StudentLeave> StudentLeaveOpt = studentLeaveRepository.findById2(id);
        StudentLeave StudentLeave = StudentLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.StudentLeave.not_found");
        });

        return GetStudentLeaveResponse.builder()
            .id(StudentLeave.getId())
            .student_id(StudentLeave.getStudent().getId())
            .student_name(StudentLeave.getStudent().getUser().getUsername() + " - " + StudentLeave.getStudent().getUser().getFirstName() + " " + StudentLeave.getStudent().getUser().getLastName())
            
            .section_id(StudentLeave.getSection().getId())
            .section_number(StudentLeave.getSection().getNumber())
            .section_name(StudentLeave.getSection().getName())
            .classes_id(StudentLeave.getSection().getClasses().getId())
            .class_name(StudentLeave.getSection().getClasses().getName())
            .status(StudentLeave.getStatus())
            .description(StudentLeave.getDescription())
            .create_time(StudentLeave.getCreate_time())
            .update_time(StudentLeave.getUpdate_time())
            .build();
    }

    @Override
    public GetStudentLeaveResponse getStudentLeaveBySectionAndStudent(Long section_id, Long student_id) {
        List<StudentLeave> StudentLeaveOpt = studentLeaveRepository.findBySectionAndStudent(section_id, student_id);
        if (StudentLeaveOpt.size() == 0) {
            throw new EntityNotFoundException("exception.StudentLeave.not_found");
        }

        return GetStudentLeaveResponse.builder()
            .id(StudentLeaveOpt.get(0).getId())
            .student_id(StudentLeaveOpt.get(0).getStudent().getId())
            .student_name(StudentLeaveOpt.get(0).getStudent().getUser().getUsername() + " - " + StudentLeaveOpt.get(0).getStudent().getUser().getFirstName() + " " + StudentLeaveOpt.get(0).getStudent().getUser().getLastName())
            .section_id(StudentLeaveOpt.get(0).getSection().getId())
            .section_number(StudentLeaveOpt.get(0).getSection().getNumber())
            .section_name(StudentLeaveOpt.get(0).getSection().getName())
            .classes_id(StudentLeaveOpt.get(0).getSection().getClasses().getId())
            .class_name(StudentLeaveOpt.get(0).getSection().getClasses().getName())
            .status(StudentLeaveOpt.get(0).getStatus())
            .description(StudentLeaveOpt.get(0).getDescription())
            .create_time(StudentLeaveOpt.get(0).getCreate_time())
            .update_time(StudentLeaveOpt.get(0).getUpdate_time())
            .build();
    }

    @Override
    public Long createStudentLeave(CreateStudentLeaveRequest createStudentLeaveRequest) {

        Optional <Student> studentOpt = studentRepository.findById1(createStudentLeaveRequest.getStudent_id());
        Student student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById1(createStudentLeaveRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });


        
        StudentLeave savedStudentLeave = StudentLeave.builder()
                .section(section)
                .student(student)
                .description(createStudentLeaveRequest.getDescription())
                .status("Not approve now")
                .build();
        studentLeaveRepository.save(savedStudentLeave);

        return savedStudentLeave.getId();
    }

    @Override
    public Long removeStudentLeaveById(Long id) {
        Optional<StudentLeave> studentLeaveOpt = studentLeaveRepository.findById1(id);
        StudentLeave studentLeave = studentLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.StudentLeave.not_found");
        });

        if (studentLeave.getStatus().equals("Approved") || studentLeave.getStatus().equals("Not approved")) {
            throw new ArtAgeNotDeleteException("exception.StudentLeave.not_delete");
        }

        studentLeaveRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateStudentLeaveById(Long id, CreateStudentLeaveRequest createStudentLeaveRequest) {
        Optional<StudentLeave> StudentLeaveOpt = studentLeaveRepository.findById1(id);
        StudentLeave updatedStudentLeave = StudentLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.StudentLeave.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById1(createStudentLeaveRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });
        updatedStudentLeave.setSection(section);
        updatedStudentLeave.setDescription(createStudentLeaveRequest.getDescription());

        return updatedStudentLeave.getId();
    }

    @Override
    public Long updateStatusStudentLeaveById(Long id, CreateReviewStudentLeaveRequest createReviewStudentLeaveRequest) {
        Optional<StudentLeave> studentLeaveOpt = studentLeaveRepository.findById1(id);
        StudentLeave updatedStudentLeave = studentLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.StudentLeave.not_found");
        });

        updatedStudentLeave.setStatus(createReviewStudentLeaveRequest.getStatus());

        return updatedStudentLeave.getId();
    }
}
