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
    private static int total_user_of_jan = 0;
    private static int total_user_of_feb = 0;
    private static int total_user_of_mar = 0;
    private static int total_user_of_apr = 0;
    private static int total_user_of_may = 0;
    private static int total_user_of_jun = 0;
    private static int total_user_of_jul = 0;
    private static int total_user_of_aug = 0;
    private static int total_user_of_sep = 0;
    private static int total_user_of_oct = 0;
    private static int total_user_of_nov = 0;
    private static int total_user_of_dec = 0;

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
    public ResponseEntity<Map<String, Object>> getReportUserRegisterJoinSemester(int year) {
        List<Integer> allUserRegisterJoinSemesterResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> listUserRegisterJoinSemester = userRegisterJoinSemesterRepository.findAll();
        total_user_of_jan = 0;
        total_user_of_feb = 0;
        total_user_of_mar = 0;
        total_user_of_apr = 0;
        total_user_of_may = 0;
        total_user_of_jun = 0;
        total_user_of_jul = 0;
        total_user_of_aug = 0;
        total_user_of_sep = 0;
        total_user_of_oct = 0;
        total_user_of_nov = 0;
        total_user_of_dec = 0;
        listUserRegisterJoinSemester.forEach(content -> {
            if (content.getSemesterClass().getSemester().getStart_time().getYear() == year){
                if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "JANUARY"){
                    total_user_of_jan += content.getPrice();
                }
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "FEBRUARY"){
                    total_user_of_feb += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "MARCH"){
                    total_user_of_mar += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "APRIL"){
                    total_user_of_apr += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "MAY"){
                    total_user_of_may += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "JUNE"){
                    total_user_of_jun += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "JULY"){
                    total_user_of_jul += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "AUGUST"){
                    total_user_of_aug += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "SEPTEMBER"){
                    total_user_of_sep += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "OCTOBER"){
                    total_user_of_oct += content.getPrice();
                }
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "NOVEMBER"){
                    total_user_of_nov += content.getPrice();
                }
                else {
                    total_user_of_dec += content.getPrice();
                }
            }
        });

        allUserRegisterJoinSemesterResponses.add(total_user_of_jan);
        allUserRegisterJoinSemesterResponses.add(total_user_of_feb);
        allUserRegisterJoinSemesterResponses.add(total_user_of_mar);
        allUserRegisterJoinSemesterResponses.add(total_user_of_apr);
        allUserRegisterJoinSemesterResponses.add(total_user_of_may);
        allUserRegisterJoinSemesterResponses.add(total_user_of_jun);
        allUserRegisterJoinSemesterResponses.add(total_user_of_jul);
        allUserRegisterJoinSemesterResponses.add(total_user_of_aug);
        allUserRegisterJoinSemesterResponses.add(total_user_of_sep);
        allUserRegisterJoinSemesterResponses.add(total_user_of_oct);
        allUserRegisterJoinSemesterResponses.add(total_user_of_nov);
        allUserRegisterJoinSemesterResponses.add(total_user_of_dec);

        List<Integer> allReportUserRegisterJoinSemesterLastYearResponses = new ArrayList<>();
        total_user_of_jan = 0;
        total_user_of_feb = 0;
        total_user_of_mar = 0;
        total_user_of_apr = 0;
        total_user_of_may = 0;
        total_user_of_jun = 0;
        total_user_of_jul = 0;
        total_user_of_aug = 0;
        total_user_of_sep = 0;
        total_user_of_oct = 0;
        total_user_of_nov = 0;
        total_user_of_dec = 0;
        listUserRegisterJoinSemester.forEach(content -> {
            if (content.getSemesterClass().getSemester().getStart_time().getYear() == year-1){
                if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "JANUARY"){
                    total_user_of_jan += content.getPrice();
                }
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "FEBRUARY"){
                    total_user_of_feb += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "MARCH"){
                    total_user_of_mar += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "APRIL"){
                    total_user_of_apr += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "MAY"){
                    total_user_of_may += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "JUNE"){
                    total_user_of_jun += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "JULY"){
                    total_user_of_jul += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "AUGUST"){
                    total_user_of_aug += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "SEPTEMBER"){
                    total_user_of_sep += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "OCTOBER"){
                    total_user_of_oct += content.getPrice();
                }
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString() == "NOVEMBER"){
                    total_user_of_nov += content.getPrice();
                }
                else {
                    total_user_of_dec += content.getPrice();
                }
            }
        });

        allReportUserRegisterJoinSemesterLastYearResponses.add(total_user_of_jan);
        allReportUserRegisterJoinSemesterLastYearResponses.add(total_user_of_feb);
        allReportUserRegisterJoinSemesterLastYearResponses.add(total_user_of_mar);
        allReportUserRegisterJoinSemesterLastYearResponses.add(total_user_of_apr);
        allReportUserRegisterJoinSemesterLastYearResponses.add(total_user_of_may);
        allReportUserRegisterJoinSemesterLastYearResponses.add(total_user_of_jun);
        allReportUserRegisterJoinSemesterLastYearResponses.add(total_user_of_jul);
        allReportUserRegisterJoinSemesterLastYearResponses.add(total_user_of_aug);
        allReportUserRegisterJoinSemesterLastYearResponses.add(total_user_of_sep);
        allReportUserRegisterJoinSemesterLastYearResponses.add(total_user_of_oct);
        allReportUserRegisterJoinSemesterLastYearResponses.add(total_user_of_nov);
        allReportUserRegisterJoinSemesterLastYearResponses.add(total_user_of_dec);

        Map<String, Object> response = new HashMap<>();
        response.put("report_user_register_semester_now", allUserRegisterJoinSemesterResponses);
        response.put("report_user_register_semester_last", allReportUserRegisterJoinSemesterLastYearResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
