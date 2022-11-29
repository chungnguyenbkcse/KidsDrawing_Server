package com.app.kidsdrawing.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateSemesterClassRequest;
import com.app.kidsdrawing.dto.GetCourseTeacherResponse;
import com.app.kidsdrawing.dto.GetSemesterClassForScheduleClassResponse;
import com.app.kidsdrawing.dto.GetSemesterClassParentResponse;
import com.app.kidsdrawing.dto.GetSemesterClassResponse;
import com.app.kidsdrawing.dto.GetSemesterClassStudentResponse;
import com.app.kidsdrawing.dto.GetSemesterClassTeacherNewResponse;
import com.app.kidsdrawing.entity.Course;
import com.app.kidsdrawing.entity.Semester;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.exception.ArtAgeNotDeleteException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.exception.SemesterClassAlreadyCreateException;
import com.app.kidsdrawing.repository.CourseRepository;
import com.app.kidsdrawing.repository.SemesterClassRepository;
import com.app.kidsdrawing.repository.SemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinSemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterTeachSemesterRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.SemesterClassService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SemesterClassServiceImpl implements SemesterClassService {

    private final SemesterClassRepository semesterClassRepository;
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final UserRegisterTeachSemesterRepository userRegisterTeachSemesterRepository;
    private final UserRegisterJoinSemesterRepository userRegisterJoinSemesterRepository;
    private final ClassesRepository classRepository;
    private static String schedule = "";
    private static int total_register = 0;

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClass1() {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<SemesterClass> pageSemesterClass = semesterClassRepository.findAll();
        LocalDateTime time_now = LocalDateTime.now();
        pageSemesterClass.forEach(semesterClass -> {
            if (time_now.isBefore(semesterClass.getRegistration_time())) {
                GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                    .id(semesterClass.getId())
                    .name(semesterClass.getName())
                    .is_new(true)
                    .semester_id(semesterClass.getSemester().getId())
                    .semester_name(semesterClass.getSemester().getName())
                    .course_id(semesterClass.getCourse().getId())
                    .course_name(semesterClass.getCourse().getName())
                    .max_participant(semesterClass.getMax_participant())
                    .registration_time(semesterClass.getRegistration_time())
                    .registration_expiration_time(semesterClass.getRegistration_expiration_time())
                    .build();
                allSemesterClassResponses.add(semesterClassResponse);
            }
            else {
                GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                    .id(semesterClass.getId())
                    .name(semesterClass.getName())
                    .is_new(false)
                    .semester_id(semesterClass.getSemester().getId())
                    .semester_name(semesterClass.getSemester().getName())
                    .course_id(semesterClass.getCourse().getId())
                    .course_name(semesterClass.getCourse().getName())
                    .max_participant(semesterClass.getMax_participant())
                    .registration_time(semesterClass.getRegistration_time())
                    .registration_expiration_time(semesterClass.getRegistration_expiration_time())
                    .build();
                allSemesterClassResponses.add(semesterClassResponse);
            }
            
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClass() {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<SemesterClass> pageSemesterClass = semesterClassRepository.findAll();
        pageSemesterClass.forEach(semesterClass -> {
            GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                    .id(semesterClass.getId())
                    .name(semesterClass.getName())
                    .semester_id(semesterClass.getSemester().getId())
                    .semester_name(semesterClass.getSemester().getName())
                    .course_id(semesterClass.getCourse().getId())
                    .course_name(semesterClass.getCourse().getName())
                    .max_participant(semesterClass.getMax_participant())
                    .registration_time(semesterClass.getRegistration_time())
                    .registration_expiration_time(semesterClass.getRegistration_expiration_time())
                    .build();
            allSemesterClassResponses.add(semesterClassResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassNew() {
        List<GetCourseTeacherResponse> allSemesterClassResponses = new ArrayList<>();
        LocalDateTime time_now = LocalDateTime.now();
        List<SemesterClass> allSemesterClass = semesterClassRepository.findAll1();
        allSemesterClass.forEach(semesterClass -> {
            if (semesterClass.getRegistration_time().isAfter(time_now) == false
                    && semesterClass.getSemester().getStart_time().isAfter(time_now)) {
                schedule = "";
                semesterClass.getSchedules().forEach(schedule_item -> {
                    if (schedule.equals("")) {
                        schedule = schedule + "Thứ " + schedule_item.getDate_of_week() + " ("
                                + schedule_item.getLessonTime().getStart_time().toString() + " - "
                                + schedule_item.getLessonTime().getEnd_time().toString() + ")";
                    } else {
                        schedule = schedule + ", Thứ " + schedule_item.getDate_of_week() + " ("
                                + schedule_item.getLessonTime().getStart_time().toString() + " - "
                                + schedule_item.getLessonTime().getEnd_time().toString() + ")";
                    }
                });
                GetCourseTeacherResponse semesterClassResponse = GetCourseTeacherResponse.builder()
                        .course_id(semesterClass.getCourse().getId())
                        .semster_class_id(semesterClass.getId())
                        .id(semesterClass.getId())
                        .semester_name(semesterClass.getSemester().getName())
                        .description(semesterClass.getCourse().getDescription())
                        .image_url(semesterClass.getCourse().getImage_url())
                        .name(semesterClass.getName())
                        .course_name(semesterClass.getCourse().getName())
                        .art_age_name(semesterClass.getCourse().getArtAges().getName())
                        .art_level_name(semesterClass.getCourse().getArtLevels().getName())
                        .art_type_name(semesterClass.getCourse().getArtTypes().getName())
                        .price(semesterClass.getCourse().getPrice())
                        .registration_deadline(semesterClass.getRegistration_expiration_time())
                        .num_of_section(semesterClass.getCourse().getNum_of_section())
                        .schedule(schedule)
                        .build();
                allSemesterClassResponses.add(semesterClassResponse);
            }
        });
        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassNewByParentAndCourse(Long parent_id, Long course_id) {
        List<GetSemesterClassParentResponse> allSemesterClassResponses = new ArrayList<>();
        LocalDateTime time_now = LocalDateTime.now();
        List<SemesterClass> allSemesterClassByCourse = semesterClassRepository
                .findByCourseId3(course_id);
        allSemesterClassByCourse.forEach(semester_class -> {
            if (time_now.isAfter(semester_class.getRegistration_time()) && time_now.isBefore(semester_class.getRegistration_expiration_time())) {
                LocalDateTime start_time = semester_class.getSemester().getStart_time();
                Integer dayOfWeek = new ArrayList<>(semester_class.getSchedules()).get(0).getDate_of_week();
                if (semester_class.getSchedules().size() > 1) {
                    if (dayOfWeek == 2) {
                        while (start_time.getDayOfWeek() != DayOfWeek.MONDAY) {
                            start_time = start_time.plusDays(1);
                        }
                    } else if (dayOfWeek == 3) {
                        while (start_time.getDayOfWeek() != DayOfWeek.TUESDAY) {
                            start_time = start_time.plusDays(1);
                        }
                    }

                    else if (dayOfWeek == 4) {
                        while (start_time.getDayOfWeek() != DayOfWeek.WEDNESDAY) {

                            start_time = start_time.plusDays(1);
                        }
                    }

                    else if (dayOfWeek == 5) {
                        while (start_time.getDayOfWeek() != DayOfWeek.THURSDAY) {

                            start_time = start_time.plusDays(1);
                        }
                    }

                    else if (dayOfWeek == 6) {
                        while (start_time.getDayOfWeek() != DayOfWeek.FRIDAY) {

                            start_time = start_time.plusDays(1);
                        }
                    }

                    else if (dayOfWeek == 7) {
                        while (start_time.getDayOfWeek() != DayOfWeek.SATURDAY) {

                            start_time = start_time.plusDays(1);
                        }
                    }

                    else {
                        while (start_time.getDayOfWeek() != DayOfWeek.SUNDAY) {

                            start_time = start_time.plusDays(1);
                        }
                    }
                } else {
                    if (dayOfWeek == 2) {
                        start_time = start_time.plusDays(7);

                    } else if (dayOfWeek == 3) {
                        start_time = start_time.plusDays(7);
                    }

                    else if (dayOfWeek == 4) {

                        start_time = start_time.plusDays(7);
                    }

                    else if (dayOfWeek == 5) {

                        start_time = start_time.plusDays(7);
                    }

                    else if (dayOfWeek == 6) {

                        start_time = start_time.plusDays(7);
                    }

                    else if (dayOfWeek == 7) {

                        start_time = start_time.plusDays(7);
                    }

                    else {

                        start_time = start_time.plusDays(7);
                    }
                }
                LocalDate start_date = start_time.toLocalDate();
                schedule = "";
                semester_class.getSchedules().forEach(schedule_item -> {
                    if (schedule.equals("")) {
                        schedule = schedule + "Thứ " + schedule_item.getDate_of_week() + " ("
                                + schedule_item.getLessonTime().getStart_time().toString() + " - "
                                + schedule_item.getLessonTime().getEnd_time().toString() + ")";
                    } else {
                        schedule = schedule + ", Thứ " + schedule_item.getDate_of_week() + " ("
                                + schedule_item.getLessonTime().getStart_time().toString() + " - "
                                + schedule_item.getLessonTime().getEnd_time().toString() + ")";
                    }
                });
                total_register = userRegisterJoinSemesterRepository.findBySemesterClassId4(semester_class.getId()).size();
                List<User> allChildForParent = userRepository.findByParentId(parent_id);
                allChildForParent.forEach(ele -> {
                    Optional<UserRegisterJoinSemester> userRegisterJoinSemesterOpt = userRegisterJoinSemesterRepository.findBySemesterClassIdAndStudent(semester_class.getId(), ele.getId());
                    if (userRegisterJoinSemesterOpt.isPresent()) {
                        if (userRegisterJoinSemesterOpt.get().getStatus().equals("Completed")) {
                            GetSemesterClassParentResponse semesterClassResponse = GetSemesterClassParentResponse.builder()
                                .course_id(semester_class.getCourse().getId())
                                .id(semester_class.getId())
                                .semester_name(semester_class.getSemester().getName())
                                .description(semester_class.getCourse().getDescription())
                                .image_url(semester_class.getCourse().getImage_url())
                                .name(semester_class.getName())
                                .course_name(semester_class.getCourse().getName())
                                .art_age_name(semester_class.getCourse().getArtAges().getName())
                                .art_level_name(semester_class.getCourse().getArtLevels().getName())
                                .art_type_name(semester_class.getCourse().getArtTypes().getName())
                                .price(semester_class.getCourse().getPrice())
                                .registration_time(semester_class.getRegistration_time())
                                .registration_expiration_time(semester_class.getRegistration_expiration_time())
                                .num_of_section(semester_class.getCourse().getNum_of_section())
                                .schedule(schedule)
                                .total_register(total_register)
                                .max_participant(semester_class.getMax_participant())
                                .semester_id(semester_class.getSemester().getId())
                                .student_id(ele.getId())
                                .start_date(start_date)
                                .student_name(ele.getUsername())
                                .status("Payed")
                                .build();
                            allSemesterClassResponses.add(semesterClassResponse);
                        }
                        else {
                            GetSemesterClassParentResponse semesterClassResponse = GetSemesterClassParentResponse.builder()
                                .course_id(semester_class.getCourse().getId())
                                .id(semester_class.getId())
                                .semester_name(semester_class.getSemester().getName())
                                .description(semester_class.getCourse().getDescription())
                                .image_url(semester_class.getCourse().getImage_url())
                                .name(semester_class.getName())
                                .course_name(semester_class.getCourse().getName())
                                .art_age_name(semester_class.getCourse().getArtAges().getName())
                                .art_level_name(semester_class.getCourse().getArtLevels().getName())
                                .art_type_name(semester_class.getCourse().getArtTypes().getName())
                                .price(semester_class.getCourse().getPrice())
                                .registration_time(semester_class.getRegistration_time())
                                .registration_expiration_time(semester_class.getRegistration_expiration_time())
                                .num_of_section(semester_class.getCourse().getNum_of_section())
                                .schedule(schedule)
                                .total_register(total_register)
                                .max_participant(semester_class.getMax_participant())
                                .semester_id(semester_class.getSemester().getId())
                                .student_id(ele.getId())
                                .start_date(start_date)
                                .student_name(ele.getUsername())
                                .status("Not pay now")
                                .build();
                            allSemesterClassResponses.add(semesterClassResponse);
                        }
                    }
                    else {
                        GetSemesterClassParentResponse semesterClassResponse = GetSemesterClassParentResponse.builder()
                            .course_id(semester_class.getCourse().getId())
                            .id(semester_class.getId())
                            .semester_name(semester_class.getSemester().getName())
                            .description(semester_class.getCourse().getDescription())
                            .image_url(semester_class.getCourse().getImage_url())
                            .name(semester_class.getName())
                            .course_name(semester_class.getCourse().getName())
                            .art_age_name(semester_class.getCourse().getArtAges().getName())
                            .art_level_name(semester_class.getCourse().getArtLevels().getName())
                            .art_type_name(semester_class.getCourse().getArtTypes().getName())
                            .price(semester_class.getCourse().getPrice())
                            .registration_time(semester_class.getRegistration_time())
                            .registration_expiration_time(semester_class.getRegistration_expiration_time())
                            .num_of_section(semester_class.getCourse().getNum_of_section())
                            .schedule(schedule)
                            .total_register(total_register)
                            .max_participant(semester_class.getMax_participant())
                            .semester_id(semester_class.getSemester().getId())
                            .student_id(ele.getId())
                            .start_date(start_date)
                            .student_name(ele.getUsername())
                            .status("Not register")
                            .build();
                        allSemesterClassResponses.add(semesterClassResponse);
                    }
                });
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassNewByStudentAndCourse(Long id, Long course_id) {
        List<GetSemesterClassStudentResponse> allSemesterClassResponses = new ArrayList<>();
        LocalDateTime time_now = LocalDateTime.now();
        List<SemesterClass> allSemesterClassByStudentAndCourseWaiting = semesterClassRepository
                .findAllSemesterClassByStudentAndCourseWaiting(id, course_id);
        List<SemesterClass> allSemesterClassByStudentAndCourseCompleted = semesterClassRepository
                .findAllSemesterClassByStudentAndCourseCompleted(id, course_id);

        List<SemesterClass> allSemesterClass = semesterClassRepository.findByCourseId3(course_id);

        System.out.print(allSemesterClass.size());

        allSemesterClass.forEach(semester_class -> {
            if (semester_class.getRegistration_time().isBefore(time_now)
                    && semester_class.getRegistration_expiration_time().isAfter(time_now)) {
                schedule = "";
                semester_class.getSchedules().forEach(schedule_item -> {
                    if (schedule.equals("")) {
                        schedule = schedule + "Thứ " + schedule_item.getDate_of_week() + " ("
                                + schedule_item.getLessonTime().getStart_time().toString() + " - "
                                + schedule_item.getLessonTime().getEnd_time().toString() + ")";
                    } else {
                        schedule = schedule + ", Thứ " + schedule_item.getDate_of_week() + " ("
                                + schedule_item.getLessonTime().getStart_time().toString() + " - "
                                + schedule_item.getLessonTime().getEnd_time().toString() + ")";
                    }
                });

                total_register = userRegisterJoinSemesterRepository.findBySemesterClassId4(semester_class.getId()).size();
                if (allSemesterClassByStudentAndCourseCompleted.contains(semester_class) == false && allSemesterClassByStudentAndCourseWaiting.contains(semester_class) == false) {
                    GetSemesterClassStudentResponse semesterClassResponse = GetSemesterClassStudentResponse.builder()
                            .course_id(semester_class.getCourse().getId())
                            .semster_class_id(semester_class.getId())
                            .id(semester_class.getId())
                            .semester_name(semester_class.getSemester().getName())
                            .description(semester_class.getCourse().getDescription())
                            .image_url(semester_class.getCourse().getImage_url())
                            .name(semester_class.getName())
                            .course_name(semester_class.getCourse().getName())
                            .art_age_name(semester_class.getCourse().getArtAges().getName())
                            .art_level_name(semester_class.getCourse().getArtLevels().getName())
                            .art_type_name(semester_class.getCourse().getArtTypes().getName())
                            .price(semester_class.getCourse().getPrice())
                            .registration_time(semester_class.getRegistration_time())
                            .registration_expiration_time(semester_class.getRegistration_expiration_time())
                            .num_of_section(semester_class.getCourse().getNum_of_section())
                            .schedule(schedule)
                            .total_register(total_register)
                            .max_participant(semester_class.getMax_participant())
                            .semester_id(semester_class.getSemester().getId())
                            .status("Not register")
                            .build();
                    allSemesterClassResponses.add(semesterClassResponse);
                }

                else if (allSemesterClassByStudentAndCourseWaiting.contains(semester_class)) {
                    GetSemesterClassStudentResponse semesterClassResponse = GetSemesterClassStudentResponse.builder()
                            .course_id(semester_class.getCourse().getId())
                            .semster_class_id(semester_class.getId())
                            .id(semester_class.getId())
                            .semester_name(semester_class.getSemester().getName())
                            .description(semester_class.getCourse().getDescription())
                            .image_url(semester_class.getCourse().getImage_url())
                            .name(semester_class.getName())
                            .course_name(semester_class.getCourse().getName())
                            .art_age_name(semester_class.getCourse().getArtAges().getName())
                            .art_level_name(semester_class.getCourse().getArtLevels().getName())
                            .art_type_name(semester_class.getCourse().getArtTypes().getName())
                            .price(semester_class.getCourse().getPrice())
                            .registration_time(semester_class.getRegistration_time())
                            .registration_expiration_time(semester_class.getRegistration_expiration_time())
                            .num_of_section(semester_class.getCourse().getNum_of_section())
                            .schedule(schedule)
                            .total_register(total_register)
                            .max_participant(semester_class.getMax_participant())
                            .semester_id(semester_class.getSemester().getId())
                            .status("Unpaid")
                            .build();
                    allSemesterClassResponses.add(semesterClassResponse);
                }
                 else {
                    GetSemesterClassStudentResponse semesterClassResponse = GetSemesterClassStudentResponse.builder()
                            .course_id(semester_class.getCourse().getId())
                            .semster_class_id(semester_class.getId())
                            .id(semester_class.getId())
                            .semester_name(semester_class.getSemester().getName())
                            .description(semester_class.getCourse().getDescription())
                            .image_url(semester_class.getCourse().getImage_url())
                            .name(semester_class.getName())
                            .course_name(semester_class.getCourse().getName())
                            .art_age_name(semester_class.getCourse().getArtAges().getName())
                            .art_level_name(semester_class.getCourse().getArtLevels().getName())
                            .art_type_name(semester_class.getCourse().getArtTypes().getName())
                            .price(semester_class.getCourse().getPrice())
                            .registration_time(semester_class.getRegistration_time())
                            .registration_expiration_time(semester_class.getRegistration_expiration_time())
                            .num_of_section(semester_class.getCourse().getNum_of_section())
                            .schedule(schedule)
                            .total_register(total_register)
                            .max_participant(semester_class.getMax_participant())
                            .semester_id(semester_class.getSemester().getId())
                            .status("Registed")
                            .build();
                    allSemesterClassResponses.add(semesterClassResponse);
                }

            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassNewByTeacherAndCourse(Long id, Long course_id) {
        List<GetSemesterClassTeacherNewResponse> allSemesterClassResponses = new ArrayList<>();
        LocalDateTime time_now = LocalDateTime.now();
        List<SemesterClass> allSemesterClassByTeacherAndCourse = semesterClassRepository
                .findAllSemesterClassByTeacherAndCourse(id, course_id);

        List<SemesterClass> allSemesterClass = semesterClassRepository.findByCourseId3(course_id);

        allSemesterClass.forEach(semester_class -> {
            if (semester_class.getRegistration_time().isAfter(time_now)
                    && semester_class.getSemester().getStart_time().isAfter(time_now)) {
                schedule = "";
                semester_class.getSchedules().forEach(schedule_item -> {
                    if (schedule.equals("")) {
                        schedule = schedule + "Thứ " + schedule_item.getDate_of_week() + " ("
                                + schedule_item.getLessonTime().getStart_time().toString() + " - "
                                + schedule_item.getLessonTime().getEnd_time().toString() + ")";
                    } else {
                        schedule = schedule + ", Thứ " + schedule_item.getDate_of_week() + " ("
                                + schedule_item.getLessonTime().getStart_time().toString() + " - "
                                + schedule_item.getLessonTime().getEnd_time().toString() + ")";
                    }
                });

                total_register = userRegisterTeachSemesterRepository.findBySemesterClassId1(semester_class.getId()).size();

                if (allSemesterClassByTeacherAndCourse.contains(semester_class) == false) {
                    GetSemesterClassTeacherNewResponse semesterClassResponse = GetSemesterClassTeacherNewResponse
                            .builder()
                            .course_id(semester_class.getCourse().getId())
                            .id(semester_class.getId())
                            .semester_name(semester_class.getSemester().getName())
                            .description(semester_class.getCourse().getDescription())
                            .image_url(semester_class.getCourse().getImage_url())
                            .name(semester_class.getName())
                            .course_name(semester_class.getCourse().getName())
                            .art_age_name(semester_class.getCourse().getArtAges().getName())
                            .art_level_name(semester_class.getCourse().getArtLevels().getName())
                            .art_type_name(semester_class.getCourse().getArtTypes().getName())
                            .price(semester_class.getCourse().getPrice())
                            .registration_deadline(semester_class.getRegistration_expiration_time())
                            .num_of_section(semester_class.getCourse().getNum_of_section())
                            .schedule(schedule)
                            .total_register(total_register)
                            .max_participant(semester_class.getMax_participant())
                            .semester_id(semester_class.getSemester().getId())
                            .status("Not register")
                            .build();
                    allSemesterClassResponses.add(semesterClassResponse);
                } else {
                    GetSemesterClassTeacherNewResponse semesterClassResponse = GetSemesterClassTeacherNewResponse
                            .builder()
                            .course_id(semester_class.getCourse().getId())
                            .id(semester_class.getId())
                            .semester_name(semester_class.getSemester().getName())
                            .description(semester_class.getCourse().getDescription())
                            .image_url(semester_class.getCourse().getImage_url())
                            .name(semester_class.getName())
                            .course_name(semester_class.getCourse().getName())
                            .art_age_name(semester_class.getCourse().getArtAges().getName())
                            .art_level_name(semester_class.getCourse().getArtLevels().getName())
                            .art_type_name(semester_class.getCourse().getArtTypes().getName())
                            .price(semester_class.getCourse().getPrice())
                            .registration_deadline(semester_class.getRegistration_expiration_time())
                            .num_of_section(semester_class.getCourse().getNum_of_section())
                            .schedule(schedule)
                            .total_register(total_register)
                            .max_participant(semester_class.getMax_participant())
                            .semester_id(semester_class.getSemester().getId())
                            .status("Registed")
                            .build();
                    allSemesterClassResponses.add(semesterClassResponse);
                }
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassHistoryOfStudent(Long id) {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> userRegisterJoinSemester = userRegisterJoinSemesterRepository
                .findByStudentId2(id);
        LocalDateTime time_now = LocalDateTime.now();
        userRegisterJoinSemester.forEach(user_register_join_semester -> {
            if (time_now.isAfter(user_register_join_semester.getSemesterClass().getSemester().getEnd_time())) {
                GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                        .id(user_register_join_semester.getSemesterClass().getId())
                        .name(user_register_join_semester.getSemesterClass().getName())
                        .semester_id(user_register_join_semester.getSemesterClass().getSemester().getId())
                        .semester_name(user_register_join_semester.getSemesterClass().getSemester().getName())
                        .course_id(user_register_join_semester.getSemesterClass().getCourse().getId())
                        .course_name(user_register_join_semester.getSemesterClass().getCourse().getName())
                        .max_participant(user_register_join_semester.getSemesterClass().getMax_participant())
                        .registration_time(user_register_join_semester.getSemesterClass().getRegistration_time())
                        .build();
                allSemesterClassResponses.add(semesterClassResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassPresentOfStudent(Long id) {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> userRegisterJoinSemester = userRegisterJoinSemesterRepository
                .findByStudentId2(id);
        LocalDateTime time_now = LocalDateTime.now();
        userRegisterJoinSemester.forEach(user_register_join_semester -> {
            if (time_now.isBefore(user_register_join_semester.getSemesterClass().getSemester().getEnd_time())) {
                GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                        .id(user_register_join_semester.getSemesterClass().getId())
                        .name(user_register_join_semester.getSemesterClass().getName())
                        .semester_id(user_register_join_semester.getSemesterClass().getSemester().getId())
                        .semester_name(user_register_join_semester.getSemesterClass().getSemester().getName())
                        .course_id(user_register_join_semester.getSemesterClass().getCourse().getId())
                        .course_name(user_register_join_semester.getSemesterClass().getCourse().getName())
                        .max_participant(user_register_join_semester.getSemesterClass().getMax_participant())
                        .registration_time(user_register_join_semester.getSemesterClass().getRegistration_time())
                        .build();
                allSemesterClassResponses.add(semesterClassResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetSemesterClassResponse getSemesterClassById(Long id) {
        Optional<SemesterClass> semesterClassOpt = semesterClassRepository.findById2(id);
        SemesterClass semesterClass = semesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        return GetSemesterClassResponse.builder()
                .id(semesterClass.getId())
                .name(semesterClass.getName())
                .semester_id(semesterClass.getSemester().getId())
                .semester_name(semesterClass.getSemester().getName())
                .course_id(semesterClass.getCourse().getId())
                .course_name(semesterClass.getCourse().getName())
                .max_participant(semesterClass.getMax_participant())
                .registration_time(semesterClass.getRegistration_time())
                .build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassBySemester(Long id) {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<SemesterClass> pageSemesterClass = semesterClassRepository.findAll();
        pageSemesterClass.forEach(semesterClass -> {
            if (semesterClass.getSemester().getId().compareTo(id) == 0) {
                GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                        .id(semesterClass.getId())
                        .name(semesterClass.getName())
                        .semester_id(semesterClass.getSemester().getId())
                        .semester_name(semesterClass.getSemester().getName())
                        .course_id(semesterClass.getCourse().getId())
                        .course_name(semesterClass.getCourse().getName())
                        .max_participant(semesterClass.getMax_participant())
                        .registration_time(semesterClass.getRegistration_time())
                        .build();
                allSemesterClassResponses.add(semesterClassResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassBySemesterScheduleClass(Long id) {
        List<GetSemesterClassForScheduleClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<SemesterClass> pageSemesterClass = semesterClassRepository.findBySemesterId1(id);
        pageSemesterClass.forEach(semesterClass -> {
            GetSemesterClassForScheduleClassResponse semesterClassResponse = GetSemesterClassForScheduleClassResponse
                    .builder()
                    .id(semesterClass.getId())
                    .build();
            allSemesterClassResponses.add(semesterClassResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassByCourse(Long id) {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<SemesterClass> pageSemesterClass = semesterClassRepository.findByCourseId2(id);
        pageSemesterClass.forEach(semesterClass -> {
            GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                    .id(semesterClass.getId())
                    .name(semesterClass.getName())
                    .semester_id(semesterClass.getSemester().getId())
                    .semester_name(semesterClass.getSemester().getName())
                    .course_id(semesterClass.getCourse().getId())
                    .course_name(semesterClass.getCourse().getName())
                    .max_participant(semesterClass.getMax_participant())
                    .registration_time(semesterClass.getRegistration_time())
                    .build();
            allSemesterClassResponses.add(semesterClassResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetSemesterClassResponse createSemesterClass(CreateSemesterClassRequest createSemesterClassRequest) {
        Optional<Semester> semesterOpt = semesterRepository.findById1(createSemesterClassRequest.getSemester_id());
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester.not_found");
        });

        Optional<Course> courseOpt = courseRepository.findById1(createSemesterClassRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        if (semesterClassRepository.existsByName(createSemesterClassRequest.getName())) {
            throw new SemesterClassAlreadyCreateException("exception.semester_name.semester_class_taken");
        }

        if (semester.getStart_time().isBefore(createSemesterClassRequest.getRegistration_time())) {
            throw new SemesterClassAlreadyCreateException("exception.semester_name.time_error");
        }

        SemesterClass savedSemesterClass = SemesterClass.builder()
                .semester(semester)
                .course(course)
                .name(createSemesterClassRequest.getName())
                .registration_time(createSemesterClassRequest.getRegistration_time())
                .registration_expiration_time(createSemesterClassRequest.getRegistration_expiration_time())
                .build();
        semesterClassRepository.save(savedSemesterClass);

        GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                .id(savedSemesterClass.getId())
                .name(savedSemesterClass.getName())
                .semester_id(savedSemesterClass.getSemester().getId())
                .semester_name(savedSemesterClass.getSemester().getName())
                .course_id(savedSemesterClass.getCourse().getId())
                .course_name(savedSemesterClass.getCourse().getName())
                .max_participant(savedSemesterClass.getMax_participant())
                .registration_time(savedSemesterClass.getRegistration_time())
                .build();

        return semesterClassResponse;
    }

    @Override
    public Long removeSemesterClassById(Long id) {
        Optional<SemesterClass> SemesterClassOpt = semesterClassRepository.findById1(id);
        SemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        List<Classes> listClass = classRepository.findAllBySemesterClass(id);
        LocalDateTime time_now = LocalDateTime.now();

        for (int i = 0; i < listClass.size(); i++) {
            if (time_now.isBefore(listClass.get(i).getUserRegisterTeachSemester().getSemesterClass().getSemester().getEnd_time())) {
                throw new ArtAgeNotDeleteException("exception.SemesterClass_Classes.not_delete");
            }
        }

        semesterClassRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateSemesterClassById(Long id, CreateSemesterClassRequest createSemesterClassRequest) {
        Optional<SemesterClass> SemesterClassOpt = semesterClassRepository.findById1(id);
        SemesterClass updatedSemesterClass = SemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        Optional<Semester> semesterOpt = semesterRepository.findById1(createSemesterClassRequest.getSemester_id());
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester.not_found");
        });

        Optional<Course> courseOpt = courseRepository.findById1(createSemesterClassRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        if (semester.getStart_time().isBefore(createSemesterClassRequest.getRegistration_time())) {
            throw new SemesterClassAlreadyCreateException("exception.semester_name.time_error");
        }

        if (createSemesterClassRequest.getName().equals(updatedSemesterClass.getName()) == false) {
            if (semesterClassRepository.existsByName(createSemesterClassRequest.getName())) {
                throw new SemesterClassAlreadyCreateException("exception.semester_name.semester_class_taken");
            }
        }

        updatedSemesterClass.setSemester(semester);
        updatedSemesterClass.setCourse(course);
        updatedSemesterClass.setName(createSemesterClassRequest.getName());
        updatedSemesterClass.setRegistration_time(createSemesterClassRequest.getRegistration_time());
        updatedSemesterClass.setRegistration_expiration_time(createSemesterClassRequest.getRegistration_expiration_time());
        semesterClassRepository.save(updatedSemesterClass);

        return updatedSemesterClass.getId();
    }

    @Override
    public String updateSemesterClassMaxParticipantById(Long id) {
        Optional<SemesterClass> SemesterClassOpt = semesterClassRepository.findById1(id);
        SemesterClass updatedSemesterClass = SemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        List<UserRegisterTeachSemester> pageUserRegisterTeachSemesters = userRegisterTeachSemesterRepository.findAll();
        List<UserRegisterTeachSemester> allUserRegisterTeachSemesters = new ArrayList<>();
        pageUserRegisterTeachSemesters.forEach(user_register_teach_semester -> {
            if (user_register_teach_semester.getSemesterClass().getId().compareTo(id) == 0) {
                allUserRegisterTeachSemesters.add(user_register_teach_semester);
            }
        });

        updatedSemesterClass.setMax_participant((allUserRegisterTeachSemesters.size()) * 8);
        semesterClassRepository.save(updatedSemesterClass);
        return "Max participant successfull!";
    }
}
