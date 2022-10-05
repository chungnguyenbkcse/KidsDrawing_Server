package com.app.kidsdrawing.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateCourseRequest;
import com.app.kidsdrawing.dto.GetCourseNewResponse;
import com.app.kidsdrawing.dto.GetCourseResponse;
import com.app.kidsdrawing.dto.GetCourseTeacherResponse;
import com.app.kidsdrawing.dto.GetReportCourseResponse;
import com.app.kidsdrawing.dto.GetCourseParentResponse;
import com.app.kidsdrawing.entity.ArtAge;
import com.app.kidsdrawing.entity.ArtLevel;
import com.app.kidsdrawing.entity.ArtType;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.Course;
import com.app.kidsdrawing.entity.Semester;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.exception.CourseAlreadyCreateException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ArtAgeRepository;
import com.app.kidsdrawing.repository.ArtLevelRepository;
import com.app.kidsdrawing.repository.ArtTypeRepository;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.CourseRepository;
import com.app.kidsdrawing.repository.ScheduleRepository;
import com.app.kidsdrawing.repository.SemesterClassRepository;
import com.app.kidsdrawing.repository.SemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterTeachSemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinSemesterRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.CourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ArtAgeRepository artAgeRepository;
    private final ArtTypeRepository artTypeRepository;
    private final ArtLevelRepository artLevelRepository;
    private final ClassesRepository classRepository;
    private final UserRegisterTeachSemesterRepository userRegisterTeachSemesterRepository;
    private final UserRegisterJoinSemesterRepository userRegisterJoinSemesterRepository;
    private final SemesterClassRepository semesterClassRepository;
    private final ScheduleRepository scheduleRepository;
    private final SemesterRepository semesterRepository;
    private static String schedule = "";
    private static int total = 0;

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourse() {
        List<GetCourseResponse> allCourseResponses = new ArrayList<>();
        List<Course> pageCourse = courseRepository.findAll();
        pageCourse.forEach(course -> {
            GetCourseResponse courseResponse = GetCourseResponse.builder()
                    .id(course.getId())
                    .name(course.getName())
                    .description(course.getDescription())
                    .num_of_section(course.getNum_of_section())
                    .image_url(course.getImage_url())
                    .price(course.getPrice())
                    .is_enabled(course.getIs_enabled())
                    .art_age_id(course.getArtAges().getId())
                    .art_type_id(course.getArtTypes().getId())
                    .art_level_id(course.getArtLevels().getId())
                    .creator_id(course.getUser().getId())
                    .create_time(course.getCreate_time())
                    .update_time(course.getUpdate_time())
                    .build();
            allCourseResponses.add(courseResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("courses", allCourseResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourseNewByStudentId(UUID id) {
        List<GetCourseNewResponse> courses = new ArrayList<>();
        List<UserRegisterJoinSemester> userRegisterJoinSemesters = userRegisterJoinSemesterRepository.findByStudentId2(id);
        List<Course> listCourseRegisted = new ArrayList<>();
        userRegisterJoinSemesters.forEach(user_register_join_semester -> {
            if (user_register_join_semester.getStatus() == "Completed") {
                listCourseRegisted.add(user_register_join_semester.getSemesterClass().getCourse());
            }
        });

        List<Semester> semesterNexts = new ArrayList<>();
        LocalDateTime time_now = LocalDateTime.now();
        List<Semester> pageSemester = semesterRepository.findAll();
        pageSemester.forEach(semester -> {
            if (time_now.isBefore(semester.getStart_time())) {
                semesterNexts.add(semester);
            }
        });

        List<Course> allCourses = courseRepository.findAll1();
        allCourses.forEach(course -> {
            if (listCourseRegisted.contains(course) == false) {
                total = 0;
                Set<SemesterClass> allSemesterClass = course.getSemesterClasses();

                allSemesterClass.forEach(semester_course -> {
                    if (semesterNexts.contains(semester_course.getSemester())){
                        total ++;
                    }
                });
                GetCourseNewResponse courseResponse = GetCourseNewResponse.builder()
                    .id(course.getId())
                    .name(course.getName())
                    .description(course.getDescription())
                    .num_of_section(course.getNum_of_section())
                    .image_url(course.getImage_url())
                    .price(course.getPrice())
                    .is_enabled(course.getIs_enabled())
                    .art_age_id(course.getArtAges().getId())
                    .art_type_id(course.getArtTypes().getId())
                    .art_level_id(course.getArtLevels().getId())
                    .art_age_name(course.getArtAges().getName())
                    .art_level_name(course.getArtLevels().getName())
                    .art_type_name(course.getArtTypes().getName())
                    .creator_id(course.getUser().getId())
                    .create_time(course.getCreate_time())
                    .update_time(course.getUpdate_time())
                    .total(total)
                    .build();
                courses.add(courseResponse);
            }
        });
        Map<String, Object> response = new HashMap<>();
        response.put("courses", courses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getReportCourse(int year) {
        List<GetReportCourseResponse> allReportCourseResponses = new ArrayList<>();

        List<UserRegisterJoinSemester> listUserRegisterJoinSemester = userRegisterJoinSemesterRepository.findAll1();
            listUserRegisterJoinSemester.forEach(ele -> {
                if (ele.getSemesterClass().getSemester().getStart_time().getYear() == year && ele.getStatus().equals("Completed")) {
                    GetReportCourseResponse courseResponse = GetReportCourseResponse.builder()
                        .id(ele.getSemesterClass().getCourse().getId())
                        .name(ele.getSemesterClass().getCourse().getName())
                        .description(ele.getSemesterClass().getCourse().getDescription())
                        .num_of_section(ele.getSemesterClass().getCourse().getNum_of_section())
                        .image_url(ele.getSemesterClass().getCourse().getImage_url())
                        .price(ele.getSemesterClass().getCourse().getPrice())
                        .is_enabled(ele.getSemesterClass().getCourse().getIs_enabled())
                        .art_age_id(ele.getSemesterClass().getCourse().getArtAges().getId())
                        .art_type_id(ele.getSemesterClass().getCourse().getArtTypes().getId())
                        .art_level_id(ele.getSemesterClass().getCourse().getArtLevels().getId())
                        .creator_id(ele.getSemesterClass().getCourse().getUser().getId())
                        .create_time(ele.getSemesterClass().getCourse().getCreate_time())
                        .update_time(ele.getSemesterClass().getCourse().getUpdate_time())
                        .build();
                    allReportCourseResponses.add(courseResponse);
                }
            });

        Map<String, Object> response = new HashMap<>();
        response.put("report_course", allReportCourseResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourseByArtTypeId(UUID id) {
        List<GetCourseResponse> allCourseResponses = new ArrayList<>();
        List<Course> pageCourse = courseRepository.findAll();
        pageCourse.forEach(course -> {
            if (course.getArtTypes().getId() == id){
                GetCourseResponse courseResponse = GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .create_time(course.getCreate_time())
                .update_time(course.getUpdate_time())
                .build();
            allCourseResponses.add(courseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("courses", allCourseResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourseByArtAgeId(UUID id) {
        List<GetCourseResponse> allCourseResponses = new ArrayList<>();
        List<Course> pageCourse = courseRepository.findAll();
        pageCourse.forEach(course -> {
            if (course.getArtAges().getId() == id){
                GetCourseResponse courseResponse = GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .create_time(course.getCreate_time())
                .update_time(course.getUpdate_time())
                .build();
            allCourseResponses.add(courseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("courses", allCourseResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourseByArtLevelId(UUID id) {
        List<GetCourseResponse> allCourseResponses = new ArrayList<>();
        List<Course> pageCourse = courseRepository.findAll();
        pageCourse.forEach(course -> {
            if (course.getArtLevels().getId() == id){
                GetCourseResponse courseResponse = GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .create_time(course.getCreate_time())
                .update_time(course.getUpdate_time())
                .build();
            allCourseResponses.add(courseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("courses", allCourseResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourseByParentId(UUID id) {
        List<GetCourseParentResponse> allCourseRegistedResponses = new ArrayList<>();
        List<User> pageUser = userRepository.findByParentId3(id);
        List<Course> listCourseRegistered = new ArrayList<>();
        List<GetCourseParentResponse> listCourseNotRegistered = new ArrayList<>();
        List<Course> allCourse = courseRepository.findAll1();

        pageUser.forEach(student -> {
            student.getUserRegisterJoinSemesters2().forEach(course -> {
                if (course.getStatus().equals("Completed")) {
                    listCourseRegistered.add(course.getSemesterClass().getCourse());
                    GetCourseParentResponse courseResponse = GetCourseParentResponse.builder()
                        .id(course.getSemesterClass().getCourse().getId())
                        .name(course.getSemesterClass().getCourse().getName())
                        .description(course.getSemesterClass().getCourse().getDescription())
                        .num_of_section(course.getSemesterClass().getCourse().getNum_of_section())
                        .image_url(course.getSemesterClass().getCourse().getImage_url())
                        .price(course.getSemesterClass().getCourse().getPrice())
                        .is_enabled(course.getSemesterClass().getCourse().getIs_enabled())
                        .art_age_id(course.getSemesterClass().getCourse().getArtAges().getId())
                        .art_age_name(course.getSemesterClass().getCourse().getArtAges().getName())
                        .art_type_id(course.getSemesterClass().getCourse().getArtTypes().getId())
                        .art_type_name(course.getSemesterClass().getCourse().getArtTypes().getName())
                        .art_level_id(course.getSemesterClass().getCourse().getArtLevels().getId())
                        .art_level_name(course.getSemesterClass().getCourse().getArtLevels().getName())
                        .creator_id(course.getSemesterClass().getCourse().getUser().getId())
                        .create_time(course.getSemesterClass().getCourse().getCreate_time())
                        .update_time(course.getSemesterClass().getCourse().getUpdate_time())
                        .student_id(student.getId())
                        .student_name(student.getFirstName() + " " + student.getLastName())
                        .build();
                    allCourseRegistedResponses.add(courseResponse);
                }
            });
        });

        pageUser.forEach(student -> {
            allCourse.forEach(course -> {
                if (listCourseRegistered.contains(course) == false) {
                    GetCourseParentResponse courseResponse = GetCourseParentResponse.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .description(course.getDescription())
                        .num_of_section(course.getNum_of_section())
                        .image_url(course.getImage_url())
                        .price(course.getPrice())
                        .is_enabled(course.getIs_enabled())
                        .art_age_id(course.getArtAges().getId())
                        .art_age_name(course.getArtAges().getName())
                        .art_type_id(course.getArtTypes().getId())
                        .art_type_name(course.getArtTypes().getName())
                        .art_level_id(course.getArtLevels().getId())
                        .art_level_name(course.getArtLevels().getName())
                        .creator_id(course.getUser().getId())
                        .create_time(course.getCreate_time())
                        .update_time(course.getUpdate_time())
                        .student_id(student.getId())
                        .student_name(student.getFirstName() + " " + student.getLastName())
                        .build();
                    listCourseNotRegistered.add(courseResponse);
                }
            });
        });

        Map<String, Object> response = new HashMap<>();
        response.put("courses_registed", allCourseRegistedResponses);
        response.put("courses_not_registed_now", listCourseNotRegistered);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourseByStudentId(UUID id) {
        List<GetCourseParentResponse> allCourseRegistedResponses = new ArrayList<>();
        List<GetCourseParentResponse> allCourseNotRegistedNowResponses = new ArrayList<>();
        List<Course> listCourseRegistered = new ArrayList<>();
        List<Course> allCourse = courseRepository.findAll1();
        List<UserRegisterJoinSemester> listUserRegisterJoinSemester = userRegisterJoinSemesterRepository.findByStudentId3(id);
        listUserRegisterJoinSemester.forEach(user_register_join_semester -> {
            if (user_register_join_semester.getStatus().equals("Completed")) {
                    GetCourseParentResponse courseResponse = GetCourseParentResponse.builder()
                    .id(user_register_join_semester.getSemesterClass().getCourse().getId())
                    .name(user_register_join_semester.getSemesterClass().getCourse().getName())
                    .description(user_register_join_semester.getSemesterClass().getCourse().getDescription())
                    .num_of_section(user_register_join_semester.getSemesterClass().getCourse().getNum_of_section())
                    .image_url(user_register_join_semester.getSemesterClass().getCourse().getImage_url())
                    .price(user_register_join_semester.getSemesterClass().getCourse().getPrice())
                    .is_enabled(user_register_join_semester.getSemesterClass().getCourse().getIs_enabled())
                    .art_age_id(user_register_join_semester.getSemesterClass().getCourse().getArtAges().getId())
                    .art_age_name(user_register_join_semester.getSemesterClass().getCourse().getArtAges().getName())
                    .art_type_id(user_register_join_semester.getSemesterClass().getCourse().getArtTypes().getId())
                    .art_type_name(user_register_join_semester.getSemesterClass().getCourse().getArtTypes().getName())
                    .art_level_id(user_register_join_semester.getSemesterClass().getCourse().getArtLevels().getId())
                    .art_level_name(user_register_join_semester.getSemesterClass().getCourse().getArtLevels().getName())
                    .creator_id(user_register_join_semester.getSemesterClass().getCourse().getUser().getId())
                    .create_time(user_register_join_semester.getSemesterClass().getCourse().getCreate_time())
                    .update_time(user_register_join_semester.getSemesterClass().getCourse().getUpdate_time())
                    .build();
                allCourseRegistedResponses.add(courseResponse);
            }
        });
        allCourse.forEach(course -> {
            if (!listCourseRegistered.contains(course)) {
                GetCourseParentResponse courseResponse = GetCourseParentResponse.builder()
                    .id(course.getId())
                    .name(course.getName())
                    .description(course.getDescription())
                    .num_of_section(course.getNum_of_section())
                    .image_url(course.getImage_url())
                    .price(course.getPrice())
                    .is_enabled(course.getIs_enabled())
                    .art_age_id(course.getArtAges().getId())
                    .art_age_name(course.getArtAges().getName())
                    .art_type_id(course.getArtTypes().getId())
                    .art_type_name(course.getArtTypes().getName())
                    .art_level_id(course.getArtLevels().getId())
                    .art_level_name(course.getArtLevels().getName())
                    .creator_id(course.getUser().getId())
                    .create_time(course.getCreate_time())
                    .update_time(course.getUpdate_time())
                    .build();
                allCourseNotRegistedNowResponses.add(courseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("courses_registed", allCourseRegistedResponses);
        response.put("courses_not_registed_now", allCourseNotRegistedNowResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourseByTeacherId(UUID id) {
        List<UserRegisterTeachSemester> listTeacherTeachSemester = userRegisterTeachSemesterRepository.findByTeacherId3(id);
        
        // Danh sach dang ki day cua giao vien
        List<UserRegisterTeachSemester> allTeacherTeachSemesterResponses = new ArrayList<>();
        // Danh sach khoa hoc theo ki giao vien da dang ki
        List<SemesterClass> allRegisteredSemesterClassResponses = new ArrayList<>();

        listTeacherTeachSemester.forEach(ele -> {
                allTeacherTeachSemesterResponses.add(ele);
                allRegisteredSemesterClassResponses.add(ele.getSemesterClass());
        });

        // Danh sach khoa hoc theo ki giao vien chua dang ki
        List<SemesterClass> allNotRegisterSemesterClass = new ArrayList<>();
        LocalDateTime time_now = LocalDateTime.now();
        List<SemesterClass> pageSemesterClass = semesterClassRepository.findAll1();
        pageSemesterClass.forEach(ele -> {
            if ((allRegisteredSemesterClassResponses.contains(ele) == false && time_now.isBefore(ele.getSemester().getStart_time())) ){
                allNotRegisterSemesterClass.add(ele);
            }
        });

        List<GetCourseTeacherResponse> allNotRegisterSemesterClassResponses = new ArrayList<>();
        allNotRegisterSemesterClass.forEach(ele -> {
            schedule = "";
            ele.getSchedules().forEach(schedule_item -> {
                if (schedule_item.getSemesterClass().getId().compareTo(ele.getId()) == 0){
                    if (schedule.equals("") ){
                        schedule = schedule + "Thứ " + schedule_item.getDate_of_week() + " (" + schedule_item.getLessonTime().getStart_time().toString() + " - " + schedule_item.getLessonTime().getEnd_time().toString() +")";
                    }
                    else {
                        schedule = schedule + ", Thứ " + schedule_item.getDate_of_week() + " (" + schedule_item.getLessonTime().getStart_time().toString() + " - " + schedule_item.getLessonTime().getEnd_time().toString() +")";
                    }
                }
            });
            GetCourseTeacherResponse notRegisterSemesterClassResponse = GetCourseTeacherResponse.builder()
                .course_id(ele.getCourse().getId())
                .id(ele.getId())
                .semster_class_id(ele.getId())
                .semester_name(ele.getSemester().getName())
                .description(ele.getCourse().getDescription())
                .image_url(ele.getCourse().getImage_url())
                .name(ele.getName())
                .course_name(ele.getCourse().getName())
                .art_age_name(ele.getCourse().getArtAges().getName())
                .art_level_name(ele.getCourse().getArtLevels().getName())
                .art_type_name(ele.getCourse().getArtTypes().getName())
                .price(ele.getCourse().getPrice())
                .registration_deadline(ele.getSemester().getStart_time())
                .num_of_section(ele.getCourse().getNum_of_section())
                .schedule(schedule)
                .build();
            allNotRegisterSemesterClassResponses.add(notRegisterSemesterClassResponse);
        });

        List<Classes> allClassResponses = new ArrayList<>();
        List<Classes> listClass = classRepository.findAll();

        // Danh sach dang ki giao vien duoc xep lop
        List<UserRegisterTeachSemester> allTeacherRegisterSuccessfullTeachSemesterResponses = new ArrayList<>();
        // Danh sach khoa hoc theo ki giao vien chua dang ki
        List<SemesterClass> allRegisterSuccessfullSemesterClass = new ArrayList<>();
        // Danh sach dang ki nhung khong duoc xep lop
        listClass.forEach(ele -> {
            if (allTeacherTeachSemesterResponses.contains(ele.getUserRegisterTeachSemester())){
                allTeacherRegisterSuccessfullTeachSemesterResponses.add(ele.getUserRegisterTeachSemester());
                allRegisterSuccessfullSemesterClass.add(ele.getUserRegisterTeachSemester().getSemesterClass());
                allClassResponses.add(ele);
            }
        });

        List<GetCourseTeacherResponse> allRegisterSuccessfullSemesterClassResponses = new ArrayList<>();
        allRegisterSuccessfullSemesterClass.forEach(ele -> {
            schedule = "";
            scheduleRepository.findAll().forEach(schedule_item -> {
                if (schedule_item.getSemesterClass().getId().compareTo(ele.getId()) == 0){
                    if (schedule.equals("")){
                        schedule = schedule + "Thứ " + schedule_item.getDate_of_week() + " (" + schedule_item.getLessonTime().getStart_time().toString() + " - " + schedule_item.getLessonTime().getEnd_time().toString() +")";
                    }
                    else {
                        schedule = schedule + ", Thứ " + schedule_item.getDate_of_week() + " (" + schedule_item.getLessonTime().getStart_time().toString() + " - " + schedule_item.getLessonTime().getEnd_time().toString() +")";
                    }
                }
            });
            GetCourseTeacherResponse registerSuccessfullSemesterClassResponse = GetCourseTeacherResponse.builder()
                .course_id(ele.getCourse().getId())
                .semster_class_id(ele.getId())
                .id(ele.getId())
                .semester_name(ele.getSemester().getName())
                .description(ele.getCourse().getDescription())
                .image_url(ele.getCourse().getImage_url())
                .name(ele.getName())
                .course_name(ele.getCourse().getName())
                .art_age_name(ele.getCourse().getArtAges().getName())
                .art_level_name(ele.getCourse().getArtLevels().getName())
                .art_type_name(ele.getCourse().getArtTypes().getName())
                .price(ele.getCourse().getPrice())
                .registration_deadline(ele.getSemester().getStart_time())
                .num_of_section(ele.getCourse().getNum_of_section())
                .schedule(schedule)
                .build();

            allRegisterSuccessfullSemesterClassResponses.add(registerSuccessfullSemesterClassResponse);
        });

        List<GetCourseTeacherResponse> allRegisterNotScheduleClassSemesterClassResponses = new ArrayList<>();
        allRegisteredSemesterClassResponses.forEach(ele -> {
            if (!allRegisterSuccessfullSemesterClass.contains(ele)){
                schedule = "";
                scheduleRepository.findAll().forEach(schedule_item -> {
                    if (schedule_item.getSemesterClass().getId().compareTo(ele.getId()) == 0){
                        if (schedule.equals("")){
                            schedule = schedule + "Thứ " + schedule_item.getDate_of_week() + " (" + schedule_item.getLessonTime().getStart_time().toString() + " - " + schedule_item.getLessonTime().getEnd_time().toString() +")";
                        }
                        else {
                            schedule = schedule + ", Thứ " + schedule_item.getDate_of_week() + " (" + schedule_item.getLessonTime().getStart_time().toString() + " - " + schedule_item.getLessonTime().getEnd_time().toString() +")";
                        }
                    }
                });
                GetCourseTeacherResponse registerNotScheduleClassSemesterClassResponse = GetCourseTeacherResponse.builder()
                    .course_id(ele.getCourse().getId())
                    .semster_class_id(ele.getId())
                    .id(ele.getId())
                    .semester_name(ele.getSemester().getName())
                    .description(ele.getCourse().getDescription())
                    .image_url(ele.getCourse().getImage_url())
                    .name(ele.getName())
                    .course_name(ele.getCourse().getName())
                    .art_age_name(ele.getCourse().getArtAges().getName())
                    .art_level_name(ele.getCourse().getArtLevels().getName())
                    .art_type_name(ele.getCourse().getArtTypes().getName())
                    .price(ele.getCourse().getPrice())
                    .registration_deadline(ele.getSemester().getStart_time())
                    .num_of_section(ele.getCourse().getNum_of_section())
                    .schedule(schedule)
                    .build();
    
                allRegisterNotScheduleClassSemesterClassResponses.add(registerNotScheduleClassSemesterClassResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("not_register_courses", allNotRegisterSemesterClassResponses);
        response.put("register_successfull_courses", allRegisterSuccessfullSemesterClassResponses);
        response.put("register_not_schedule_courses", allRegisterNotScheduleClassSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetCourseResponse getCourseByName(String name) {
        Optional<Course> courseOpt = courseRepository.findByName2(name);
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Course.not_found");
        });

        return GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .create_time(course.getCreate_time())
                .update_time(course.getUpdate_time())
                .build();
    }

    @Override
    public GetCourseResponse getCourseById(UUID id){
        Optional<Course> courseOpt = courseRepository.findById2(id);
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Course.not_found");
        });

        return GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .create_time(course.getCreate_time())
                .update_time(course.getUpdate_time())
                .build();
    }

    @Override
    public UUID createCourse(CreateCourseRequest createCourseRequest) {
        if (courseRepository.existsByName(createCourseRequest.getName())) {
            throw new CourseAlreadyCreateException("exception.course.course_taken");
        }

        Optional<User> userOpt = userRepository.findById1(createCourseRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Optional<ArtAge> artAgeOpt = artAgeRepository.findById(createCourseRequest.getArt_age_id());
        ArtAge artAge = artAgeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtAge.not_found");
        });

        Optional<ArtType> artTypeOpt = artTypeRepository.findById(createCourseRequest.getArt_type_id());
        ArtType artType = artTypeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtType.not_found");
        });

        Optional<ArtLevel> artLevelOpt = artLevelRepository.findById(createCourseRequest.getArt_level_id());
        ArtLevel artLevel = artLevelOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtLevel.not_found");
        });


        Course savedCourse = Course.builder()
                .name(createCourseRequest.getName())
                .description(createCourseRequest.getDescription())
                .num_of_section(createCourseRequest.getNum_of_section())
                .image_url(createCourseRequest.getImage_url())
                .price(createCourseRequest.getPrice())
                .is_enabled(createCourseRequest.getIs_enabled())
                .user(user)
                .artAges(artAge)
                .artTypes(artType)
                .artLevels(artLevel)
                .build();
        courseRepository.save(savedCourse);

        return savedCourse.getId();
    }

    @Override
    public UUID removeCourseById(UUID id) {
        Optional<Course> courseOpt = courseRepository.findById1(id);
        courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Course.not_found");
        });
        courseRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateCourseById(UUID id, CreateCourseRequest createCourseRequest) {
        Optional<Course> courseOpt = courseRepository.findById1(id);
        Course updatedCourse = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Course.not_found");
        });

        Optional<User> userOpt = userRepository.findById1(createCourseRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Optional<ArtAge> artAgeOpt = artAgeRepository.findById(createCourseRequest.getArt_age_id());
        ArtAge artAge = artAgeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtAge.not_found");
        });

        Optional<ArtType> artTypeOpt = artTypeRepository.findById(createCourseRequest.getArt_type_id());
        ArtType artType = artTypeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtType.not_found");
        });

        Optional<ArtLevel> artLevelOpt = artLevelRepository.findById(createCourseRequest.getArt_level_id());
        ArtLevel artLevel = artLevelOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtLevel.not_found");
        });
        updatedCourse.setName(createCourseRequest.getName());
        updatedCourse.setDescription(createCourseRequest.getDescription());
        updatedCourse.setNum_of_section(createCourseRequest.getNum_of_section());
        updatedCourse.setImage_url(createCourseRequest.getImage_url());
        updatedCourse.setPrice(createCourseRequest.getPrice());
        updatedCourse.setUser(user);
        updatedCourse.setArtAges(artAge);
        updatedCourse.setArtTypes(artType);
        updatedCourse.setArtLevels(artLevel);
        courseRepository.save(updatedCourse);

        return updatedCourse.getId();
    }
}
