package com.app.kidsdrawing.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateClassRequest;
import com.app.kidsdrawing.dto.GetArtAgeResponse;
import com.app.kidsdrawing.dto.GetArtLevelResponse;
import com.app.kidsdrawing.dto.GetArtTypeResponse;
import com.app.kidsdrawing.dto.GetClassResponse;
import com.app.kidsdrawing.dto.GetCourseResponse;
import com.app.kidsdrawing.dto.GetInfoClassTeacherResponse;
import com.app.kidsdrawing.dto.GetScheduleResponse;
import com.app.kidsdrawing.dto.GetSemesterResponse;
import com.app.kidsdrawing.dto.GetStudentResponse;
import com.app.kidsdrawing.dto.GetUserResponse;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.entity.Class;
import com.app.kidsdrawing.entity.LessonTime;
import com.app.kidsdrawing.entity.Semester;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.UserRegisterTeachSemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinSemesterRepository;
import com.app.kidsdrawing.repository.ClassRepository;
import com.app.kidsdrawing.repository.ScheduleRepository;
import com.app.kidsdrawing.repository.SemesterClassRepository;
import com.app.kidsdrawing.repository.SemesterRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.ClassesService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ClassesServiceImpl implements ClassesService {

    private final ClassRepository classRepository;
    private final UserRegisterTeachSemesterRepository userRegisterTeachSemesterRepository;
    private final UserRepository userRepository;
    private final UserRegisterJoinSemesterRepository userRegisterJoinSemesterRepository;
    private final SemesterClassRepository semesterClassRepository;
    private final ScheduleRepository scheduleRepository;
    private final SemesterRepository semesterRepository;

    private static int total_section_count = 0;
    private static int week_count = 0;
    private static String schedule = "";
    @Override
    public ResponseEntity<Map<String, Object>> getAllClass() {
        List<GetClassResponse> allClassResponses = new ArrayList<>();
        List<Class> listClass = classRepository.findAll();
        listClass.forEach(content -> {
            GetClassResponse classResponse = GetClassResponse.builder()
                    .id(content.getId())
                    .creator_id(content.getUser().getId())
                    .user_register_teach_semester(content.getUserRegisterTeachSemester().getId())
                    .security_code(content.getSecurity_code())
                    .name(content.getName())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
            allClassResponses.add(classResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("classes", allClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getInforDetailOfClassByTeacherId(Long id) {
        List<GetInfoClassTeacherResponse> allInfoClassTeacherDoingResponses = new ArrayList<>();
        List<GetInfoClassTeacherResponse> allInfoClassTeacherDoneResponses = new ArrayList<>();
        List<Map<String,List<Map<String, List<List<LocalDateTime>>>>>> allScheduleTime = new ArrayList<>();
        List<List<GetStudentResponse>> allStudentDoneResponses = new ArrayList<>();
        List<List<GetStudentResponse>> allStudentDoingResponses = new ArrayList<>();
        List<Class> listClass = classRepository.findAll();
        LocalDateTime time_now = LocalDateTime.now();
        listClass.forEach(ele -> {
            schedule = "";
            LocalDateTime res = getEndSectionOfClass(ele.getId());

            scheduleRepository.findAll().forEach(schedule_ele -> {
                if ( ele.getUserRegisterTeachSemester().getSemesterClass().getSchedules().contains(schedule_ele)){
                    if (schedule == ""){
                        schedule = schedule + "Thứ " + schedule_ele.getDate_of_week() + " (" + schedule_ele.getLessonTime().getStart_time().toString() + " - " + schedule_ele.getLessonTime().getEnd_time().toString() +")";
                    }
                    else {
                        schedule = schedule + ", Thứ " + schedule_ele.getDate_of_week() + " (" + schedule_ele.getLessonTime().getStart_time().toString() + " - " + schedule_ele.getLessonTime().getEnd_time().toString() +")";
                    }
                }
            });
            if (ele.getUserRegisterTeachSemester().getTeacher().getId() == id){
                if (time_now.isAfter(res) == false){
                    List<Map<String, List<List<LocalDateTime>>>> schedule_time = getScheduleDetailOfClass(ele.getId());
                    Map<String,List<Map<String, List<List<LocalDateTime>>>>> x = new HashMap<>();
                    x.put(ele.getName(),schedule_time);
                    allScheduleTime.add(x);
                    GetInfoClassTeacherResponse infoClassTeacherResponse = GetInfoClassTeacherResponse.builder()
                        .id(ele.getId())
                        .user_register_teach_semester(ele.getUserRegisterTeachSemester().getId())
                        .security_code(ele.getSecurity_code())
                        .name(ele.getName())
                        .course_id(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getId())
                        .course_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getName())
                        .semester_name(ele.getUserRegisterTeachSemester().getSemesterClass().getSemester().getName())
                        .semster_course_id(ele.getUserRegisterTeachSemester().getSemesterClass().getId())
                        .total_student(ele.getUserRegisterJoinSemesters().size())
                        .num_of_section(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getNum_of_section())
                        .art_age_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getArtAges().getName())
                        .art_type_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getArtTypes().getName())
                        .art_level_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getArtLevels().getName())
                        .schedule(schedule)
                        .build();
                    allInfoClassTeacherDoingResponses.add(infoClassTeacherResponse);
                    
                    List<GetStudentResponse> listStudents = new ArrayList<>();
                    ele.getUserRegisterJoinSemesters().forEach(content -> {
                        String parent_name = content.getStudent().getParent().getUsername();
                        GetStudentResponse student = GetStudentResponse.builder()
                                .id(content.getStudent().getId())
                                .username(content.getStudent().getUsername())
                                .email(content.getStudent().getEmail())
                                .firstName(content.getStudent().getFirstName())
                                .lastName(content.getStudent().getLastName())
                                .dateOfBirth(content.getStudent().getDateOfBirth())
                                .profile_image_url(content.getStudent().getProfileImageUrl())
                                .sex(content.getStudent().getSex())
                                .phone(content.getStudent().getPhone())
                                .address(content.getStudent().getAddress())
                                .parent(parent_name)
                                .createTime(content.getStudent().getCreateTime())
                                .build();
                        listStudents.add(student);
                    });
                    allStudentDoingResponses.add(listStudents);
                }

                else {
                    GetInfoClassTeacherResponse infoClassTeacherResponse = GetInfoClassTeacherResponse.builder()
                        .id(ele.getId())
                        .user_register_teach_semester(ele.getUserRegisterTeachSemester().getId())
                        .security_code(ele.getSecurity_code())
                        .name(ele.getName())
                        .course_id(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getId())
                        .course_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getName())
                        .semester_name(ele.getUserRegisterTeachSemester().getSemesterClass().getSemester().getName())
                        .semster_course_id(ele.getUserRegisterTeachSemester().getSemesterClass().getId())
                        .total_student(ele.getUserRegisterJoinSemesters().size())
                        .num_of_section(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getNum_of_section())
                        .art_age_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getArtAges().getName())
                        .art_type_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getArtTypes().getName())
                        .art_level_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getArtLevels().getName())
                        .schedule(schedule)
                        .build();
                    allInfoClassTeacherDoneResponses.add(infoClassTeacherResponse);

                    List<GetStudentResponse> listStudentDones = new ArrayList<>();
                    ele.getUserRegisterJoinSemesters().forEach(content -> {
                        String parent_name = content.getStudent().getParent().getUsername();
                        GetStudentResponse student = GetStudentResponse.builder()
                                .id(content.getStudent().getId())
                                .username(content.getStudent().getUsername())
                                .email(content.getStudent().getEmail())
                                .firstName(content.getStudent().getFirstName())
                                .lastName(content.getStudent().getLastName())
                                .dateOfBirth(content.getStudent().getDateOfBirth())
                                .profile_image_url(content.getStudent().getProfileImageUrl())
                                .sex(content.getStudent().getSex())
                                .phone(content.getStudent().getPhone())
                                .address(content.getStudent().getAddress())
                                .parent(parent_name)
                                .createTime(content.getStudent().getCreateTime())
                                .build();
                        listStudentDones.add(student);
                    });
                    allStudentDoneResponses.add(listStudentDones);
                }
                
            }
        });
        Map<String, Object> response = new HashMap<>();
        response.put("classes_doning", allInfoClassTeacherDoingResponses);
        response.put("schedule_time", allScheduleTime);
        response.put("students_done", allStudentDoneResponses);
        response.put("students_doing", allStudentDoingResponses);
        response.put("classes_done", allInfoClassTeacherDoneResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getInforDetailAllClass() {
        List<GetClassResponse> allClassResponses = new ArrayList<>();
        List<GetSemesterResponse> allSemesterResponses = new ArrayList<>();
        List<GetCourseResponse> allCourseResponses = new ArrayList<>();
        List<GetStudentResponse> allUserResponses = new ArrayList<>();
        List<Class> listClass = classRepository.findAll();
        listClass.forEach(content -> {
            GetClassResponse classResponse = GetClassResponse.builder()
                    .id(content.getId())
                    .creator_id(content.getUser().getId())
                    .user_register_teach_semester(content.getUserRegisterTeachSemester().getId())
                    .security_code(content.getSecurity_code())
                    .name(content.getName())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
            allClassResponses.add(classResponse);

            Optional<UserRegisterTeachSemester> teacherTeachSemesterOpt = userRegisterTeachSemesterRepository
                    .findById(content.getUserRegisterTeachSemester().getId());
            UserRegisterTeachSemester userRegisterTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
                throw new EntityNotFoundException("exception.TeacherTeachSemester.not_found");
            });

            Optional<SemesterClass> semester_classOpt = semesterClassRepository
                    .findById(userRegisterTeachSemester.getSemesterClass().getId());
            SemesterClass semesterCouse = semester_classOpt.orElseThrow(() -> {
                throw new EntityNotFoundException("exception.semester_class.not_found");
            });

            GetSemesterResponse semesterResponse = GetSemesterResponse.builder()
                    .id(semesterCouse.getSemester().getId())
                    .name(semesterCouse.getSemester().getName())
                    .description(semesterCouse.getSemester().getDescription())
                    .start_time(semesterCouse.getSemester().getStart_time())
                    .number(semesterCouse.getSemester().getNumber())
                    .year(semesterCouse.getSemester().getYear())
                    .create_time(semesterCouse.getSemester().getCreate_time())
                    .update_time(semesterCouse.getSemester().getUpdate_time())
                    .creator_id(semesterCouse.getSemester().getUser().getId())
                    .build();
            allSemesterResponses.add(semesterResponse);

            GetCourseResponse courseResponse = GetCourseResponse.builder()
                    .id(semesterCouse.getCourse().getId())
                    .name(semesterCouse.getCourse().getName())
                    .description(semesterCouse.getCourse().getDescription())
                    .num_of_section(semesterCouse.getCourse().getNum_of_section())
                    .image_url(semesterCouse.getCourse().getImage_url())
                    .price(semesterCouse.getCourse().getPrice())
                    .is_enabled(semesterCouse.getCourse().getIs_enabled())
                    .art_age_id(semesterCouse.getCourse().getArtAges().getId())
                    .art_type_id(semesterCouse.getCourse().getArtTypes().getId())
                    .art_level_id(semesterCouse.getCourse().getArtLevels().getId())
                    .creator_id(semesterCouse.getCourse().getUser().getId())
                    .create_time(semesterCouse.getCourse().getCreate_time())
                    .update_time(semesterCouse.getCourse().getUpdate_time())
                    .build();
            allCourseResponses.add(courseResponse);

            String parent_name = userRegisterTeachSemester.getTeacher().getParent().getUsername();

            GetStudentResponse userResponse = GetStudentResponse.builder()
                    .id(userRegisterTeachSemester.getTeacher().getId())
                    .username(userRegisterTeachSemester.getTeacher().getUsername())
                    .email(userRegisterTeachSemester.getTeacher().getEmail())
                    .firstName(userRegisterTeachSemester.getTeacher().getFirstName())
                    .lastName(userRegisterTeachSemester.getTeacher().getLastName())
                    .dateOfBirth(userRegisterTeachSemester.getTeacher().getDateOfBirth())
                    .profile_image_url(userRegisterTeachSemester.getTeacher().getProfileImageUrl())
                    .sex(userRegisterTeachSemester.getTeacher().getSex())
                    .phone(userRegisterTeachSemester.getTeacher().getPhone())
                    .address(userRegisterTeachSemester.getTeacher().getAddress())
                    .parent(parent_name)
                    .createTime(userRegisterTeachSemester.getTeacher().getCreateTime())
                    .build();
            allUserResponses.add(userResponse);

        });

        Map<String, Object> response = new HashMap<>();
        response.put("classes", allClassResponses);
        response.put("courses", allCourseResponses);
        response.put("semesters", allSemesterResponses);
        response.put("teachers", allUserResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getInforScheduleAllClass(Long id) {
        LocalDateTime time_now = LocalDateTime.now();
        List<Semester> allSemesters = semesterRepository.findAll();
        List<Class> allClassDoing = new ArrayList<>();
        allSemesters.forEach(semester  -> {
            if (semester.getEnd_time().isAfter(time_now)){
                semesterClassRepository.findBySemesterId(semester.getId()).forEach(semester_class -> {
                    userRegisterTeachSemesterRepository.findBySemesterClassId(semester_class.getId()).forEach(user_register_teache_semester -> {
                        if (classRepository.existsByUserRegisterTeachSemester(user_register_teache_semester.getId())){
                            allClassDoing.add(classRepository.findByUserRegisterTeachSemester(user_register_teache_semester.getId()));
                        }
                    });
                });
            }
        });

        List<List<Map<String, List<List<LocalDateTime>>>>> allScheduleForAllClass = new ArrayList<>();
        allClassDoing.forEach(class_ele -> {
            allScheduleForAllClass.add(getScheduleDetailOfClass(class_ele.getId()));
        });
        Map<String, Object> response = new HashMap<>();
        response.put("schedules", allScheduleForAllClass);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public LocalDateTime getEndSectionOfClass(Long id) {
        Optional<Class> classOpt = classRepository.findById(id);
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        Optional<UserRegisterTeachSemester> teacherTeachSemesterOpt = userRegisterTeachSemesterRepository
                .findById(classes.getUserRegisterTeachSemester().getId());
        UserRegisterTeachSemester userRegisterTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherTeachSemester.not_found");
        });


        Optional<SemesterClass> semester_classOpt = semesterClassRepository
                .findById(userRegisterTeachSemester.getSemesterClass().getId());
        SemesterClass semesterCouse = semester_classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_class.not_found");
        });


        List<Integer> dayOfWeeks = new ArrayList<>();
        semesterCouse.getSchedules().forEach(ele -> {
            dayOfWeeks.add(ele.getDate_of_week());
        });

        Collections.sort(dayOfWeeks); 

        List<LessonTime> lessonTimeResponses = new ArrayList<>();
        semesterCouse.getSchedules().forEach(ele -> {
            lessonTimeResponses.add(ele.getLessonTime());
        });

        List<LocalDate> list_holiday = new ArrayList<>();
        semesterCouse.getSemester().getHolidays().forEach(holiday -> {
            list_holiday.add(holiday.getDay());
        });

        List<Map<String, List<List<LocalDateTime>>>> allCalendarForSemesterClass = new ArrayList<>();
        Integer total_section = semesterCouse.getCourse().getNum_of_section();
        System.out.printf("total_section: %d\n", total_section);
        System.out.printf("total_number_week: %d\n", semesterCouse.getSchedules().size());
        int total_week = total_section / semesterCouse.getSchedules().size();
        if (total_section % semesterCouse.getSchedules().size() != 0){
            total_week ++;
        }
        System.out.printf("total_week: %d\n", total_week);
        total_section_count = 0;
        LocalDateTime start_time = semesterCouse.getSemester().getStart_time();
        week_count = 0;
        while (total_section_count < total_section) {
            List<List<LocalDateTime>> lesson_time_in_week = new ArrayList<>();
            for (int idx = 0; idx < semesterCouse.getSchedules().size(); idx++) {
                Integer dayOfWeek = dayOfWeeks.get(idx);
                LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                System.out.printf("Day_of_week: %d\n", dayOfWeek);
                List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
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

                if (total_section_count < total_section) {
                    LocalDate start_date = start_time.toLocalDate();
                    if (list_holiday.contains(start_date) == false) {
                        lesson_time_in_day.add(start_lessontime.atDate(start_date));
                        lesson_time_in_day.add(end_lessontime.atDate(start_date));
                        total_section_count++;
                    }
                }
                lesson_time_in_week.add(lesson_time_in_day); 
            }
            Map<String, List<List<LocalDateTime>>> schedule_in_week = new HashMap<>(); 
            String name = "week_" + week_count;
            schedule_in_week.put(name, lesson_time_in_week);
            allCalendarForSemesterClass.add(schedule_in_week);
            //start_time = start_time.plusWeeks(1);
            week_count ++;
        }

        return start_time;
    }

    public List<Map<String, List<List<LocalDateTime>>>> getScheduleDetailOfClass(Long id) {
        Optional<Class> classOpt = classRepository.findById(id);
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        Optional<UserRegisterTeachSemester> teacherTeachSemesterOpt = userRegisterTeachSemesterRepository
                .findById(classes.getUserRegisterTeachSemester().getId());
        UserRegisterTeachSemester userRegisterTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherTeachSemester.not_found");
        });

        Optional<SemesterClass> semester_classOpt = semesterClassRepository
                .findById(userRegisterTeachSemester.getSemesterClass().getId());
        SemesterClass semesterCouse = semester_classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_class.not_found");
        });

        List<GetScheduleResponse> allScheduleResponses = new ArrayList<>();
        semesterCouse.getSchedules().forEach(schedule_item -> {
            GetScheduleResponse scheduleResponse = GetScheduleResponse.builder()
                    .id(schedule_item.getId())
                    .lesson_time(schedule_item.getLessonTime().getStart_time().toString() + " - " + schedule_item.getLessonTime().getEnd_time().toString())
                    .lesson_time_id(schedule_item.getLessonTime().getId())
                    .date_of_week(schedule_item.getDate_of_week())
                    .build();
            allScheduleResponses.add(scheduleResponse);
        });

        List<Integer> dayOfWeeks = new ArrayList<>();
        semesterCouse.getSchedules().forEach(ele -> {
            dayOfWeeks.add(ele.getDate_of_week());
        });

        Collections.sort(dayOfWeeks); 

        List<LessonTime> lessonTimeResponses = new ArrayList<>();
        semesterCouse.getSchedules().forEach(schedule_item -> {
            lessonTimeResponses.add(schedule_item.getLessonTime());
        });

        List<LocalDate> list_holiday = new ArrayList<>();
        semesterCouse.getSemester().getHolidays().forEach(holiday -> {
            list_holiday.add(holiday.getDay());
        });

        List<Map<String, List<List<LocalDateTime>>>> allCalendarForSemesterClass = new ArrayList<>();
        Integer total_section = semesterCouse.getCourse().getNum_of_section();
        System.out.printf("total_section: %d\n", total_section);
        System.out.printf("total_number_week: %d\n", semesterCouse.getSchedules().size());
        int total_week = total_section / semesterCouse.getSchedules().size();
        if (total_section % semesterCouse.getSchedules().size() != 0){
            total_week ++;
        }
        System.out.printf("total_week: %d\n", total_week);
        total_section_count = 0;
        LocalDateTime start_time = semesterCouse.getSemester().getStart_time();
        week_count = 0;
        while (total_section_count < total_section) {
            List<List<LocalDateTime>> lesson_time_in_week = new ArrayList<>();
            for (int idx = 0; idx < semesterCouse.getSchedules().size(); idx++) {
                Integer dayOfWeek = dayOfWeeks.get(idx);
                LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                System.out.printf("Day_of_week: %d\n", dayOfWeek);
                List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
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

                if (total_section_count < total_section) {
                    LocalDate start_date = start_time.toLocalDate();
                    if (list_holiday.contains(start_date) == false) {
                        lesson_time_in_day.add(start_lessontime.atDate(start_date));
                        lesson_time_in_day.add(end_lessontime.atDate(start_date));
                        total_section_count++;
                    }
                }
                lesson_time_in_week.add(lesson_time_in_day); 
            }
            Map<String, List<List<LocalDateTime>>> schedule_in_week = new HashMap<>(); 
            String name = "week_" + week_count;
            schedule_in_week.put(name, lesson_time_in_week);
            allCalendarForSemesterClass.add(schedule_in_week);
            //start_time = start_time.plusWeeks(1);
            week_count ++;
        }

        return  allCalendarForSemesterClass;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getInforDetailOfClass(Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Class> classOpt = classRepository.findById(id);
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });
        response.put("classes", GetClassResponse.builder()
                .id(classes.getId())
                .creator_id(classes.getUser().getId())
                .user_register_teach_semester(classes.getUserRegisterTeachSemester().getId())
                .security_code(classes.getSecurity_code())
                .name(classes.getName())
                .create_time(classes.getCreate_time())
                .update_time(classes.getUpdate_time())
                .build());

        Optional<UserRegisterTeachSemester> teacherTeachSemesterOpt = userRegisterTeachSemesterRepository
                .findById(classes.getUserRegisterTeachSemester().getId());
        UserRegisterTeachSemester userRegisterTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherTeachSemester.not_found");
        });

        response.put("teacher", GetUserResponse.builder()
                .id(userRegisterTeachSemester.getTeacher().getId())
                .username(userRegisterTeachSemester.getTeacher().getUsername())
                .email(userRegisterTeachSemester.getTeacher().getEmail())
                .firstName(userRegisterTeachSemester.getTeacher().getFirstName())
                .lastName(userRegisterTeachSemester.getTeacher().getLastName())
                .dateOfBirth(userRegisterTeachSemester.getTeacher().getDateOfBirth())
                .profile_image_url(userRegisterTeachSemester.getTeacher().getProfileImageUrl())
                .sex(userRegisterTeachSemester.getTeacher().getSex())
                .phone(userRegisterTeachSemester.getTeacher().getPhone())
                .address(userRegisterTeachSemester.getTeacher().getAddress())
                .createTime(userRegisterTeachSemester.getTeacher().getCreateTime())
                .build());

        Optional<SemesterClass> semester_classOpt = semesterClassRepository
                .findById(userRegisterTeachSemester.getSemesterClass().getId());
        SemesterClass semesterCouse = semester_classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_class.not_found");
        });

        response.put("course", GetCourseResponse.builder()
                .id(semesterCouse.getCourse().getId())
                .name(semesterCouse.getCourse().getName())
                .description(semesterCouse.getCourse().getDescription())
                .num_of_section(semesterCouse.getCourse().getNum_of_section())
                .image_url(semesterCouse.getCourse().getImage_url())
                .price(semesterCouse.getCourse().getPrice())
                .is_enabled(semesterCouse.getCourse().getIs_enabled())
                .art_age_id(semesterCouse.getCourse().getArtAges().getId())
                .art_type_id(semesterCouse.getCourse().getArtTypes().getId())
                .art_level_id(semesterCouse.getCourse().getArtLevels().getId())
                .creator_id(semesterCouse.getCourse().getUser().getId())
                .create_time(semesterCouse.getCourse().getCreate_time())
                .update_time(semesterCouse.getCourse().getUpdate_time())
                .build());

        response.put("semester", GetSemesterResponse.builder()
                .id(semesterCouse.getSemester().getId())
                .name(semesterCouse.getSemester().getName())
                .description(semesterCouse.getSemester().getDescription())
                .start_time(semesterCouse.getSemester().getStart_time())
                .number(semesterCouse.getSemester().getNumber())
                .year(semesterCouse.getSemester().getYear())
                .create_time(semesterCouse.getSemester().getCreate_time())
                .update_time(semesterCouse.getSemester().getUpdate_time())
                .creator_id(semesterCouse.getSemester().getUser().getId())
                .build());

        List<GetStudentResponse> listStudents = new ArrayList<>();
        classes.getUserRegisterJoinSemesters().forEach(content -> {
            Long parent_idx = content.getStudent().getParent().getId();
            String parent_namex = content.getStudent().getParent().getFirstName() + " " + content.getStudent().getParent().getLastName();
            GetStudentResponse student = GetStudentResponse.builder()
                    .id(content.getStudent().getId())
                    .username(content.getStudent().getUsername())
                    .email(content.getStudent().getEmail())
                    .firstName(content.getStudent().getFirstName())
                    .lastName(content.getStudent().getLastName())
                    .dateOfBirth(content.getStudent().getDateOfBirth())
                    .profile_image_url(content.getStudent().getProfileImageUrl())
                    .sex(content.getStudent().getSex())
                    .phone(content.getStudent().getPhone())
                    .address(content.getStudent().getAddress())
                    .parent(parent_namex)
                    .parents(parent_idx)
                    .createTime(content.getStudent().getCreateTime())
                    .build();
            listStudents.add(student);
        });

        List<GetScheduleResponse> allScheduleResponses = new ArrayList<>();
        semesterCouse.getSchedules().forEach(schedule_item -> {
            GetScheduleResponse scheduleResponse = GetScheduleResponse.builder()
                    .id(schedule_item.getId())
                    .lesson_time(schedule_item.getLessonTime().getStart_time().toString() + " - " + schedule_item.getLessonTime().getEnd_time().toString())
                    .lesson_time_id(schedule_item.getLessonTime().getId())
                    .date_of_week(schedule_item.getDate_of_week())
                    .build();
            allScheduleResponses.add(scheduleResponse);
        });

        List<Integer> dayOfWeeks = new ArrayList<>();
        semesterCouse.getSchedules().forEach(ele -> {
            dayOfWeeks.add(ele.getDate_of_week());
        });

        Collections.sort(dayOfWeeks); 

        List<LessonTime> lessonTimeResponses = new ArrayList<>();
        semesterCouse.getSchedules().forEach(schedule_item -> {
            lessonTimeResponses.add(schedule_item.getLessonTime());
        });

        List<LocalDate> list_holiday = new ArrayList<>();
        semesterCouse.getSemester().getHolidays().forEach(holiday -> {
            list_holiday.add(holiday.getDay());
        });

        List<Map<String, List<List<LocalDateTime>>>> allCalendarForSemesterClass = new ArrayList<>();
        Integer total_section = semesterCouse.getCourse().getNum_of_section();
        System.out.printf("total_section: %d\n", total_section);
        System.out.printf("total_number_week: %d\n", semesterCouse.getSchedules().size());
        int total_week = total_section / semesterCouse.getSchedules().size();
        if (total_section % semesterCouse.getSchedules().size() != 0){
            total_week ++;
        }
        System.out.printf("total_week: %d\n", total_week);
        total_section_count = 0;
        LocalDateTime start_time = semesterCouse.getSemester().getStart_time();
        week_count = 0;
        while (total_section_count < total_section) {
            List<List<LocalDateTime>> lesson_time_in_week = new ArrayList<>();
            for (int idx = 0; idx < semesterCouse.getSchedules().size(); idx++) {
                Integer dayOfWeek = dayOfWeeks.get(idx);
                LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                System.out.printf("Day_of_week: %d\n", dayOfWeek);
                List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
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

                if (total_section_count < total_section) {
                    LocalDate start_date = start_time.toLocalDate();
                    if (list_holiday.contains(start_date) == false) {
                        lesson_time_in_day.add(start_lessontime.atDate(start_date));
                        lesson_time_in_day.add(end_lessontime.atDate(start_date));
                        total_section_count++;
                    }
                }
                lesson_time_in_week.add(lesson_time_in_day); 
            }
            Map<String, List<List<LocalDateTime>>> schedule_in_week = new HashMap<>(); 
            String name = "week_" + week_count;
            schedule_in_week.put(name, lesson_time_in_week);
            allCalendarForSemesterClass.add(schedule_in_week);
            //start_time = start_time.plusWeeks(1);
            week_count ++;
        }

        response.put("schedule", allScheduleResponses);
        response.put("art_type", GetArtTypeResponse.builder()
                .id(semesterCouse.getCourse().getArtTypes().getId())
                .name(semesterCouse.getCourse().getArtTypes().getName())
                .description(semesterCouse.getCourse().getArtTypes().getDescription())
                .build());
        response.put("art_level", GetArtLevelResponse.builder()
                .id(semesterCouse.getCourse().getArtLevels().getId())
                .name(semesterCouse.getCourse().getArtLevels().getName())
                .description(semesterCouse.getCourse().getArtLevels().getDescription())
                .build());
        response.put("art_age", GetArtAgeResponse.builder()
                .id(semesterCouse.getCourse().getArtAges().getId())
                .name(semesterCouse.getCourse().getArtAges().getName())
                .description(semesterCouse.getCourse().getArtAges().getDescription())
                .build());
        response.put("students", listStudents);
        response.put("lesson_time", allCalendarForSemesterClass);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetClassResponse getClassById(Long id) {
        Optional<Class> classOpt = classRepository.findById(id);
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        return GetClassResponse.builder()
                .id(classes.getId())
                .creator_id(classes.getUser().getId())
                .user_register_teach_semester(classes.getUserRegisterTeachSemester().getId())
                .security_code(classes.getSecurity_code())
                .name(classes.getName())
                .create_time(classes.getCreate_time())
                .update_time(classes.getUpdate_time())
                .build();
    }

    @Override
    public Long createClass(CreateClassRequest createClassRequest) {
        Optional<UserRegisterTeachSemester> teacherTeachSemesterOpt = userRegisterTeachSemesterRepository
                .findById(createClassRequest.getUser_register_teach_semester());
        UserRegisterTeachSemester teacherTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.teacher_teach_semester.not_found");
        });

        List<UserRegisterJoinSemester> validUserRegisterSemesters = new ArrayList<>();
        createClassRequest.getUser_register_join_semester().forEach(user_register_join_semester_id -> {
            userRegisterJoinSemesterRepository.findById(user_register_join_semester_id).<Runnable>map(
                    user_register_join_semester -> () -> validUserRegisterSemesters.add(user_register_join_semester))
                    .orElseThrow(() -> {
                        throw new EntityNotFoundException(String.format("exception.user_register_join_semester.invalid",
                                user_register_join_semester_id));
                    })
                    .run();
        });

        Optional<User> userOpt = userRepository.findById(createClassRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Class savedClass = Class.builder()
                .user(user)
                .userRegisterTeachSemester(teacherTeachSemester)
                .security_code(createClassRequest.getSecurity_code())
                .name(createClassRequest.getName())
                .userRegisterJoinSemesters(new HashSet<>(validUserRegisterSemesters))
                .build();
        classRepository.save(savedClass);

        return savedClass.getId();
    }

    @Override
    public Long removeClassById(Long id) {
        Optional<Class> classOpt = classRepository.findById(id);
        classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        classRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateClassById(Long id, CreateClassRequest createClassRequest) {
        Optional<Class> classOpt = classRepository.findById(id);
        Class updatedClass = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        Optional<UserRegisterTeachSemester> teacherTeachSemesterOpt = userRegisterTeachSemesterRepository
                .findById(createClassRequest.getUser_register_teach_semester());
        UserRegisterTeachSemester teacherTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.teacher_teach_semester.not_found");
        });

        Optional<User> userOpt = userRepository.findById(createClassRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        updatedClass.setName(createClassRequest.getName());
        updatedClass.setSecurity_code(createClassRequest.getSecurity_code());
        updatedClass.setUser(user);
        updatedClass.setUserRegisterTeachSemester(teacherTeachSemester);

        return updatedClass.getId();
    }
}
