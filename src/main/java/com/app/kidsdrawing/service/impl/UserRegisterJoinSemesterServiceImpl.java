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

import com.app.kidsdrawing.dto.CreateUserRegisterJoinSemesterRequest;
import com.app.kidsdrawing.dto.GetUserRegisterJoinSemesterResponse;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.SemesterClassRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinSemesterRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.UserRegisterJoinSemesterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserRegisterJoinSemesterServiceImpl implements UserRegisterJoinSemesterService{
    
    private final UserRegisterJoinSemesterRepository userRegisterJoinSemesterRepository;
    private final SemesterClassRepository semesterClassRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserRegisterJoinSemester() {
        List<GetUserRegisterJoinSemesterResponse> allUserRegisterJoinSemesterResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> listUserRegisterJoinSemester = userRegisterJoinSemesterRepository.findAll();
        listUserRegisterJoinSemester.forEach(content -> {
            GetUserRegisterJoinSemesterResponse userRegisterJoinSemesterResponse = GetUserRegisterJoinSemesterResponse.builder()
                .id(content.getId())
                .student_id(content.getStudent().getId())
                .semester_course_id(content.getSemesterClass().getId())
                .payer_id(content.getPayer().getId())
                .price(content.getPrice())
                .time(content.getTime())
                .build();
            allUserRegisterJoinSemesterResponses.add(userRegisterJoinSemesterResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_semester", allUserRegisterJoinSemesterResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetUserRegisterJoinSemesterResponse getUserRegisterJoinSemesterById(Long id) {
        Optional<UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findById(id);
        UserRegisterJoinSemester userRegisterJoinSemester = userRegisterJoinSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinSemester.not_found");
        });

        return GetUserRegisterJoinSemesterResponse.builder()
                .id(userRegisterJoinSemester.getId())
                .student_id(userRegisterJoinSemester.getStudent().getId())
                .semester_course_id(userRegisterJoinSemester.getSemesterClass().getId())
                .payer_id(userRegisterJoinSemester.getPayer().getId())
                .price(userRegisterJoinSemester.getPrice())
                .time(userRegisterJoinSemester.getTime())
                .build();
    }

    @Override
    public Long createUserRegisterJoinSemester(CreateUserRegisterJoinSemesterRequest createUserRegisterJoinSemesterRequest) {
        Optional <SemesterClass> semester_courseOpt = semesterClassRepository.findById(createUserRegisterJoinSemesterRequest.getSemester_course_id());
        SemesterClass semesterCouse = semester_courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_course.not_found");
        });

        Optional <User> studentOpt = userRepository.findById(createUserRegisterJoinSemesterRequest.getStudent_id());
        User student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <User> payer_idOpt = userRepository.findById(createUserRegisterJoinSemesterRequest.getPayer_id());
        User payer = payer_idOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_payer.not_found");
        });
        
        UserRegisterJoinSemester savedUserRegisterJoinSemester = UserRegisterJoinSemester.builder()
                .semesterClass(semesterCouse)
                .student(student)
                .payer(payer)
                .price(createUserRegisterJoinSemesterRequest.getPrice())
                .build();
        userRegisterJoinSemesterRepository.save(savedUserRegisterJoinSemester);

        return savedUserRegisterJoinSemester.getId();
    }

    @Override
    public Long removeUserRegisterJoinSemesterById(Long id) {
        Optional<UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findById(id);
        userRegisterJoinSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinSemester.not_found");
        });

        userRegisterJoinSemesterRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateUserRegisterJoinSemesterById(Long id, CreateUserRegisterJoinSemesterRequest createUserRegisterJoinSemesterRequest) {
        Optional<UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findById(id);
        UserRegisterJoinSemester updatedUserRegisterJoinSemester = userRegisterJoinSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinSemester.not_found");
        });

        Optional <SemesterClass> semester_courseOpt = semesterClassRepository.findById(createUserRegisterJoinSemesterRequest.getSemester_course_id());
        SemesterClass semesterCouse = semester_courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_course.not_found");
        });

        Optional <User> studentOpt = userRepository.findById(createUserRegisterJoinSemesterRequest.getStudent_id());
        User student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <User> payer_idOpt = userRepository.findById(createUserRegisterJoinSemesterRequest.getPayer_id());
        User payer = payer_idOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_payer.not_found");
        });

        updatedUserRegisterJoinSemester.setSemesterClass(semesterCouse);
        updatedUserRegisterJoinSemester.setStudent(student);
        updatedUserRegisterJoinSemester.setPayer(payer);
        updatedUserRegisterJoinSemester.setPrice(createUserRegisterJoinSemesterRequest.getPrice());

        return updatedUserRegisterJoinSemester.getId();
    }
}
