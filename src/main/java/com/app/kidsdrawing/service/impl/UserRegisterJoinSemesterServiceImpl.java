package com.app.kidsdrawing.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateMomoRequest;
import com.app.kidsdrawing.dto.CreateUserRegisterJoinSemesterRequest;
import com.app.kidsdrawing.dto.GetUserRegisterJoinSemesterResponse;
import com.app.kidsdrawing.dto.GetUserRegisterJoinSemesterScheduleClassResponse;
import com.app.kidsdrawing.entity.LessonTime;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.entity.Student;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.SemesterClassRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinSemesterRepository;
import com.app.kidsdrawing.repository.StudentRepository;
import com.app.kidsdrawing.service.UserRegisterJoinSemesterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserRegisterJoinSemesterServiceImpl implements UserRegisterJoinSemesterService{
    
    private final UserRegisterJoinSemesterRepository userRegisterJoinSemesterRepository;
    private final SemesterClassRepository semesterClassRepository;
    private final StudentRepository studentRepository;
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
        List<UserRegisterJoinSemester> listUserRegisterJoinSemester = userRegisterJoinSemesterRepository.findAll3();
        listUserRegisterJoinSemester.forEach(content -> {
            GetUserRegisterJoinSemesterResponse userRegisterJoinSemesterResponse = GetUserRegisterJoinSemesterResponse.builder()
                .id(content.getId())
                .student_id(content.getStudent().getId())
                .register_by_type(content.getRegister_by_type())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                .semester_classes_name(content.getSemesterClass().getName())
                .link_url(content.getSemesterClass().getCourse().getImage_url())
                .semester_classes_id(content.getSemesterClass().getId())
                .payer_id(content.getStudent().getParent().getId())
                .payer_name(content.getStudent().getParent().getUser().getUsername())
                .course_name(content.getSemesterClass().getCourse().getName())
                .price(content.getPrice())
                .time(content.getTime())
                .status(content.getStatus())
                .build();
            allUserRegisterJoinSemesterResponses.add(userRegisterJoinSemesterResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_semester", allUserRegisterJoinSemesterResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllMoneyUserRegisterJoinSemester() {
        Float listUserRegisterJoinSemester = userRegisterJoinSemesterRepository.findAll2();
        Map<String, Object> response = new HashMap<>();
        response.put("total_money", listUserRegisterJoinSemester);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override 
    public ResponseEntity<Map<String, Object>> getAllUserRegisterJoinSemesterBySemesterClass(Long id) {
        List<GetUserRegisterJoinSemesterResponse> allUserRegisterJoinSemesterResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> listUserRegisterJoinSemester = userRegisterJoinSemesterRepository.findBySemesterClassId2(id);
        listUserRegisterJoinSemester.forEach(content -> {
            GetUserRegisterJoinSemesterResponse userRegisterJoinSemesterResponse = GetUserRegisterJoinSemesterResponse.builder()
                .id(content.getId())
                .register_by_type(content.getRegister_by_type())
                .student_id(content.getStudent().getId())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                .semester_classes_name(content.getSemesterClass().getName())
                .link_url(content.getSemesterClass().getCourse().getImage_url())
                .semester_classes_id(content.getSemesterClass().getId())
                .course_name(content.getSemesterClass().getCourse().getName())
                .payer_id(content.getStudent().getParent().getId())
                .price(content.getPrice())
                .time(content.getTime())
                .status(content.getStatus())
                .build();
            allUserRegisterJoinSemesterResponses.add(userRegisterJoinSemesterResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_semester", allUserRegisterJoinSemesterResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserRegisterJoinSemesterBySemesterClassScheduleClass(Long id) {
        List<GetUserRegisterJoinSemesterScheduleClassResponse> allUserRegisterJoinSemesterResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> listUserRegisterJoinSemester = userRegisterJoinSemesterRepository.findBySemesterClassId3(id);
        listUserRegisterJoinSemester.forEach(content -> {
            GetUserRegisterJoinSemesterScheduleClassResponse userRegisterJoinSemesterResponse = GetUserRegisterJoinSemesterScheduleClassResponse.builder()
                .id(content.getId())
                .build();
            allUserRegisterJoinSemesterResponses.add(userRegisterJoinSemesterResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_semester", allUserRegisterJoinSemesterResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getReportUserRegisterJoinSemesterByPayerId(Long id) {
        List<GetUserRegisterJoinSemesterResponse> allUserRegisterJoinSemesterResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> listUserRegisterJoinSemester = userRegisterJoinSemesterRepository.findByPayerId3(id);
        listUserRegisterJoinSemester.forEach(content -> {
            GetUserRegisterJoinSemesterResponse userRegisterJoinSemesterResponse = GetUserRegisterJoinSemesterResponse.builder()
                .id(content.getId())
                .register_by_type(content.getRegister_by_type())
                .student_id(content.getStudent().getId())
                .semester_classes_id(content.getSemesterClass().getId())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                .semester_classes_name(content.getSemesterClass().getName())
                .payer_name(content.getStudent().getParent().getUser().getUsername() + " " + content.getStudent().getParent().getUser().getLastName())
                .link_url(content.getSemesterClass().getCourse().getImage_url())
                .payer_id(content.getStudent().getParent().getId())
                .semester_name(content.getSemesterClass().getSemester().getName())
                .price(content.getPrice())
                .course_name(content.getSemesterClass().getCourse().getName())
                .time(content.getTime())
                .status(content.getStatus())
                .build();
            allUserRegisterJoinSemesterResponses.add(userRegisterJoinSemesterResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_semester", allUserRegisterJoinSemesterResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserRegisterJoinSemesterByPayerId(Long id) {
        List<GetUserRegisterJoinSemesterResponse> allUserRegisterJoinSemesterResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> listUserRegisterJoinSemester = userRegisterJoinSemesterRepository.findByPayerId2(id);
        listUserRegisterJoinSemester.forEach(content -> {
            GetUserRegisterJoinSemesterResponse userRegisterJoinSemesterResponse = GetUserRegisterJoinSemesterResponse.builder()
                .id(content.getId())
                .register_by_type(content.getRegister_by_type())
                .student_id(content.getStudent().getId())
                .semester_classes_id(content.getSemesterClass().getId())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                .semester_classes_name(content.getSemesterClass().getName())
                .payer_name(content.getStudent().getParent().getUser().getUsername() + " " + content.getStudent().getParent().getUser().getLastName())
                .link_url(content.getSemesterClass().getCourse().getImage_url())
                .payer_id(content.getStudent().getParent().getId())
                .price(content.getPrice())
                .course_name(content.getSemesterClass().getCourse().getName())
                .time(content.getTime())
                .status(content.getStatus())
                .build();
            allUserRegisterJoinSemesterResponses.add(userRegisterJoinSemesterResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_semester", allUserRegisterJoinSemesterResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getReportUserRegisterJoinSemesterByStudentId(Long id) {
        List<GetUserRegisterJoinSemesterResponse> allUserRegisterJoinSemesterResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> listUserRegisterJoinSemester = userRegisterJoinSemesterRepository.findByStudentId3(id);
        listUserRegisterJoinSemester.forEach(content -> {
            GetUserRegisterJoinSemesterResponse userRegisterJoinSemesterResponse = GetUserRegisterJoinSemesterResponse.builder()
                .id(content.getId())
                .register_by_type(content.getRegister_by_type())
                .student_id(content.getStudent().getId())
                .semester_classes_id(content.getSemesterClass().getId())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                .semester_classes_name(content.getSemesterClass().getName())
                .link_url(content.getSemesterClass().getCourse().getImage_url())
                .course_name(content.getSemesterClass().getCourse().getName())
                .payer_id(content.getStudent().getParent().getId())
                .semester_name(content.getSemesterClass().getSemester().getName())
                .course_name(content.getSemesterClass().getCourse().getName())
                .payer_name(content.getStudent().getParent().getUser().getUsername() + " " + content.getStudent().getParent().getUser().getLastName())
                .price(content.getPrice())
                .time(content.getTime())
                .status(content.getStatus())
                .build();
            allUserRegisterJoinSemesterResponses.add(userRegisterJoinSemesterResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_semester", allUserRegisterJoinSemesterResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserRegisterJoinSemesterByStudentId(Long id) {
        List<GetUserRegisterJoinSemesterResponse> allUserRegisterJoinSemesterResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> listUserRegisterJoinSemester = userRegisterJoinSemesterRepository.findByStudentId2(id);
        listUserRegisterJoinSemester.forEach(content -> {
            GetUserRegisterJoinSemesterResponse userRegisterJoinSemesterResponse = GetUserRegisterJoinSemesterResponse.builder()
                .id(content.getId())
                .register_by_type(content.getRegister_by_type())
                .student_id(content.getStudent().getId())
                .semester_classes_id(content.getSemesterClass().getId())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                .semester_classes_name(content.getSemesterClass().getName())
                .link_url(content.getSemesterClass().getCourse().getImage_url())
                .payer_name(content.getStudent().getParent().getUser().getUsername() + " " + content.getStudent().getParent().getUser().getLastName())
                .payer_id(content.getStudent().getParent().getId())
                .course_name(content.getSemesterClass().getCourse().getName())
                .price(content.getPrice())
                .time(content.getTime())
                .status(content.getStatus())
                .build();
            allUserRegisterJoinSemesterResponses.add(userRegisterJoinSemesterResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_semester", allUserRegisterJoinSemesterResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetUserRegisterJoinSemesterResponse getUserRegisterJoinSemesterById(Long id) {
        Optional<UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findById2(id);
        UserRegisterJoinSemester userRegisterJoinSemester = userRegisterJoinSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinSemester.not_found");
        });

        return GetUserRegisterJoinSemesterResponse.builder()
                .id(userRegisterJoinSemester.getId())
                .register_by_type(userRegisterJoinSemester.getRegister_by_type())
                .student_id(userRegisterJoinSemester.getStudent().getId())
                .semester_classes_id(userRegisterJoinSemester.getSemesterClass().getId())
                .payer_id(userRegisterJoinSemester.getStudent().getParent().getId())
                .student_name(userRegisterJoinSemester.getStudent().getUser().getUsername() + " - " + userRegisterJoinSemester.getStudent().getUser().getFirstName() + " " + userRegisterJoinSemester.getStudent().getUser().getLastName())
                .semester_classes_name(userRegisterJoinSemester.getSemesterClass().getName())
                .link_url(userRegisterJoinSemester.getSemesterClass().getCourse().getImage_url())
                .price(userRegisterJoinSemester.getPrice())
                .time(userRegisterJoinSemester.getTime())
                .status(userRegisterJoinSemester.getStatus())
                .build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> getReportUserRegisterJoinSemester(int year) {
        List<Integer> allUserRegisterJoinSemesterResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> listUserRegisterJoinSemester = userRegisterJoinSemesterRepository.findAll1();
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
                if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("JANUARY")){
                    total_user_of_jan += content.getPrice();
                }
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("FEBRUARY")){
                    total_user_of_feb += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("MARCH")){
                    total_user_of_mar += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("APRIL")){
                    total_user_of_apr += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("MAY")){
                    total_user_of_may += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("JUNE")){
                    total_user_of_jun += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("JULY")){
                    total_user_of_jul += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("AUGUST")){
                    total_user_of_aug += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("SEPTEMBER")){
                    total_user_of_sep += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("OCTOBER")){
                    total_user_of_oct += content.getPrice();
                }
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("NOVEMBER")){
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
                if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("JANUARY")){
                    total_user_of_jan += content.getPrice();
                }
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("FEBRUARY")){
                    total_user_of_feb += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("MARCH")){
                    total_user_of_mar += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("APRIL")){
                    total_user_of_apr += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("MAY")){
                    total_user_of_may += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("JUNE")){
                    total_user_of_jun += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("JULY")){
                    total_user_of_jul += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("AUGUST")){
                    total_user_of_aug += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("SEPTEMBER")){
                    total_user_of_sep += content.getPrice();
                } 
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("OCTOBER")){
                    total_user_of_oct += content.getPrice();
                }
                else if (content.getSemesterClass().getSemester().getStart_time().getMonth().toString().equals("NOVEMBER")){
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
    public Boolean checkScheduleForStudent(Long student_id, Long semester_class_id) {
        Optional <SemesterClass> semester_classOpt = semesterClassRepository.findById4(semester_class_id);
        SemesterClass semesterCouse = semester_classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_class.not_found");
        });

        List<Map<Integer, LessonTime>> lessonTimes = new ArrayList<>();
        semesterCouse.getSchedules().forEach(ele -> {
            Map<Integer, LessonTime> item = new HashMap<>();
            item.put(ele.getDate_of_week(), ele.getLessonTime());
            lessonTimes.add(item);
        });

        List<Map<Integer, LessonTime>> lessonTimesAll = new ArrayList<>();
        List<UserRegisterJoinSemester> userRegisterTeachSemesters = userRegisterJoinSemesterRepository.findBySemester(semesterCouse.getSemester().getId(), student_id);
        userRegisterTeachSemesters.forEach(ele -> {
            if (ele.getSemesterClass().getSemester().getId() == semesterCouse.getSemester().getId()) {
                ele.getSemesterClass().getSchedules().forEach(schedule -> {
                    Map<Integer, LessonTime> item = new HashMap<>();
                    item.put(schedule.getDate_of_week(), schedule.getLessonTime());
                    lessonTimesAll.add(item);
                });
            }
        });

        if (!Collections.disjoint(lessonTimes, lessonTimesAll)) {
            return false;
        }
        return true;


    }

    @Override
    public Long createUserRegisterJoinSemester(CreateUserRegisterJoinSemesterRequest createUserRegisterJoinSemesterRequest) {
        if (!checkScheduleForStudent(createUserRegisterJoinSemesterRequest.getStudent_id(), createUserRegisterJoinSemesterRequest.getSemester_classes_id())) {
            throw new EntityNotFoundException("exception.schedule.duplicate");
        }

        Optional <SemesterClass> semester_classOpt = semesterClassRepository.findById1(createUserRegisterJoinSemesterRequest.getSemester_classes_id());
        SemesterClass semesterCouse = semester_classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_class.not_found");
        });

        LocalDateTime time_now = LocalDateTime.now();

        if (time_now.isAfter((semesterCouse.getRegistration_expiration_time())) && time_now.isBefore(semesterCouse.getRegistration_time())) {
            throw new EntityNotFoundException("exception.deadline.not_found");
        }

        List<UserRegisterJoinSemester> listUserRegisterJoinSemesters = userRegisterJoinSemesterRepository.findBySemesterClassId1(createUserRegisterJoinSemesterRequest.getSemester_classes_id());

        if (semesterCouse.getMax_participant() <= listUserRegisterJoinSemesters.size()) {
            throw new EntityNotFoundException("exception.max_participant.not_register");
        }

        Optional <Student> studentOpt = studentRepository.findById1(createUserRegisterJoinSemesterRequest.getStudent_id());
        Student student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });
        
        UserRegisterJoinSemester savedUserRegisterJoinSemester = UserRegisterJoinSemester.builder()
                .semesterClass(semesterCouse)
                .student(student)
                .register_by_type(createUserRegisterJoinSemesterRequest.getRegister_by_type())
                .status(createUserRegisterJoinSemesterRequest.getStatus())
                .price(createUserRegisterJoinSemesterRequest.getPrice())
                .build();
        userRegisterJoinSemesterRepository.save(savedUserRegisterJoinSemester);

        return savedUserRegisterJoinSemester.getId();
    }

    @Override
    public Long removeUserRegisterJoinSemesterBySemesterClassAndStudent(Long semester_class_id, Long student_id) {
        Optional <SemesterClass> semester_classOpt = semesterClassRepository.findById1(semester_class_id);
        SemesterClass semesterCouse = semester_classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_class.not_found");
        });

        LocalDateTime time_now = LocalDateTime.now();

        if (time_now.isAfter((semesterCouse.getRegistration_expiration_time())) && time_now.isBefore(semesterCouse.getRegistration_time())) {
            throw new EntityNotFoundException("exception.deadline.not_found");
        }

        Optional<UserRegisterJoinSemester> allUserRegisterJoinSemester = userRegisterJoinSemesterRepository.findBySemesterClassIdAndStudent(semester_class_id, student_id);
        if (allUserRegisterJoinSemester.isPresent()) {
            userRegisterJoinSemesterRepository.deleteById(allUserRegisterJoinSemester.get().getId());
        }
        return semester_class_id;
    }

    @Override
    public Long removeUserRegisterJoinSemesterById(Long id) {
        Optional<UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findById1(id);
        userRegisterJoinSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinSemester.not_found");
        });

        userRegisterJoinSemesterRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateUserRegisterJoinSemesterById(Long id, CreateUserRegisterJoinSemesterRequest createUserRegisterJoinSemesterRequest) {
        Optional<UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findById1(id);
        UserRegisterJoinSemester updatedUserRegisterJoinSemester = userRegisterJoinSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterJoinSemester.not_found");
        });

        Optional <SemesterClass> semester_classOpt = semesterClassRepository.findById1(createUserRegisterJoinSemesterRequest.getSemester_classes_id());
        SemesterClass semesterCouse = semester_classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_class.not_found");
        });

        Optional <Student> studentOpt = studentRepository.findById1(createUserRegisterJoinSemesterRequest.getStudent_id());
        Student student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        updatedUserRegisterJoinSemester.setSemesterClass(semesterCouse);
        updatedUserRegisterJoinSemester.setStudent(student);
        updatedUserRegisterJoinSemester.setRegister_by_type(createUserRegisterJoinSemesterRequest.getRegister_by_type());
        updatedUserRegisterJoinSemester.setStatus(createUserRegisterJoinSemesterRequest.getStatus());
        updatedUserRegisterJoinSemester.setPrice(createUserRegisterJoinSemesterRequest.getPrice());

        return updatedUserRegisterJoinSemester.getId();
    }

    @Override
    public Long updateStatusUserRegisterJoinSemester(List<Long> ids, CreateMomoRequest createMomoRequest) {
        LocalDateTime time_now = LocalDateTime.now();
        ids.forEach(ele -> {
            Optional<UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findById1(ele);
            UserRegisterJoinSemester updatedUserRegisterJoinSemester = userRegisterJoinSemesterOpt.orElseThrow(() -> {
                throw new EntityNotFoundException("exception.UserRegisterJoinSemester.not_found");
            });
            if (createMomoRequest.getResultCode() == 0){
                updatedUserRegisterJoinSemester.setStatus("Completed");
                updatedUserRegisterJoinSemester.setTime(time_now);
                updatedUserRegisterJoinSemester.setOrderId(createMomoRequest.getOrderId());
            }
        });

        return (long)1;
    }

    @Override
    public Long updateStatusUserRegisterJoinSemester(List<Long> ids) {
        ids.forEach(ele -> {
            Optional<UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findById1(ele);
            UserRegisterJoinSemester updatedUserRegisterJoinSemester = userRegisterJoinSemesterOpt.orElseThrow(() -> {
                throw new EntityNotFoundException("exception.UserRegisterJoinSemester.not_found");
            });
            updatedUserRegisterJoinSemester.setStatus("Completed");
            userRegisterJoinSemesterRepository.save(updatedUserRegisterJoinSemester);
        });

        return (long)1;
    }
}