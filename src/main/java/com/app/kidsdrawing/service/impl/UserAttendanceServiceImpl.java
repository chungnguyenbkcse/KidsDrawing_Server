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

import com.app.kidsdrawing.dto.CreateUserAttendanceRequest;
import com.app.kidsdrawing.dto.GetUserAttendanceResponse;
import com.app.kidsdrawing.entity.UserAttendance;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.UserAttendanceRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.service.UserAttendanceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAttendanceServiceImpl implements UserAttendanceService{
    
    private final UserAttendanceRepository userAttendanceRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserAttendance() {
        List<GetUserAttendanceResponse> allUserAttendanceResponses = new ArrayList<>();
        List<UserAttendance> listUserAttendance = userAttendanceRepository.findAll();
        listUserAttendance.forEach(content -> {
            GetUserAttendanceResponse userAttendanceResponse = GetUserAttendanceResponse.builder()
                .id(content.getId())
                .section_id(content.getSection().getId())
                .section_number(content.getSection().getNumber())
                .course_id(content.getSection().getClasses().getUserRegisterTeachSemester().getSemesterClass().getCourse().getId())
                .course_name(content.getSection().getClasses().getUserRegisterTeachSemester().getSemesterClass().getCourse().getName())
                .email(content.getStudent().getEmail())
                .student_id(content.getStudent().getId())
                .section_name(content.getSection().getName())
                .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                .create_time(content.getCreateTime())
                .update_time(content.getUpdateTime())
                .build();
            allUserAttendanceResponses.add(userAttendanceResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserAttendance", allUserAttendanceResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserAttendanceBySection(UUID id) {
        List<GetUserAttendanceResponse> allUserAttendanceResponses = new ArrayList<>();
        List<UserAttendance> listUserAttendance = userAttendanceRepository.findBySectionId(id);
        listUserAttendance.forEach(content -> {
            GetUserAttendanceResponse userAttendanceResponse = GetUserAttendanceResponse.builder()
                .id(content.getId())
                .section_number(content.getSection().getNumber())
                .course_id(content.getSection().getClasses().getUserRegisterTeachSemester().getSemesterClass().getCourse().getId())
                .course_name(content.getSection().getClasses().getUserRegisterTeachSemester().getSemesterClass().getCourse().getName())
                .email(content.getStudent().getEmail())
                .section_id(content.getSection().getId())
                .student_id(content.getStudent().getId())
                .section_name(content.getSection().getName())
                .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                .create_time(content.getCreateTime())
                .update_time(content.getUpdateTime())
                .build();
            allUserAttendanceResponses.add(userAttendanceResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserAttendance", allUserAttendanceResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserAttendanceByStudent(UUID id) {
        List<GetUserAttendanceResponse> allUserAttendanceResponses = new ArrayList<>();
        List<UserAttendance> listUserAttendance = userAttendanceRepository.findByStudentId(id);
        listUserAttendance.forEach(content -> {
            GetUserAttendanceResponse userAttendanceResponse = GetUserAttendanceResponse.builder()
                .id(content.getId())
                .section_number(content.getSection().getNumber())
                .course_id(content.getSection().getClasses().getUserRegisterTeachSemester().getSemesterClass().getCourse().getId())
                .course_name(content.getSection().getClasses().getUserRegisterTeachSemester().getSemesterClass().getCourse().getName())
                .email(content.getStudent().getEmail())
                .section_id(content.getSection().getId())
                .student_id(content.getStudent().getId())
                .section_name(content.getSection().getName())
                .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                .create_time(content.getCreateTime())
                .update_time(content.getUpdateTime())
                .build();
            allUserAttendanceResponses.add(userAttendanceResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserAttendance", allUserAttendanceResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Map<String, Object>> getAllUserAttendanceByClassAndStudent(UUID classes_id, UUID student_id) {
        List<GetUserAttendanceResponse> allUserAttendanceResponses = new ArrayList<>();
        List<UserAttendance> listUserAttendance = userAttendanceRepository.findByStudentId(student_id);
        listUserAttendance.forEach(content -> {
            if (content.getSection().getClasses().getId().compareTo(classes_id) == 0) {
                GetUserAttendanceResponse userAttendanceResponse = GetUserAttendanceResponse.builder()
                    .id(content.getId())
                    .section_number(content.getSection().getNumber())
                    .course_id(content.getSection().getClasses().getUserRegisterTeachSemester().getSemesterClass().getCourse().getId())
                    .course_name(content.getSection().getClasses().getUserRegisterTeachSemester().getSemesterClass().getCourse().getName())
                    .email(content.getStudent().getEmail())
                    .section_id(content.getSection().getId())
                    .student_id(content.getStudent().getId())
                    .section_name(content.getSection().getName())
                    .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .create_time(content.getCreateTime())
                    .update_time(content.getUpdateTime())
                    .build();
                allUserAttendanceResponses.add(userAttendanceResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserAttendance", allUserAttendanceResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserAttendanceByClass(UUID classes_id) {
        List<GetUserAttendanceResponse> allUserAttendanceResponses = new ArrayList<>();
        List<UserAttendance> listUserAttendance = userAttendanceRepository.findAll();
        listUserAttendance.forEach(content -> {
            if (content.getSection().getClasses().getId().compareTo(classes_id) == 0) {
                GetUserAttendanceResponse userAttendanceResponse = GetUserAttendanceResponse.builder()
                    .id(content.getId())
                    .section_number(content.getSection().getNumber())
                    .course_id(content.getSection().getClasses().getUserRegisterTeachSemester().getSemesterClass().getCourse().getId())
                    .course_name(content.getSection().getClasses().getUserRegisterTeachSemester().getSemesterClass().getCourse().getName())
                    .email(content.getStudent().getEmail())
                    .section_id(content.getSection().getId())
                    .student_id(content.getStudent().getId())
                    .section_name(content.getSection().getName())
                    .student_name(content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .create_time(content.getCreateTime())
                    .update_time(content.getUpdateTime())
                    .build();
                allUserAttendanceResponses.add(userAttendanceResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserAttendance", allUserAttendanceResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public GetUserAttendanceResponse getAllUserAttendanceBySectionAndStudent(UUID section_id, UUID student_id) {
        List<UserAttendance> userAttendance = userAttendanceRepository.findBySectionIdAndStudentId(section_id, student_id);
        return GetUserAttendanceResponse.builder()
            .id(userAttendance.get(0).getId())
            .section_number(userAttendance.get(0).getSection().getNumber())
            .course_id(userAttendance.get(0).getSection().getClasses().getUserRegisterTeachSemester().getSemesterClass().getCourse().getId())
            .course_name(userAttendance.get(0).getSection().getClasses().getUserRegisterTeachSemester().getSemesterClass().getCourse().getName())
            .email(userAttendance.get(0).getStudent().getEmail())
            .section_id(userAttendance.get(0).getSection().getId())
            .student_id(userAttendance.get(0).getStudent().getId())
            .section_name(userAttendance.get(0).getSection().getName())
            .student_name(userAttendance.get(0).getStudent().getFirstName() + " " + userAttendance.get(0).getStudent().getLastName())
            .create_time(userAttendance.get(0).getCreateTime())
            .update_time(userAttendance.get(0).getUpdateTime())
            .build();
    }



    @Override
    public GetUserAttendanceResponse getUserAttendanceById(UUID id) {
        Optional<UserAttendance> userAttendanceOpt = userAttendanceRepository.findById(id);
        UserAttendance userAttendance = userAttendanceOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserAttendance.not_found");
        });

        return GetUserAttendanceResponse.builder()
            .id(userAttendance.getId())
            .section_number(userAttendance.getSection().getNumber())
            .course_id(userAttendance.getSection().getClasses().getUserRegisterTeachSemester().getSemesterClass().getCourse().getId())
            .course_name(userAttendance.getSection().getClasses().getUserRegisterTeachSemester().getSemesterClass().getCourse().getName())
            .email(userAttendance.getStudent().getEmail())
            .section_id(userAttendance.getSection().getId())
            .student_id(userAttendance.getStudent().getId())
            .section_name(userAttendance.getSection().getName())
            .student_name(userAttendance.getStudent().getFirstName() + " " + userAttendance.getStudent().getLastName())
            .create_time(userAttendance.getCreateTime())
            .update_time(userAttendance.getUpdateTime())
            .build();
    }

    @Override
    public UUID createUserAttendance(CreateUserAttendanceRequest createUserAttendanceRequest) {

        Optional <Section> sectionOpt = sectionRepository.findById1(createUserAttendanceRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <User> userOpt = userRepository.findById1(createUserAttendanceRequest.getStudent_id());
        User student = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_creator.not_found");
        });
        
        UserAttendance savedUserAttendance = UserAttendance.builder()
                .section(section)
                .student(student)
                .status(createUserAttendanceRequest.getStatus())
                .build();
        userAttendanceRepository.save(savedUserAttendance);

        return savedUserAttendance.getId();
    }

    
    @Override
    public UUID removeUserAttendanceById(UUID id) {
        Optional<UserAttendance> userAttendanceOpt = userAttendanceRepository.findById(id);
        userAttendanceOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserAttendance.not_found");
        });

        userAttendanceRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateUserAttendanceById(UUID id, CreateUserAttendanceRequest createUserAttendanceRequest) {
        Optional<UserAttendance> userAttendanceOpt = userAttendanceRepository.findById(id);
        UserAttendance updatedUserAttendance = userAttendanceOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserAttendance.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById1(createUserAttendanceRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <User> userOpt = userRepository.findById1(createUserAttendanceRequest.getStudent_id());
        User student = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.student.not_found");
        });

        updatedUserAttendance.setStatus(createUserAttendanceRequest.getStatus());
        updatedUserAttendance.setSection(section);
        updatedUserAttendance.setStudent(student);

        return updatedUserAttendance.getId();
    }
}
