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

import com.app.kidsdrawing.dto.CreateUserAttendanceRequest;
import com.app.kidsdrawing.dto.GetCheckUserAttendanceResponse;
import com.app.kidsdrawing.dto.GetUserAttendanceResponse;
import com.app.kidsdrawing.entity.UserAttendance;
import com.app.kidsdrawing.entity.UserAttendanceKey;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.Student;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.UserAttendanceRepository;
import com.app.kidsdrawing.repository.StudentRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.service.UserAttendanceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAttendanceServiceImpl implements UserAttendanceService{
    
    private final UserAttendanceRepository userAttendanceRepository;
    private final SectionRepository sectionRepository;
    private final StudentRepository studentRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserAttendance() {
        List<GetUserAttendanceResponse> allUserAttendanceResponses = new ArrayList<>();
        List<UserAttendance> listUserAttendance = userAttendanceRepository.findAll();
        listUserAttendance.forEach(content -> {
            GetUserAttendanceResponse userAttendanceResponse = GetUserAttendanceResponse.builder()
                .section_id(content.getSection().getId())
                .section_number(content.getSection().getNumber())
                .course_id(content.getSection().getClasses().getSemesterClass().getCourse().getId())
                .course_name(content.getSection().getClasses().getSemesterClass().getCourse().getName())
                .email(content.getStudent().getUser().getEmail())
                .student_id(content.getStudent().getId())
                .section_name(content.getSection().getName())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                .create_time(content.getCreateTime())
                .update_time(content.getUpdateTime())
                .status(content.getStatus())
                .build();
            allUserAttendanceResponses.add(userAttendanceResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserAttendance", allUserAttendanceResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserAttendanceBySection(Long id) {
        List<GetUserAttendanceResponse> allUserAttendanceResponses = new ArrayList<>();
        List<UserAttendance> listUserAttendance = userAttendanceRepository.findBySectionId2(id);
        listUserAttendance.forEach(content -> {
            GetUserAttendanceResponse userAttendanceResponse = GetUserAttendanceResponse.builder()
                .section_number(content.getSection().getNumber())
                .email(content.getStudent().getUser().getEmail())
                .section_id(content.getSection().getId())
                .student_id(content.getStudent().getId())
                .section_name(content.getSection().getName())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                .create_time(content.getCreateTime())
                .update_time(content.getUpdateTime())
                .status(content.getStatus())
                .build();
            allUserAttendanceResponses.add(userAttendanceResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserAttendance", allUserAttendanceResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetCheckUserAttendanceResponse checkUserAttendanceBySectionAndStudent(Long section_id, Long student_id) {
        Optional<UserAttendance> userAttendanceOpt = userAttendanceRepository.findBySectionIdAndStudentId(section_id, student_id);
        UserAttendance userAttendance = userAttendanceOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserAttendance.not_found");
        });

        return GetCheckUserAttendanceResponse.builder()
                .status(userAttendance.getStatus())
                .build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserAttendanceByStudent(Long id) {
        List<GetUserAttendanceResponse> allUserAttendanceResponses = new ArrayList<>();
        List<UserAttendance> listUserAttendance = userAttendanceRepository.findByStudentId2(id);
        listUserAttendance.forEach(content -> {
            GetUserAttendanceResponse userAttendanceResponse = GetUserAttendanceResponse.builder()
                .section_number(content.getSection().getNumber())
                .email(content.getStudent().getUser().getEmail())
                .section_id(content.getSection().getId())
                .student_id(content.getStudent().getId())
                .section_name(content.getSection().getName())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                .create_time(content.getCreateTime())
                .update_time(content.getUpdateTime())
                .status(content.getStatus())
                .build();
            allUserAttendanceResponses.add(userAttendanceResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserAttendance", allUserAttendanceResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Map<String, Object>> getAllUserAttendanceByClassAndStudent(Long classes_id, Long student_id) {
        List<GetUserAttendanceResponse> allUserAttendanceResponses = new ArrayList<>();
        List<UserAttendance> listUserAttendance = userAttendanceRepository.findByClassIdAndStudentId(classes_id, student_id);
        listUserAttendance.forEach(content -> {
            GetUserAttendanceResponse userAttendanceResponse = GetUserAttendanceResponse.builder()
                .section_number(content.getSection().getNumber())
                .email(content.getStudent().getUser().getEmail())
                .section_id(content.getSection().getId())
                .student_id(content.getStudent().getId())
                .section_name(content.getSection().getName())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                .create_time(content.getCreateTime())
                .update_time(content.getUpdateTime())
                .status(content.getStatus())
                .build();
            allUserAttendanceResponses.add(userAttendanceResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserAttendance", allUserAttendanceResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserAttendanceByClass(Long classes_id) {
        List<GetUserAttendanceResponse> allUserAttendanceResponses = new ArrayList<>();
        List<UserAttendance> listUserAttendance = userAttendanceRepository.findByClassId(classes_id);
        listUserAttendance.forEach(content -> {
            GetUserAttendanceResponse userAttendanceResponse = GetUserAttendanceResponse.builder()
                .section_number(content.getSection().getNumber())
                .email(content.getStudent().getUser().getEmail())
                .section_id(content.getSection().getId())
                .student_id(content.getStudent().getId())
                .section_name(content.getSection().getName())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                .create_time(content.getCreateTime())
                .update_time(content.getUpdateTime())
                .status(content.getStatus())
                .build();
            allUserAttendanceResponses.add(userAttendanceResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserAttendance", allUserAttendanceResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public GetUserAttendanceResponse getAllUserAttendanceBySectionAndStudent(Long section_id, Long student_id) {
        Optional<UserAttendance> userAttendanceOpt = userAttendanceRepository.findBySectionIdAndStudentId(section_id, student_id);
        UserAttendance userAttendance = userAttendanceOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserAttendance.not_found");
        });
        if (userAttendance.getStatus() == false ) {
            throw new EntityNotFoundException("exception.UserAttendance.not_found");
        }
        return GetUserAttendanceResponse.builder()
            .section_number(userAttendance.getSection().getNumber())
            .email(userAttendance.getStudent().getUser().getEmail())
            .section_id(userAttendance.getSection().getId())
            .student_id(userAttendance.getStudent().getId())
            .section_name(userAttendance.getSection().getName())
            .student_name(userAttendance.getStudent().getUser().getUsername() + " - " + userAttendance.getStudent().getUser().getFirstName() + " " + userAttendance.getStudent().getUser().getLastName())
            .create_time(userAttendance.getCreateTime())
            .update_time(userAttendance.getUpdateTime())
            .status(userAttendance.getStatus())
            .build();
    }

    @Override
    public Long createUserAttendance(CreateUserAttendanceRequest createUserAttendanceRequest) {

        Optional <Section> sectionOpt = sectionRepository.findById1(createUserAttendanceRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <Student> userOpt = studentRepository.findById1(createUserAttendanceRequest.getStudent_id());
        Student student = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_creator.not_found");
        });

        UserAttendanceKey id = new UserAttendanceKey(student.getId(),section.getId());
        
        UserAttendance savedUserAttendance = UserAttendance.builder()
                .id(id)
                .section(section)
                .student(student)
                .status(createUserAttendanceRequest.getStatus())
                .build();
        userAttendanceRepository.save(savedUserAttendance);

        return savedUserAttendance.getStudent().getId();
    }


    @Override
    public Long updateUserAttendanceById(CreateUserAttendanceRequest createUserAttendanceRequest) {
        Optional<UserAttendance> userAttendanceOpt = userAttendanceRepository.findBySectionIdAndStudentId(createUserAttendanceRequest.getSection_id(), createUserAttendanceRequest.getStudent_id());
        UserAttendance updatedUserAttendance = userAttendanceOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserAttendance.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById1(createUserAttendanceRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <Student> userOpt = studentRepository.findById1(createUserAttendanceRequest.getStudent_id());
        Student student = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_creator.not_found");
        });

        updatedUserAttendance.setStatus(createUserAttendanceRequest.getStatus());
        updatedUserAttendance.setSection(section);
        updatedUserAttendance.setStudent(student);

        return updatedUserAttendance.getStudent().getId();
    }

    @Override
    public Long updateUserAttendanceBySectionAndStudent(Long section_id, Long student_id) {
        Optional<UserAttendance> userAttendanceOpt = userAttendanceRepository.findBySectionIdAndStudentId(section_id, student_id);
        UserAttendance updatedUserAttendance = userAttendanceOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserAttendance.not_found");
        });

        updatedUserAttendance.setStatus(true);

        return section_id;
    }
}
