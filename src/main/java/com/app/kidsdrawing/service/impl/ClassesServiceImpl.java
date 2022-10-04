package com.app.kidsdrawing.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateClassRequest;
import com.app.kidsdrawing.dto.GetArtAgeResponse;
import com.app.kidsdrawing.dto.GetArtLevelResponse;
import com.app.kidsdrawing.dto.GetArtTypeResponse;
import com.app.kidsdrawing.dto.GetClassResponse;
import com.app.kidsdrawing.dto.GetClassesParentResponse;
import com.app.kidsdrawing.dto.GetClassesStudentResponse;
import com.app.kidsdrawing.dto.GetCourseResponse;
import com.app.kidsdrawing.dto.GetInfoClassTeacherResponse;
import com.app.kidsdrawing.dto.GetScheduleResponse;
import com.app.kidsdrawing.dto.GetSemesterResponse;
import com.app.kidsdrawing.dto.GetStudentResponse;
import com.app.kidsdrawing.dto.GetUserResponse;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.entity.LessonTime;
import com.app.kidsdrawing.entity.Semester;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.UserRegisterTeachSemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinSemesterRepository;
import com.app.kidsdrawing.repository.ClassHasRegisterJoinSemesterClassRepository;
import com.app.kidsdrawing.repository.ClassesRepository;
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

    private final ClassesRepository classRepository;
    private final UserRegisterTeachSemesterRepository userRegisterTeachSemesterRepository;
    private final UserRepository userRepository;
    private final UserRegisterJoinSemesterRepository userRegisterJoinSemesterRepository;
    private final SemesterClassRepository semesterClassRepository;
    private final ScheduleRepository scheduleRepository;
    private final SemesterRepository semesterRepository;
    private final ClassHasRegisterJoinSemesterClassRepository classHasRegisterJoinSemesterClassRepository;

    private static int total_section_count = 0;
    private static int week_count = 0;
    private static String schedule = "";

    @Override
    public ResponseEntity<Map<String, Object>> getAllClass() {
        List<GetClassResponse> allClassResponses = new ArrayList<>();
        List<Classes> listClass = classRepository.findAll();
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
    public ResponseEntity<Map<String, Object>> getInforDetailOfClassByTeacherId(UUID id) {
        List<GetInfoClassTeacherResponse> allInfoClassTeacherDoingResponses = new ArrayList<>();
        List<GetInfoClassTeacherResponse> allInfoClassTeacherDoneResponses = new ArrayList<>();
        List<Map<String, List<Map<String, List<List<LocalDateTime>>>>>> allScheduleTime = new ArrayList<>();
        List<List<GetStudentResponse>> allStudentDoneResponses = new ArrayList<>();
        List<List<GetStudentResponse>> allStudentDoingResponses = new ArrayList<>();
        List<Classes> listClass = classRepository.findAll();
        LocalDateTime time_now = LocalDateTime.now();
        listClass.forEach(ele -> {
            schedule = "";
            // LocalDateTime res = getEndSectionOfClass(ele.getId());

            scheduleRepository.findAll().forEach(schedule_ele -> {
                if (ele.getUserRegisterTeachSemester().getSemesterClass().getSchedules().contains(schedule_ele)) {
                    if (schedule.equals("")) {
                        schedule = schedule + "Thứ " + schedule_ele.getDate_of_week() + " ("
                                + schedule_ele.getLessonTime().getStart_time().toString() + " - "
                                + schedule_ele.getLessonTime().getEnd_time().toString() + ")";
                    } else {
                        schedule = schedule + ", Thứ " + schedule_ele.getDate_of_week() + " ("
                                + schedule_ele.getLessonTime().getStart_time().toString() + " - "
                                + schedule_ele.getLessonTime().getEnd_time().toString() + ")";
                    }
                }
            });
            if (ele.getUserRegisterTeachSemester().getTeacher().getId().compareTo(id) == 0) {
                if (time_now
                        .isBefore(ele.getUserRegisterTeachSemester().getSemesterClass().getSemester().getEnd_time())) {
                    List<Map<String, List<List<LocalDateTime>>>> schedule_time = getScheduleDetailOfClass(ele.getId());
                    Map<String, List<Map<String, List<List<LocalDateTime>>>>> x = new HashMap<>();
                    x.put(ele.getName(), schedule_time);
                    allScheduleTime.add(x);
                    List<ClassHasRegisterJoinSemesterClass> listClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository
                            .findByClassesId2(ele.getId());
                    GetInfoClassTeacherResponse infoClassTeacherResponse = GetInfoClassTeacherResponse.builder()
                            .id(ele.getId())
                            .user_register_teach_semester(ele.getUserRegisterTeachSemester().getId())
                            .security_code(ele.getSecurity_code())
                            .name(ele.getName())
                            .link_url(ele.getLink_meeting())
                            .course_id(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getId())
                            .course_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getName())
                            .semester_name(
                                    ele.getUserRegisterTeachSemester().getSemesterClass().getSemester().getName())
                            .semster_class_id(ele.getUserRegisterTeachSemester().getSemesterClass().getId())
                            .total_student(listClassHasRegisterJoinSemesterClass.size())
                            .num_of_section(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse()
                                    .getNum_of_section())
                            .art_age_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getArtAges()
                                    .getName())
                            .art_type_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse()
                                    .getArtTypes().getName())
                            .art_level_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse()
                                    .getArtLevels().getName())
                            .schedule(schedule)
                            .build();
                    allInfoClassTeacherDoingResponses.add(infoClassTeacherResponse);

                    List<GetStudentResponse> listStudents = new ArrayList<>();
                    listClassHasRegisterJoinSemesterClass.forEach(content -> {
                        String parent_name = content.getUserRegisterJoinSemester().getStudent().getParent()
                                .getUsername();
                        GetStudentResponse student = GetStudentResponse.builder()
                                .id(content.getUserRegisterJoinSemester().getStudent().getId())
                                .username(content.getUserRegisterJoinSemester().getStudent().getUsername())
                                .email(content.getUserRegisterJoinSemester().getStudent().getEmail())
                                .firstName(content.getUserRegisterJoinSemester().getStudent().getFirstName())
                                .lastName(content.getUserRegisterJoinSemester().getStudent().getLastName())
                                .dateOfBirth(content.getUserRegisterJoinSemester().getStudent().getDateOfBirth())
                                .profile_image_url(
                                        content.getUserRegisterJoinSemester().getStudent().getProfileImageUrl())
                                .sex(content.getUserRegisterJoinSemester().getStudent().getSex())
                                .phone(content.getUserRegisterJoinSemester().getStudent().getPhone())
                                .address(content.getUserRegisterJoinSemester().getStudent().getAddress())
                                .parent(parent_name)
                                .createTime(content.getUserRegisterJoinSemester().getStudent().getCreateTime())
                                .build();
                        listStudents.add(student);
                    });
                    allStudentDoingResponses.add(listStudents);
                }

                else {
                    List<ClassHasRegisterJoinSemesterClass> listClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository
                            .findByClassesId2(ele.getId());
                    GetInfoClassTeacherResponse infoClassTeacherResponse = GetInfoClassTeacherResponse.builder()
                            .id(ele.getId())
                            .user_register_teach_semester(ele.getUserRegisterTeachSemester().getId())
                            .security_code(ele.getSecurity_code())
                            .name(ele.getName())
                            .link_url(ele.getLink_meeting())
                            .course_id(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getId())
                            .course_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getName())
                            .semester_name(
                                    ele.getUserRegisterTeachSemester().getSemesterClass().getSemester().getName())
                            .semster_class_id(ele.getUserRegisterTeachSemester().getSemesterClass().getId())
                            .total_student(listClassHasRegisterJoinSemesterClass.size())
                            .num_of_section(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse()
                                    .getNum_of_section())
                            .art_age_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse().getArtAges()
                                    .getName())
                            .art_type_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse()
                                    .getArtTypes().getName())
                            .art_level_name(ele.getUserRegisterTeachSemester().getSemesterClass().getCourse()
                                    .getArtLevels().getName())
                            .schedule(schedule)
                            .build();
                    allInfoClassTeacherDoneResponses.add(infoClassTeacherResponse);

                    List<GetStudentResponse> listStudentDones = new ArrayList<>();
                    listClassHasRegisterJoinSemesterClass.forEach(content -> {
                        String parent_name = content.getUserRegisterJoinSemester().getStudent().getParent()
                                .getUsername();
                        GetStudentResponse student = GetStudentResponse.builder()
                                .id(content.getUserRegisterJoinSemester().getStudent().getId())
                                .username(content.getUserRegisterJoinSemester().getStudent().getUsername())
                                .email(content.getUserRegisterJoinSemester().getStudent().getEmail())
                                .firstName(content.getUserRegisterJoinSemester().getStudent().getFirstName())
                                .lastName(content.getUserRegisterJoinSemester().getStudent().getLastName())
                                .dateOfBirth(content.getUserRegisterJoinSemester().getStudent().getDateOfBirth())
                                .profile_image_url(
                                        content.getUserRegisterJoinSemester().getStudent().getProfileImageUrl())
                                .sex(content.getUserRegisterJoinSemester().getStudent().getSex())
                                .phone(content.getUserRegisterJoinSemester().getStudent().getPhone())
                                .address(content.getUserRegisterJoinSemester().getStudent().getAddress())
                                .parent(parent_name)
                                .createTime(content.getUserRegisterJoinSemester().getStudent().getCreateTime())
                                .build();
                        listStudentDones.add(student);
                    });
                    allStudentDoneResponses.add(listStudentDones);
                }

            }
        });
        Map<String, Object> response = new HashMap<>();
        response.put("classes_doing", allInfoClassTeacherDoingResponses);
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
        List<Classes> listClass = classRepository.findAll();
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
    public ResponseEntity<Map<String, Object>> getClassesForStudentId(UUID id) {
        List<GetClassesParentResponse> allClassDoingResponses = new ArrayList<>();
        List<GetClassesParentResponse> allClassDoneResponses = new ArrayList<>();

        Optional<User> userOpt = userRepository.findById1(id);
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        LocalDateTime time_now = LocalDateTime.now();
        List<UserRegisterJoinSemester> allUserRegisterJoinSemesters = userRegisterJoinSemesterRepository
                .findByStudentId(id);

        List<ClassHasRegisterJoinSemesterClass> allClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository
                .findAll();

        allClassHasRegisterJoinSemesterClass.forEach(class_has_join_semester_class -> {
            if (allUserRegisterJoinSemesters.contains(class_has_join_semester_class.getUserRegisterJoinSemester())) {
                /*
                 * LocalDateTime res = getEndSectionOfClass(
                 * class_has_join_semester_class.getUserRegisterJoinSemester().
                 * getClassHasRegisterJoinSemesterClass().getClasses().getId());
                 */
                if (time_now.isAfter(class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                        .getSemester().getEnd_time()) == false) {
                    GetClassesParentResponse classResponse = GetClassesParentResponse.builder()
                            .id(class_has_join_semester_class.getClasses().getId())
                            .student_id(id)
                            .semester_id(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                            .getSemester().getId())
                            .semester_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                    .getSemesterClass().getSemester().getName())
                            .user_register_join_semester_id(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getId())
                            .course_id(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                            .getCourse().getId())
                            .course_name(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                            .getCourse().getName())
                            .student_name(user.getFirstName() + " " + user.getLastName())
                            .semester_class_id(class_has_join_semester_class.getUserRegisterJoinSemester()
                                    .getSemesterClass().getId())
                            .semester_class_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                    .getSemesterClass().getName())
                            .link_url(class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                    .getCourse().getImage_url())
                            .teacher_id(class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                    .getTeacher().getId())
                            .teacher_name(class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                    .getTeacher().getFirstName()
                                    + " "
                                    + class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                            .getTeacher().getLastName())
                            .art_age_id(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                            .getCourse().getArtAges().getId())
                            .art_age_name(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                            .getCourse().getArtAges().getName())
                            .art_level_id(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                            .getCourse().getArtLevels().getId())
                            .art_level_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                    .getSemesterClass().getCourse().getArtLevels().getName())
                            .art_type_id(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                            .getCourse().getArtTypes().getId())
                            .art_type_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                    .getSemesterClass().getCourse().getArtTypes().getName())
                            .total_section(class_has_join_semester_class.getClasses().getSections().size())
                            .total_student(class_has_join_semester_class.getClasses()
                                    .getClassHasRegisterJoinSemesterClasses().size())
                            .creator_id(class_has_join_semester_class.getClasses().getUser().getId())
                            .user_register_teach_semester(
                                    class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                            .getId())
                            .security_code(class_has_join_semester_class.getClasses().getSecurity_code())
                            .name(class_has_join_semester_class.getClasses().getName())
                            .create_time(class_has_join_semester_class.getClasses().getCreate_time())
                            .update_time(class_has_join_semester_class.getClasses().getUpdate_time())
                            .build();
                    allClassDoingResponses.add(classResponse);
                } else {
                    GetClassesParentResponse classResponse = GetClassesParentResponse.builder()
                            .id(class_has_join_semester_class.getClasses().getId())
                            .student_id(id)
                            .semester_class_id(class_has_join_semester_class.getUserRegisterJoinSemester()
                                    .getSemesterClass().getId())
                            .semester_class_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                    .getSemesterClass().getName())
                            .student_name(user.getFirstName() + " " + user.getLastName())
                            .semester_id(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                            .getSemester().getId())
                            .semester_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                    .getSemesterClass().getSemester().getName())
                            .course_id(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                            .getCourse().getId())
                            .user_register_join_semester_id(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getId())
                            .course_name(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                            .getCourse().getName())
                            .link_url(class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                    .getCourse().getImage_url())
                            .teacher_id(class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                    .getTeacher().getId())
                            .teacher_name(class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                    .getTeacher().getFirstName()
                                    + " "
                                    + class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                            .getTeacher().getLastName())
                            .art_age_id(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                            .getCourse().getArtAges().getId())
                            .art_age_name(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                            .getCourse().getArtAges().getName())
                            .art_level_id(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                            .getCourse().getArtLevels().getId())
                            .art_level_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                    .getSemesterClass().getCourse().getArtLevels().getName())
                            .art_type_id(
                                    class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                            .getCourse().getArtTypes().getId())
                            .art_type_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                    .getSemesterClass().getCourse().getArtTypes().getName())
                            .total_section(class_has_join_semester_class.getClasses().getSections().size())
                            .total_student(class_has_join_semester_class.getClasses()
                                    .getClassHasRegisterJoinSemesterClasses().size())
                            .creator_id(class_has_join_semester_class.getClasses().getUser().getId())
                            .user_register_teach_semester(
                                    class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                            .getId())
                            .security_code(class_has_join_semester_class.getClasses().getSecurity_code())
                            .name(class_has_join_semester_class.getClasses().getName())
                            .create_time(class_has_join_semester_class.getClasses().getCreate_time())
                            .update_time(class_has_join_semester_class.getClasses().getUpdate_time())
                            .build();
                    allClassDoneResponses.add(classResponse);
                }
            }
        });
        Map<String, Object> response = new HashMap<>();
        response.put("classes_doing", allClassDoingResponses);
        response.put("classes_done", allClassDoneResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getClassesStudentForParentId(UUID parent_id) {
        List<User> pageUser = userRepository.findByParentId(parent_id);
        List<GetClassesParentResponse> allClassDoingResponses = new ArrayList<>();
        List<GetClassesParentResponse> allClassDoneResponses = new ArrayList<>();
        LocalDateTime time_now = LocalDateTime.now();
        pageUser.forEach(student -> {
            List<UserRegisterJoinSemester> allUserRegisterJoinSemesters = userRegisterJoinSemesterRepository
                    .findByStudentId(student.getId());

            List<ClassHasRegisterJoinSemesterClass> allClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository
                    .findAll();

            allClassHasRegisterJoinSemesterClass.forEach(class_has_join_semester_class -> {
                if (allUserRegisterJoinSemesters
                        .contains(class_has_join_semester_class.getUserRegisterJoinSemester())) {
                    LocalDateTime res = getEndSectionOfClass(
                            class_has_join_semester_class.getClasses().getId());
                    if (time_now.isAfter(res) == false) {
                        GetClassesParentResponse classResponse = GetClassesParentResponse.builder()
                                .id(class_has_join_semester_class.getClasses().getId())
                                .student_id(student.getId())
                                .user_register_join_semester_id(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getId())
                                .semester_id(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                                .getSemester().getId())
                                .semester_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                        .getSemesterClass().getSemester().getName())
                                .course_id(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                                .getCourse().getId())
                                .course_name(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                                .getCourse().getName())
                                .student_name(student.getFirstName() + " " + student.getLastName())
                                .semester_class_id(class_has_join_semester_class.getUserRegisterJoinSemester()
                                        .getSemesterClass().getId())
                                .semester_class_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                        .getSemesterClass().getName())
                                .link_url(class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                        .getCourse().getImage_url())
                                .teacher_id(class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                        .getTeacher().getId())
                                .teacher_name(class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                        .getTeacher().getFirstName()
                                        + " "
                                        + class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                                .getTeacher().getLastName())
                                .art_age_id(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                                .getCourse().getArtAges().getId())
                                .art_age_name(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                                .getCourse().getArtAges().getName())
                                .art_level_id(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                                .getCourse().getArtLevels().getId())
                                .art_level_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                        .getSemesterClass().getCourse().getArtLevels().getName())
                                .art_type_id(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                                .getCourse().getArtTypes().getId())
                                .art_type_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                        .getSemesterClass().getCourse().getArtTypes().getName())
                                .total_section(class_has_join_semester_class.getClasses().getSections().size())
                                .total_student(class_has_join_semester_class.getClasses()
                                        .getClassHasRegisterJoinSemesterClasses().size())
                                .creator_id(class_has_join_semester_class.getClasses().getUser().getId())
                                .user_register_teach_semester(
                                        class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                                .getId())
                                .security_code(class_has_join_semester_class.getClasses().getSecurity_code())
                                .name(class_has_join_semester_class.getClasses().getName())
                                .create_time(class_has_join_semester_class.getClasses().getCreate_time())
                                .update_time(class_has_join_semester_class.getClasses().getUpdate_time())
                                .build();
                        allClassDoingResponses.add(classResponse);
                    } else {
                        GetClassesParentResponse classResponse = GetClassesParentResponse.builder()
                                .id(class_has_join_semester_class.getClasses().getId())
                                .student_id(student.getId())
                                .user_register_join_semester_id(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getId())
                                .semester_class_id(class_has_join_semester_class.getUserRegisterJoinSemester()
                                        .getSemesterClass().getId())
                                .semester_class_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                        .getSemesterClass().getName())
                                .student_name(student.getFirstName() + " " + student.getLastName())
                                .semester_id(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                                .getSemester().getId())
                                .semester_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                        .getSemesterClass().getSemester().getName())
                                .course_id(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                                .getCourse().getId())
                                .course_name(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                                .getCourse().getName())
                                .link_url(class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                        .getCourse().getImage_url())
                                .teacher_id(class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                        .getTeacher().getId())
                                .teacher_name(class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                        .getTeacher().getFirstName()
                                        + " "
                                        + class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                                .getTeacher().getLastName())
                                .art_age_id(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                                .getCourse().getArtAges().getId())
                                .art_age_name(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                                .getCourse().getArtAges().getName())
                                .art_level_id(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                                .getCourse().getArtLevels().getId())
                                .art_level_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                        .getSemesterClass().getCourse().getArtLevels().getName())
                                .art_type_id(
                                        class_has_join_semester_class.getUserRegisterJoinSemester().getSemesterClass()
                                                .getCourse().getArtTypes().getId())
                                .art_type_name(class_has_join_semester_class.getUserRegisterJoinSemester()
                                        .getSemesterClass().getCourse().getArtTypes().getName())
                                .total_section(class_has_join_semester_class.getClasses().getSections().size())
                                .total_student(class_has_join_semester_class.getClasses()
                                        .getClassHasRegisterJoinSemesterClasses().size())
                                .creator_id(class_has_join_semester_class.getClasses().getUser().getId())
                                .user_register_teach_semester(
                                        class_has_join_semester_class.getClasses().getUserRegisterTeachSemester()
                                                .getId())
                                .security_code(class_has_join_semester_class.getClasses().getSecurity_code())
                                .name(class_has_join_semester_class.getClasses().getName())
                                .create_time(class_has_join_semester_class.getClasses().getCreate_time())
                                .update_time(class_has_join_semester_class.getClasses().getUpdate_time())
                                .build();
                        allClassDoneResponses.add(classResponse);
                    }
                }
            });
        });
        Map<String, Object> response = new HashMap<>();
        response.put("classes_doing", allClassDoingResponses);
        response.put("classes_done", allClassDoneResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getClassesStudentForStudentId(UUID id) {
        List<GetClassesStudentResponse> allClassResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> listUserRegisterJoinSemester = userRegisterJoinSemesterRepository
                .findByStudentId(id);
        listUserRegisterJoinSemester.forEach(user_register_join_semester -> {
            List<ClassHasRegisterJoinSemesterClass> classHasRegisterJoinSemesterClassOpt = classHasRegisterJoinSemesterClassRepository
                    .findByUserRegisterJoinSemesterId2(user_register_join_semester.getId());
            classHasRegisterJoinSemesterClassOpt.forEach(ele -> {
                GetClassesStudentResponse classResponse = GetClassesStudentResponse.builder()
                        .id(ele.getClasses().getId())
                        .user_register_join_semester_id(
                                ele.getUserRegisterJoinSemester().getId())
                        .link_url(ele.getUserRegisterJoinSemester().getSemesterClass()
                                .getCourse().getImage_url())
                        .user_register_join_semester_id(
                                ele.getUserRegisterJoinSemester().getId())
                        .teacher_id(ele.getClasses().getUserRegisterTeachSemester()
                                .getTeacher().getId())
                        .teacher_name(ele.getClasses().getUserRegisterTeachSemester()
                                .getTeacher().getFirstName()
                                + " "
                                + ele.getClasses().getUserRegisterTeachSemester().getTeacher()
                                        .getLastName())
                        .art_age_id(ele.getUserRegisterJoinSemester().getSemesterClass()
                                .getCourse().getArtAges().getId())
                        .art_age_name(ele.getUserRegisterJoinSemester().getSemesterClass()
                                .getCourse().getArtAges().getName())
                        .art_level_id(ele.getUserRegisterJoinSemester().getSemesterClass()
                                .getCourse().getArtLevels().getId())
                        .art_level_name(ele.getUserRegisterJoinSemester().getSemesterClass()
                                .getCourse().getArtLevels().getName())
                        .art_type_id(ele.getUserRegisterJoinSemester().getSemesterClass()
                                .getCourse().getArtTypes().getId())
                        .art_type_name(ele.getUserRegisterJoinSemester().getSemesterClass()
                                .getCourse().getArtTypes().getName())
                        .total_section(ele.getClasses().getSections().size())
                        .total_student(ele.getClasses()
                                .getClassHasRegisterJoinSemesterClasses().size())
                        .creator_id(ele.getClasses().getUser().getId())
                        .user_register_teach_semester(
                                ele.getClasses().getUserRegisterTeachSemester().getId())
                        .security_code(ele.getClasses().getSecurity_code())
                        .name(ele.getClasses().getName())
                        .create_time(ele.getClasses().getCreate_time())
                        .update_time(ele.getClasses().getUpdate_time())
                        .build();
                allClassResponses.add(classResponse);
            });
        });
        Map<String, Object> response = new HashMap<>();
        response.put("classes", allClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getInforScheduleAllClass() {
        LocalDateTime time_now = LocalDateTime.now();
        List<Semester> allSemesters = semesterRepository.findAll();
        List<Classes> allClassDoing = new ArrayList<>();
        allSemesters.forEach(semester -> {
            if (semester.getEnd_time().isAfter(time_now)) {
                semesterClassRepository.findBySemesterId(semester.getId()).forEach(semester_class -> {
                    userRegisterTeachSemesterRepository.findBySemesterClassId(semester_class.getId())
                            .forEach(user_register_teache_semester -> {
                                if (classRepository.existsByUserRegisterTeachSemesterId(
                                        user_register_teache_semester.getId())) {
                                    List<Classes> classesOtp = classRepository
                                            .findByUserRegisterTeachSemesterId(
                                                    user_register_teache_semester.getId());
                                    classesOtp.forEach(classes_ele -> {
                                        allClassDoing.add(classes_ele);
                                    });
                                }
                            });
                });
            }
        });

        List<Map<String, List<Map<String, List<List<LocalDateTime>>>>>> allScheduleForAllClass = new ArrayList<>();
        allClassDoing.forEach(class_ele -> {
            Map<String, List<Map<String, List<List<LocalDateTime>>>>> res = new HashMap<>();
            res.put(class_ele.getName(), getScheduleDetailOfClass(class_ele.getId()));
            allScheduleForAllClass.add(res);
        });
        Map<String, Object> response = new HashMap<>();
        response.put("schedules", allScheduleForAllClass);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public LocalDateTime getEndSectionOfClass(UUID id) {
        Optional<Classes> classOpt = classRepository.findById1(id);
        Classes classes = classOpt.orElseThrow(() -> {
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
        if (total_section % semesterCouse.getSchedules().size() != 0) {
            total_week++;
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
            // start_time = start_time.plusWeeks(1);
            week_count++;
        }

        return start_time;
    }

    public List<Map<String, List<List<LocalDateTime>>>> getScheduleDetailOfClass(UUID id) {
        Optional<Classes> classOpt = classRepository.findById2(id);
        Classes classes = classOpt.orElseThrow(() -> {
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
                    .lesson_time(schedule_item.getLessonTime().getStart_time().toString() + " - "
                            + schedule_item.getLessonTime().getEnd_time().toString())
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
        if (total_section % semesterCouse.getSchedules().size() != 0) {
            total_week++;
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
            // start_time = start_time.plusWeeks(1);
            week_count++;
        }

        return allCalendarForSemesterClass;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getInforScheduleChild(UUID child_id) {
        List<UserRegisterJoinSemester> userRegisterJoinSemester = userRegisterJoinSemesterRepository
                .findByStudentId(child_id);
        List<Map<String, List<Map<String, List<List<LocalDateTime>>>>>> allCalendarForChild = new ArrayList<>();
        LocalDateTime time_now = LocalDateTime.now();
        userRegisterJoinSemester.forEach(user_register_join_semester -> {
            Optional<SemesterClass> semester_classOpt = semesterClassRepository
                    .findById(user_register_join_semester.getSemesterClass().getId());
            SemesterClass semesterCouse = semester_classOpt.orElseThrow(() -> {
                throw new EntityNotFoundException("exception.semester_class.not_found");
            });

            if (semesterCouse.getSemester().getEnd_time().isAfter(time_now)) {
                List<GetScheduleResponse> allScheduleResponses = new ArrayList<>();
                semesterCouse.getSchedules().forEach(schedule_item -> {
                    GetScheduleResponse scheduleResponse = GetScheduleResponse.builder()
                            .id(schedule_item.getId())
                            .lesson_time(schedule_item.getLessonTime().getStart_time().toString() + " - "
                                    + schedule_item.getLessonTime().getEnd_time().toString())
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
                if (total_section % semesterCouse.getSchedules().size() != 0) {
                    total_week++;
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
                    // start_time = start_time.plusWeeks(1);
                    week_count++;
                }
                Map<String, List<Map<String, List<List<LocalDateTime>>>>> schedule_class = new HashMap<>();
                String name = user_register_join_semester.getSemesterClass().getName();
                schedule_class.put(name, allCalendarForSemesterClass);
                allCalendarForChild.add(schedule_class);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("schedules", allCalendarForChild);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getInforScheduleAllChild(UUID parent_id) {
        List<User> listChilds = userRepository.findByParentId(parent_id);
        List<Map<String, List<Map<String, List<Map<String, List<List<LocalDateTime>>>>>>>> allCalendarForAllChild = new ArrayList<>();
        listChilds.forEach(child -> {
            List<UserRegisterJoinSemester> userRegisterJoinSemester = userRegisterJoinSemesterRepository
                    .findByStudentId(child.getId());
            List<Map<String, List<Map<String, List<List<LocalDateTime>>>>>> allCalendarForChild = new ArrayList<>();
            LocalDateTime time_now = LocalDateTime.now();
            userRegisterJoinSemester.forEach(user_register_join_semester -> {
                Optional<SemesterClass> semester_classOpt = semesterClassRepository
                        .findById(user_register_join_semester.getSemesterClass().getId());
                SemesterClass semesterCouse = semester_classOpt.orElseThrow(() -> {
                    throw new EntityNotFoundException("exception.semester_class.not_found");
                });

                if (semesterCouse.getSemester().getEnd_time().isAfter(time_now)) {
                    List<GetScheduleResponse> allScheduleResponses = new ArrayList<>();
                    semesterCouse.getSchedules().forEach(schedule_item -> {
                        GetScheduleResponse scheduleResponse = GetScheduleResponse.builder()
                                .id(schedule_item.getId())
                                .lesson_time(schedule_item.getLessonTime().getStart_time().toString() + " - "
                                        + schedule_item.getLessonTime().getEnd_time().toString())
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
                    if (total_section % semesterCouse.getSchedules().size() != 0) {
                        total_week++;
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
                        // start_time = start_time.plusWeeks(1);
                        week_count++;
                    }
                    Map<String, List<Map<String, List<List<LocalDateTime>>>>> schedule_class = new HashMap<>();
                    schedule_class.put(
                            user_register_join_semester.getSemesterClass().getName(),
                            allCalendarForSemesterClass);
                    allCalendarForChild.add(schedule_class);
                }
            });
            Map<String, List<Map<String, List<Map<String, List<List<LocalDateTime>>>>>>> schedule_child = new HashMap<>();
            schedule_child.put(child.getUsername(), allCalendarForChild);
            allCalendarForAllChild.add(schedule_child);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("schedules", allCalendarForAllChild);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getInforDetailOfClass(UUID id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Classes> classOpt = classRepository.findById(id);
        Classes classes = classOpt.orElseThrow(() -> {
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
        List<ClassHasRegisterJoinSemesterClass> listClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository
                .findByClassesId2(classes.getId());
        listClassHasRegisterJoinSemesterClass.forEach(content -> {
            UUID parent_idx = content.getUserRegisterJoinSemester().getStudent().getParent().getId();
            String parent_namex = content.getUserRegisterJoinSemester().getStudent().getParent().getFirstName() + " "
                    + content.getUserRegisterJoinSemester().getStudent().getParent().getLastName();
            GetStudentResponse student = GetStudentResponse.builder()
                    .id(content.getUserRegisterJoinSemester().getStudent().getId())
                    .username(content.getUserRegisterJoinSemester().getStudent().getUsername())
                    .email(content.getUserRegisterJoinSemester().getStudent().getEmail())
                    .firstName(content.getUserRegisterJoinSemester().getStudent().getFirstName())
                    .lastName(content.getUserRegisterJoinSemester().getStudent().getLastName())
                    .dateOfBirth(content.getUserRegisterJoinSemester().getStudent().getDateOfBirth())
                    .profile_image_url(content.getUserRegisterJoinSemester().getStudent().getProfileImageUrl())
                    .sex(content.getUserRegisterJoinSemester().getStudent().getSex())
                    .phone(content.getUserRegisterJoinSemester().getStudent().getPhone())
                    .address(content.getUserRegisterJoinSemester().getStudent().getAddress())
                    .parent(parent_namex)
                    .parents(parent_idx)
                    .createTime(content.getUserRegisterJoinSemester().getStudent().getCreateTime())
                    .build();
            listStudents.add(student);
        });

        List<GetScheduleResponse> allScheduleResponses = new ArrayList<>();
        semesterCouse.getSchedules().forEach(schedule_item -> {
            GetScheduleResponse scheduleResponse = GetScheduleResponse.builder()
                    .id(schedule_item.getId())
                    .lesson_time(schedule_item.getLessonTime().getStart_time().toString() + " - "
                            + schedule_item.getLessonTime().getEnd_time().toString())
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
        if (total_section % semesterCouse.getSchedules().size() != 0) {
            total_week++;
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
            // start_time = start_time.plusWeeks(1);
            week_count++;
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
    public GetClassResponse getClassById(UUID id) {
        Optional<Classes> classOpt = classRepository.findById2(id);
        Classes classes = classOpt.orElseThrow(() -> {
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
    public UUID createClass(CreateClassRequest createClassRequest) {
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

        Optional<User> userOpt = userRepository.findById1(createClassRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Classes savedClass = Classes.builder()
                .user(user)
                .userRegisterTeachSemester(teacherTeachSemester)
                .security_code(createClassRequest.getSecurity_code())
                .name(createClassRequest.getName())
                .link_meeting("https://meet.jit.si/" + createClassRequest.getSecurity_code())
                .build();
        classRepository.save(savedClass);

        return savedClass.getId();
    }

    @Override
    public UUID removeClassById(UUID id) {
        Optional<Classes> classOpt = classRepository.findById1(id);
        classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        classRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateClassById(UUID id, CreateClassRequest createClassRequest) {
        Optional<Classes> classOpt = classRepository.findById1(id);
        Classes updatedClass = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        Optional<UserRegisterTeachSemester> teacherTeachSemesterOpt = userRegisterTeachSemesterRepository
                .findById(createClassRequest.getUser_register_teach_semester());
        UserRegisterTeachSemester teacherTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.teacher_teach_semester.not_found");
        });

        Optional<User> userOpt = userRepository.findById1(createClassRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        updatedClass.setName(createClassRequest.getName());
        updatedClass.setSecurity_code(createClassRequest.getSecurity_code());
        updatedClass.setLink_meeting("https://meet.jit.si/" + createClassRequest.getSecurity_code());
        updatedClass.setUser(user);
        updatedClass.setUserRegisterTeachSemester(teacherTeachSemester);

        return updatedClass.getId();
    }
}
