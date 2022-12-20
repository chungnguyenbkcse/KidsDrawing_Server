package com.app.kidsdrawing.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

import com.app.kidsdrawing.dto.CreateClassRequest;
import com.app.kidsdrawing.dto.GetArtAgeResponse;
import com.app.kidsdrawing.dto.GetArtLevelResponse;
import com.app.kidsdrawing.dto.GetArtTypeResponse;
import com.app.kidsdrawing.dto.GetChildInClassResponse;
import com.app.kidsdrawing.dto.GetClassResponse;
import com.app.kidsdrawing.dto.GetClassesParentResponse;
import com.app.kidsdrawing.dto.GetClassesStudentResponse;
import com.app.kidsdrawing.dto.GetCourseResponse;
import com.app.kidsdrawing.dto.GetInfoClassTeacherResponse;
import com.app.kidsdrawing.dto.GetScheduleResponse;
import com.app.kidsdrawing.dto.GetSemesterResponse;
import com.app.kidsdrawing.dto.GetStudentResponse;
import com.app.kidsdrawing.dto.GetUserResponse;
import com.app.kidsdrawing.entity.Teacher;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.Holiday;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.entity.LessonTime;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.entity.Student;
import com.app.kidsdrawing.exception.ArtAgeNotDeleteException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.TeacherRepository;
import com.app.kidsdrawing.repository.ClassHasRegisterJoinSemesterClassRepository;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.SemesterClassRepository;
import com.app.kidsdrawing.repository.StudentRepository;
import com.app.kidsdrawing.service.ClassesService;
import com.app.kidsdrawing.repository.HolidayRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ClassesServiceImpl implements ClassesService {

    private final ClassesRepository classRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final HolidayRepository holidayRepository;
    private final SemesterClassRepository semesterClassRepository;
    private final ClassHasRegisterJoinSemesterClassRepository classHasRegisterJoinSemesterClassRepository;

    private static int total_section_count = 0;
    private static Integer total = 0;
    private static int checked_section_next = 0;
    private static String schedule_section_next = "";
    private static int week_count = 0;
    private static String schedule = "";

    @Override
    public ResponseEntity<Map<String, Object>> getChildInClassByClassAndParent(Long class_id, Long parent_id) {
        List<GetChildInClassResponse> allChildInClassResponse = new ArrayList<>();
        List<Student> allChildForParent = studentRepository
                .findByParentId(parent_id);
        allChildForParent.forEach(ele -> {
            Optional<ClassHasRegisterJoinSemesterClass> classHasRegisterJoinSemesterClassOpt = classHasRegisterJoinSemesterClassRepository.findByClassesIdAndStudentId(class_id, ele.getId());
            if (classHasRegisterJoinSemesterClassOpt.isPresent()) {
                GetChildInClassResponse classResponse = GetChildInClassResponse.builder()
                    .student_id(ele.getId())
                    .student_name(ele.getUser().getUsername() + " - " + ele.getUser().getFirstName() + " " + ele.getUser().getLastName())
                    .dateOfBirth(ele.getDateOfBirth())
                    .sex(ele.getUser().getSex())
                    .build();
                allChildInClassResponse.add(classResponse);
            }
        });
        Map<String, Object> response = new HashMap<>();
        response.put("students", allChildInClassResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllClass() {
        List<GetClassResponse> allClassResponses = new ArrayList<>();
        List<Classes> listClass = classRepository.findAll();
        listClass.forEach(content -> {
            GetClassResponse classResponse = GetClassResponse.builder()
                    .id(content.getId())
                    .semester_class_id(content.getSemesterClass().getId())
                    .teacher_name(content.getTeacher().getUser().getUsername() + " - " + content.getTeacher().getUser().getFirstName() + " " + content.getTeacher().getUser().getLastName())
                    .course_id(content.getSemesterClass().getCourse().getId())
                    .course_name(content.getSemesterClass().getCourse().getName())
                    .semester_id(content.getSemesterClass().getSemester().getId())
                    .semester_name(content.getSemesterClass().getSemester().getName())
                    .total_student(content.getClassHasRegisterJoinSemesterClasses().size())
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
    public ResponseEntity<Map<String, Object>> getClassesForStudentId1(Long id) {
        List<GetInfoClassTeacherResponse> allInfoClassTeacherDoingResponses = new ArrayList<>();
        List<GetInfoClassTeacherResponse> allInfoClassTeacherDoneResponses = new ArrayList<>();
        List<Classes> listClass = classRepository.findAllByTeacher1(id);
        LocalDateTime time_now = LocalDateTime.now();
        listClass.forEach(ele -> {

            if (time_now
                    .isBefore(ele.getSemesterClass().getSemester().getEnd_time())) {       
                GetInfoClassTeacherResponse infoClassTeacherResponse = GetInfoClassTeacherResponse.builder()
                        .id(ele.getId())
                        .semester_class_id(ele.getSemesterClass().getId())
                        .security_code(ele.getSecurity_code())
                        .name(ele.getName())
                        .link_url(ele.getLink_meeting())
                        .course_id(ele.getSemesterClass().getCourse().getId())
                        .course_name(ele.getSemesterClass().getCourse().getName())
                        .semster_class_id(ele.getSemesterClass().getId())
                        .total_student(ele.getClassHasRegisterJoinSemesterClasses().size())
                        .num_of_section(ele.getSemesterClass().getCourse()
                                .getNum_of_section())
                        .build();
                allInfoClassTeacherDoingResponses.add(infoClassTeacherResponse);
            }

            else {
                GetInfoClassTeacherResponse infoClassTeacherResponse = GetInfoClassTeacherResponse.builder()
                        .id(ele.getId())
                        .semester_class_id(ele.getSemesterClass().getId())
                        .security_code(ele.getSecurity_code())
                        .name(ele.getName())
                        .link_url(ele.getLink_meeting())
                        .course_id(ele.getSemesterClass().getCourse().getId())
                        .course_name(ele.getSemesterClass().getCourse().getName())
                        .semster_class_id(ele.getSemesterClass().getId())
                        .total_student(ele.getClassHasRegisterJoinSemesterClasses().size())
                        .num_of_section(ele.getSemesterClass().getCourse()
                                .getNum_of_section())
                        .review_star(getReviewStarForClass(ele.getId()))
                        .build();
                allInfoClassTeacherDoneResponses.add(infoClassTeacherResponse);
            }
        });
        Map<String, Object> response = new HashMap<>();
        response.put("classes_doing", allInfoClassTeacherDoingResponses);
        response.put("classes_done", allInfoClassTeacherDoneResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    @Override
    public ResponseEntity<Map<String, Object>> getInforDetailOfClassByTeacherId(Long id) {
        List<GetInfoClassTeacherResponse> allInfoClassTeacherDoingResponses = new ArrayList<>();
        List<GetInfoClassTeacherResponse> allInfoClassTeacherDoneResponses = new ArrayList<>();
        List<Map<String, List<Map<String, List<List<LocalDateTime>>>>>> allScheduleTime = new ArrayList<>();
        List<List<GetStudentResponse>> allStudentDoneResponses = new ArrayList<>();
        List<List<GetStudentResponse>> allStudentDoingResponses = new ArrayList<>();
        List<Classes> listClass = classRepository.findAllByTeacher(id);
        LocalDateTime time_now = LocalDateTime.now();
        listClass.forEach(ele -> {
            schedule = "";
            // LocalDateTime res = getEndSectionOfClass(ele.getId());
            ele.getSemesterClass().getSchedules().forEach(schedule_ele -> {
                if (ele.getSemesterClass().getSchedules().contains(schedule_ele)) {
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

            if (time_now
                    .isBefore(ele.getSemesterClass().getSemester().getEnd_time())) {
                List<Map<String, List<List<LocalDateTime>>>> schedule_time = getScheduleDetailOfClass(ele);
                Map<String, List<Map<String, List<List<LocalDateTime>>>>> x = new HashMap<>();
                x.put(ele.getName(), schedule_time);
                allScheduleTime.add(x);
                GetInfoClassTeacherResponse infoClassTeacherResponse = GetInfoClassTeacherResponse.builder()
                        .id(ele.getId())
                        .semester_class_id(ele.getSemesterClass().getId())
                        .security_code(ele.getSecurity_code())
                        .name(ele.getName())
                        .link_url(ele.getLink_meeting())
                        .course_id(ele.getSemesterClass().getCourse().getId())
                        .course_name(ele.getSemesterClass().getCourse().getName())
                        .semester_name(
                                ele.getSemesterClass().getSemester().getName())
                        .semster_class_id(ele.getSemesterClass().getId())
                        .total_student(ele.getClassHasRegisterJoinSemesterClasses().size())
                        .num_of_section(ele.getSemesterClass().getCourse()
                                .getNum_of_section())
                        .art_age_name(ele.getSemesterClass().getCourse().getArtAges()
                                .getName())
                        .art_type_name(ele.getSemesterClass().getCourse()
                                .getArtTypes().getName())
                        .art_level_name(ele.getSemesterClass().getCourse()
                                .getArtLevels().getName())
                        .schedule(schedule)
                        .build();
                allInfoClassTeacherDoingResponses.add(infoClassTeacherResponse);

                List<GetStudentResponse> listStudents = new ArrayList<>();
                ele.getClassHasRegisterJoinSemesterClasses().forEach(content -> {
                    String parent_name = content.getStudent().getParent()
                            .getUser().getUsername() + " - " + content.getStudent().getParent().getUser().getFirstName() + " " + content.getStudent().getParent().getUser().getLastName();
                    GetStudentResponse student = GetStudentResponse.builder()
                            .id(content.getStudent().getId())
                            .username(content.getStudent().getUser().getUsername())
                            .email(content.getStudent().getUser().getEmail())
                            .firstName(content.getStudent().getUser().getFirstName())
                            .lastName(content.getStudent().getUser().getLastName())
                            .dateOfBirth(content.getStudent().getDateOfBirth())
                            .profile_image_url(
                                    content.getStudent().getUser().getProfileImageUrl())
                            .sex(content.getStudent().getUser().getSex())
                            .phone(content.getStudent().getPhone())
                            .address(content.getStudent().getUser().getAddress())
                            .parent(parent_name)
                            .createTime(content.getStudent().getUser().getCreateTime())
                            .build();
                    listStudents.add(student);
                });
                allStudentDoingResponses.add(listStudents);
            }

            else {
                GetInfoClassTeacherResponse infoClassTeacherResponse = GetInfoClassTeacherResponse.builder()
                        .id(ele.getId())
                        .semester_class_id(ele.getSemesterClass().getId())
                        .security_code(ele.getSecurity_code())
                        .name(ele.getName())
                        .link_url(ele.getLink_meeting())
                        .course_id(ele.getSemesterClass().getCourse().getId())
                        .course_name(ele.getSemesterClass().getCourse().getName())
                        .semester_name(
                                ele.getSemesterClass().getSemester().getName())
                        .semster_class_id(ele.getSemesterClass().getId())
                        .total_student(ele.getClassHasRegisterJoinSemesterClasses().size())
                        .num_of_section(ele.getSemesterClass().getCourse()
                                .getNum_of_section())
                        .art_age_name(ele.getSemesterClass().getCourse().getArtAges()
                                .getName())
                        .art_type_name(ele.getSemesterClass().getCourse()
                                .getArtTypes().getName())
                        .art_level_name(ele.getSemesterClass().getCourse()
                                .getArtLevels().getName())
                        .schedule(schedule)
                        .review_star(getReviewStarForClass(ele.getId()))
                        .build();
                allInfoClassTeacherDoneResponses.add(infoClassTeacherResponse);

                List<GetStudentResponse> listStudentDones = new ArrayList<>();
                ele.getClassHasRegisterJoinSemesterClasses().forEach(content -> {
                    String parent_name = content.getStudent().getParent()
                    .getUser().getUsername() + " - " +  content.getStudent().getParent().getUser().getFirstName() + " " + content.getStudent().getParent().getUser().getLastName();
                    GetStudentResponse student = GetStudentResponse.builder()
                            .id(content.getStudent().getId())
                            .username(content.getStudent().getUser().getUsername())
                            .email(content.getStudent().getUser().getEmail())
                            .firstName(content.getStudent().getUser().getFirstName())
                            .lastName(content.getStudent().getUser().getLastName())
                            .dateOfBirth(content.getStudent().getDateOfBirth())
                            .profile_image_url(
                                    content.getStudent().getUser().getProfileImageUrl())
                            .sex(content.getStudent().getUser().getSex())
                            .phone(content.getStudent().getPhone())
                            .address(content.getStudent().getUser().getAddress())
                            .parent(parent_name)
                            .createTime(content.getStudent().getUser().getCreateTime())
                            .build();
                    listStudentDones.add(student);
                });
                allStudentDoneResponses.add(listStudentDones);
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

    public float getReviewStarForClass(Long class_id) {
        List<ClassHasRegisterJoinSemesterClass> pageClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository.findByClassesId1(class_id);
        total = 0;
        pageClassHasRegisterJoinSemesterClass.forEach(ele -> {
            if (ele.getReview_star() != -1) {
                total = total + ele.getReview_star();
            }
        });
        return (float) (total/pageClassHasRegisterJoinSemesterClass.size());

    }

    @Override
    public ResponseEntity<Map<String, Object>> getListClassByTeacherId(Long id) {
        List<GetInfoClassTeacherResponse> allInfoClassTeacherResponses = new ArrayList<>();
        List<Classes> listClass = classRepository.findAllByTeacher(id);
        LocalDateTime time_now = LocalDateTime.now();
        listClass.forEach(ele -> {
            schedule = "";
            // LocalDateTime res = getEndSectionOfClass(ele.getId());
            ele.getSemesterClass().getSchedules().forEach(schedule_ele -> {
                if (ele.getSemesterClass().getSchedules().contains(schedule_ele)) {
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

            if (time_now
                    .isBefore(ele.getSemesterClass().getSemester().getEnd_time())) {

                GetInfoClassTeacherResponse infoClassTeacherResponse = GetInfoClassTeacherResponse.builder()
                        .id(ele.getId())
                        .semester_class_id(ele.getSemesterClass().getId())
                        .security_code(ele.getSecurity_code())
                        .name(ele.getName())
                        .link_url(ele.getLink_meeting())
                        .course_id(ele.getSemesterClass().getCourse().getId())
                        .course_name(ele.getSemesterClass().getCourse().getName())
                        .semester_name(
                                ele.getSemesterClass().getSemester().getName())
                        .semster_class_id(ele.getSemesterClass().getId())
                        .total_student(ele.getClassHasRegisterJoinSemesterClasses().size())
                        .num_of_section(ele.getSemesterClass().getCourse()
                                .getNum_of_section())
                        .art_age_name(ele.getSemesterClass().getCourse().getArtAges()
                                .getName())
                        .art_type_name(ele.getSemesterClass().getCourse()
                                .getArtTypes().getName())
                        .art_level_name(ele.getSemesterClass().getCourse()
                                .getArtLevels().getName())
                        .schedule(schedule)
                        .status("DOING")
                        .build();
                allInfoClassTeacherResponses.add(infoClassTeacherResponse);
            }

            else {
                GetInfoClassTeacherResponse infoClassTeacherResponse = GetInfoClassTeacherResponse.builder()
                        .id(ele.getId())
                        .semester_class_id(ele.getSemesterClass().getId())
                        .security_code(ele.getSecurity_code())
                        .name(ele.getName())
                        .link_url(ele.getLink_meeting())
                        .course_id(ele.getSemesterClass().getCourse().getId())
                        .course_name(ele.getSemesterClass().getCourse().getName())
                        .semester_name(
                                ele.getSemesterClass().getSemester().getName())
                        .semster_class_id(ele.getSemesterClass().getId())
                        .total_student(ele.getClassHasRegisterJoinSemesterClasses().size())
                        .num_of_section(ele.getSemesterClass().getCourse()
                                .getNum_of_section())
                        .art_age_name(ele.getSemesterClass().getCourse().getArtAges()
                                .getName())
                        .art_type_name(ele.getSemesterClass().getCourse()
                                .getArtTypes().getName())
                        .art_level_name(ele.getSemesterClass().getCourse()
                                .getArtLevels().getName())
                        .schedule(schedule)
                        .status("DONE")
                        .review_star(getReviewStarForClass(ele.getId()))
                        .build();
                        allInfoClassTeacherResponses.add(infoClassTeacherResponse);

            }
        });
        Map<String, Object> response = new HashMap<>();
        response.put("classes", allInfoClassTeacherResponses);
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
                    .semester_class_id(content.getSemesterClass().getId())
                    .security_code(content.getSecurity_code())
                    .name(content.getName())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
            allClassResponses.add(classResponse);

            GetSemesterResponse semesterResponse = GetSemesterResponse.builder()
                    .id(content.getSemesterClass().getSemester().getId())
                    .name(content.getSemesterClass().getSemester().getName())
                    .description(content.getSemesterClass().getSemester().getDescription())
                    .start_time(content.getSemesterClass().getSemester().getStart_time())
                    .number(content.getSemesterClass().getSemester().getNumber())
                    .year(content.getSemesterClass().getSemester().getYear())
                    .create_time(content.getSemesterClass().getSemester().getCreate_time())
                    .update_time(content.getSemesterClass().getSemester().getUpdate_time())
                    .build();
            allSemesterResponses.add(semesterResponse);

            GetCourseResponse courseResponse = GetCourseResponse.builder()
                    .id(content.getSemesterClass().getCourse().getId())
                    .name(content.getSemesterClass().getCourse().getName())
                    .description(content.getSemesterClass().getCourse().getDescription())
                    .num_of_section(content.getSemesterClass().getCourse().getNum_of_section())
                    .image_url(content.getSemesterClass().getCourse().getImage_url())
                    .price(content.getSemesterClass().getCourse().getPrice())
                    
                    .art_age_id(content.getSemesterClass().getCourse().getArtAges().getId())
                    .art_type_id(content.getSemesterClass().getCourse().getArtTypes().getId())
                    .art_level_id(content.getSemesterClass().getCourse().getArtLevels().getId())
                    .create_time(content.getSemesterClass().getCourse().getCreate_time())
                    .update_time(content.getSemesterClass().getCourse().getUpdate_time())
                    .build();
            allCourseResponses.add(courseResponse);

            String parent_name = "";

            GetStudentResponse userResponse = GetStudentResponse.builder()
                    .id(content.getTeacher().getId())
                    .username(content.getTeacher().getUser().getUsername())
                    .email(content.getTeacher().getUser().getEmail())
                    .firstName(content.getTeacher().getUser().getFirstName())
                    .lastName(content.getTeacher().getUser().getLastName())
                    .profile_image_url(content.getTeacher().getUser().getProfileImageUrl())
                    .sex(content.getTeacher().getUser().getSex())
                    .phone(content.getTeacher().getPhone())
                    .address(content.getTeacher().getUser().getAddress())
                    .parent(parent_name)
                    .createTime(content.getTeacher().getUser().getCreateTime())
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
    public ResponseEntity<Map<String, Object>> getClassesForStudentId(Long id) {
        List<GetClassesParentResponse> allClassDoingResponses = new ArrayList<>();
        List<GetClassesParentResponse> allClassDoneResponses = new ArrayList<>();

        LocalDateTime time_now = LocalDateTime.now();

        List<ClassHasRegisterJoinSemesterClass> allClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository
                .findAllByStudent(id);

        allClassHasRegisterJoinSemesterClass.forEach(class_has_join_semester_class -> {
            if (time_now.isAfter(
                    class_has_join_semester_class.getClasses().getSemesterClass()
                            .getSemester().getEnd_time()) == false) {
                totalSectionStudyedByClass(class_has_join_semester_class.getClasses()).forEach((key, tab) -> {
                    GetClassesParentResponse classResponse = GetClassesParentResponse.builder()
                    .id(class_has_join_semester_class.getClasses().getId())
                    .student_id(id)
                    .total_section_studied(total)
                    .schedule_section_next(schedule_section_next)
                    .semester_id(
                            class_has_join_semester_class.getClasses()
                                    .getSemesterClass()
                                    .getSemester().getId())
                    .url_image_course(class_has_join_semester_class.getClasses()
                        .getSemesterClass()
                        .getCourse().getImage_url())
                    .semester_name(class_has_join_semester_class.getClasses()
                            .getSemesterClass().getSemester().getName())
                    .student_id(
                            class_has_join_semester_class.getClasses().getSemesterClass().getId())
                    .course_id(
                            class_has_join_semester_class.getClasses()
                                    .getSemesterClass()
                                    .getCourse().getId())
                    .course_name(
                            class_has_join_semester_class.getClasses()
                                    .getSemesterClass()
                                    .getCourse().getName())
                    .student_name(
                            class_has_join_semester_class.getStudent().getUser().getUsername() + " - "
                            + class_has_join_semester_class.getStudent().getUser().getFirstName()
                                    + " "
                                    + class_has_join_semester_class.getStudent()
                                    .getUser().getLastName())
                    .semester_class_id(class_has_join_semester_class.getClasses()
                            .getSemesterClass().getId())
                    .semester_class_name(class_has_join_semester_class.getClasses()
                            .getSemesterClass().getName())
                    .link_url(class_has_join_semester_class.getClasses().getLink_meeting())
                    .teacher_id(class_has_join_semester_class.getClasses()
                            .getTeacher().getId())
                    .teacher_name(class_has_join_semester_class.getClasses()
                    .getTeacher().getUser().getUsername() + " - " + class_has_join_semester_class.getClasses()
                            .getTeacher().getUser().getFirstName()
                            + " "
                            + class_has_join_semester_class.getClasses()
                                    .getTeacher().getUser().getLastName())
                    .art_age_id(
                            class_has_join_semester_class.getClasses()
                                    .getSemesterClass()
                                    .getCourse().getArtAges().getId())
                    .art_age_name(
                            class_has_join_semester_class.getClasses()
                                    .getSemesterClass()
                                    .getCourse().getArtAges().getName())
                    .art_level_id(
                            class_has_join_semester_class.getClasses()
                                    .getSemesterClass()
                                    .getCourse().getArtLevels().getId())
                    .art_level_name(class_has_join_semester_class.getClasses()
                            .getSemesterClass().getCourse().getArtLevels().getName())
                    .art_type_id(
                            class_has_join_semester_class.getClasses()
                                    .getSemesterClass()
                                    .getCourse().getArtTypes().getId())
                    .art_type_name(class_has_join_semester_class.getClasses()
                            .getSemesterClass().getCourse().getArtTypes().getName())
                    .total_section(class_has_join_semester_class.getClasses().getSections().size())
                    .total_student(class_has_join_semester_class.getClasses()
                            .getClassHasRegisterJoinSemesterClasses().size())
                    .semester_class_id(
                            class_has_join_semester_class.getClasses()
                                    .getId())
                    .security_code(class_has_join_semester_class.getClasses().getSecurity_code())
                    .name(class_has_join_semester_class.getClasses().getName())
                    .create_time(class_has_join_semester_class.getClasses().getCreate_time())
                    .update_time(class_has_join_semester_class.getClasses().getUpdate_time())
                    .build();
            allClassDoingResponses.add(classResponse);
                });
            } else {
                GetClassesParentResponse classResponse = GetClassesParentResponse.builder()
                        .id(class_has_join_semester_class.getClasses().getId())
                        .student_id(id)
                        .semester_class_id(class_has_join_semester_class.getClasses()
                                .getSemesterClass().getId())
                        .semester_class_name(class_has_join_semester_class.getClasses()
                                .getSemesterClass().getName())
                        .url_image_course(class_has_join_semester_class.getClasses()
                                .getSemesterClass()
                                .getCourse().getImage_url())
                        .student_name(
                            class_has_join_semester_class.getStudent().getUser().getUsername() + " - "
                            + class_has_join_semester_class.getStudent().getUser().getFirstName()
                                    + " "
                                    + class_has_join_semester_class.getStudent()
                                    .getUser().getLastName())
                        .semester_id(
                                class_has_join_semester_class.getClasses()
                                        .getSemesterClass()
                                        .getSemester().getId())
                        .semester_name(class_has_join_semester_class.getClasses()
                                .getSemesterClass().getSemester().getName())
                        .course_id(
                                class_has_join_semester_class.getClasses()
                                        .getSemesterClass()
                                        .getCourse().getId())
                        .student_id(
                                class_has_join_semester_class.getClasses().getSemesterClass().getId())
                        .course_name(
                                class_has_join_semester_class.getClasses()
                                        .getSemesterClass()
                                        .getCourse().getName())
                        .link_url(class_has_join_semester_class.getClasses().getLink_meeting())
                        .teacher_id(class_has_join_semester_class.getClasses()
                                .getTeacher().getId())
                        .teacher_name(class_has_join_semester_class.getClasses()
                        .getTeacher().getUser().getUsername() + " - " + class_has_join_semester_class.getClasses()
                                .getTeacher().getUser().getFirstName()
                                + " "
                                + class_has_join_semester_class.getClasses()
                                        .getTeacher().getUser().getLastName())
                        .art_age_id(
                                class_has_join_semester_class.getClasses()
                                        .getSemesterClass()
                                        .getCourse().getArtAges().getId())
                        .art_age_name(
                                class_has_join_semester_class.getClasses()
                                        .getSemesterClass()
                                        .getCourse().getArtAges().getName())
                        .art_level_id(
                                class_has_join_semester_class.getClasses()
                                        .getSemesterClass()
                                        .getCourse().getArtLevels().getId())
                        .art_level_name(class_has_join_semester_class.getClasses()
                                .getSemesterClass().getCourse().getArtLevels().getName())
                        .art_type_id(
                                class_has_join_semester_class.getClasses()
                                        .getSemesterClass()
                                        .getCourse().getArtTypes().getId())
                        .art_type_name(class_has_join_semester_class.getClasses()
                                .getSemesterClass().getCourse().getArtTypes().getName())
                        .total_section(class_has_join_semester_class.getClasses().getSections().size())
                        .total_student(class_has_join_semester_class.getClasses()
                                .getClassHasRegisterJoinSemesterClasses().size())
                        .semester_class_id(
                                class_has_join_semester_class.getClasses()
                                        .getId())
                        .security_code(class_has_join_semester_class.getClasses().getSecurity_code())
                        .name(class_has_join_semester_class.getClasses().getName())
                        .create_time(class_has_join_semester_class.getClasses().getCreate_time())
                        .update_time(class_has_join_semester_class.getClasses().getUpdate_time())
                        .build();
                allClassDoneResponses.add(classResponse);
            }
        });
        Map<String, Object> response = new HashMap<>();
        response.put("classes_doing", allClassDoingResponses);
        response.put("classes_done", allClassDoneResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getListForParentId(Long parent_id) {
        List<GetClassesParentResponse> allClassDoingResponses = new ArrayList<>();
        List<GetClassesParentResponse> allClassDoneResponses = new ArrayList<>();
        LocalDateTime time_now = LocalDateTime.now();
        List<Student> allChildForParent = studentRepository
                .findByParentId4(parent_id);
        allChildForParent.forEach(ele -> {
            List<ClassHasRegisterJoinSemesterClass> allClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository
                    .findAllByStudent(ele.getId());

            allClassHasRegisterJoinSemesterClass.forEach(class_has_join_semester_class -> {
                if (time_now.isAfter(
                        class_has_join_semester_class.getClasses().getSemesterClass()
                                .getSemester().getEnd_time()) == false) {
                    totalSectionStudyedByClass(class_has_join_semester_class.getClasses()).forEach((key, tab) -> {
                        GetClassesParentResponse classResponse = GetClassesParentResponse.builder()
                        .id(class_has_join_semester_class.getClasses().getId())
                        .student_id(ele.getId())
                        .total_section_studied(total)
                        .schedule_section_next(schedule_section_next)
                        .semester_id(
                                class_has_join_semester_class.getClasses()
                                        .getSemesterClass()
                                        .getSemester().getId())
                        .student_id(
                                class_has_join_semester_class.getClasses().getSemesterClass().getId())
                        .course_id(
                            class_has_join_semester_class.getClasses()
                            .getSemesterClass()
                            .getCourse().getId())
                        .url_image_course(class_has_join_semester_class.getClasses()
                            .getSemesterClass()
                            .getCourse().getImage_url())
                        .total_section(class_has_join_semester_class.getClasses()
                            .getSemesterClass()
                            .getCourse().getNum_of_section())
                            .art_age_id(
                                class_has_join_semester_class.getClasses()
                                        .getSemesterClass()
                                        .getCourse().getArtAges().getId())
                        .total_student(class_has_join_semester_class.getClasses()
                            .getClassHasRegisterJoinSemesterClasses().size())
                        .art_age_name(
                                class_has_join_semester_class.getClasses()
                                        .getSemesterClass()
                                        .getCourse().getArtAges().getName())
                        .art_level_id(
                                class_has_join_semester_class.getClasses()
                                        .getSemesterClass()
                                        .getCourse().getArtLevels().getId())
                        .art_level_name(class_has_join_semester_class.getClasses()
                                .getSemesterClass().getCourse().getArtLevels().getName())
                        .art_type_id(
                                class_has_join_semester_class.getClasses()
                                        .getSemesterClass()
                                        .getCourse().getArtTypes().getId())
                        .art_type_name(class_has_join_semester_class.getClasses()
                                .getSemesterClass().getCourse().getArtTypes().getName())
                        .course_name(
                                class_has_join_semester_class.getClasses()
                                        .getSemesterClass()
                                        .getCourse().getName())
                        .student_name(class_has_join_semester_class.getStudent().getUser().getUsername() + " - "
                        + class_has_join_semester_class.getStudent().getUser().getFirstName()
                                + " "
                                + class_has_join_semester_class.getStudent()
                                .getUser().getLastName())
                        .link_url(class_has_join_semester_class.getClasses().getLink_meeting())
                        .teacher_id(class_has_join_semester_class.getClasses()
                                .getTeacher().getId())
                        .teacher_name(class_has_join_semester_class.getClasses()
                        .getTeacher().getUser().getUsername() + " - " + class_has_join_semester_class.getClasses()
                                .getTeacher().getUser().getFirstName()
                                + " "
                                + class_has_join_semester_class.getClasses()
                                        .getTeacher().getUser().getLastName())
                        .security_code(class_has_join_semester_class.getClasses().getSecurity_code())
                        .name(class_has_join_semester_class.getClasses().getName())
                        .create_time(class_has_join_semester_class.getClasses().getCreate_time())
                        .update_time(class_has_join_semester_class.getClasses().getUpdate_time())
                        .build();
                allClassDoingResponses.add(classResponse);
                    });
                } else {
                    GetClassesParentResponse classResponse = GetClassesParentResponse.builder()
                            .id(class_has_join_semester_class.getClasses().getId())
                            .student_id(ele.getId())
                            .student_name(ele.getUser().getUsername() + " - " + ele
                                .getUser().getFirstName()
                                    + " "
                                    + ele
                                    .getUser().getLastName())
                            .course_id(
                                    class_has_join_semester_class.getClasses()
                                            .getSemesterClass()
                                            .getCourse().getId())
                            .total_section(class_has_join_semester_class.getClasses()
                                .getSemesterClass()
                                .getCourse().getNum_of_section())
                            .url_image_course(class_has_join_semester_class.getClasses()
                                .getSemesterClass()
                                .getCourse().getImage_url())
                                .art_age_id(
                                    class_has_join_semester_class.getClasses()
                                            .getSemesterClass()
                                            .getCourse().getArtAges().getId())
                            .art_age_name(
                                    class_has_join_semester_class.getClasses()
                                            .getSemesterClass()
                                            .getCourse().getArtAges().getName())
                            .art_level_id(
                                    class_has_join_semester_class.getClasses()
                                            .getSemesterClass()
                                            .getCourse().getArtLevels().getId())
                            .art_level_name(class_has_join_semester_class.getClasses()
                                    .getSemesterClass().getCourse().getArtLevels().getName())
                            .art_type_id(
                                    class_has_join_semester_class.getClasses()
                                            .getSemesterClass()
                                            .getCourse().getArtTypes().getId())
                            .art_type_name(class_has_join_semester_class.getClasses()
                                    .getSemesterClass().getCourse().getArtTypes().getName())
                            .course_name(
                                    class_has_join_semester_class.getClasses()
                                            .getSemesterClass()
                                            .getCourse().getName())
                            .link_url(class_has_join_semester_class.getClasses().getLink_meeting())
                            .teacher_id(class_has_join_semester_class.getClasses()
                                    .getTeacher().getId())
                            .teacher_name(class_has_join_semester_class.getClasses()
                            .getTeacher().getUser().getUsername() + " - " + class_has_join_semester_class.getClasses()
                                    .getTeacher().getUser().getFirstName()
                                    + " "
                                    + class_has_join_semester_class.getClasses()
                                            .getTeacher().getUser().getLastName() )
                            .total_student(class_has_join_semester_class.getClasses()
                                .getClassHasRegisterJoinSemesterClasses().size())
                            .security_code(class_has_join_semester_class.getClasses().getSecurity_code())
                            .name(class_has_join_semester_class.getClasses().getName())
                            .create_time(class_has_join_semester_class.getClasses().getCreate_time())
                            .update_time(class_has_join_semester_class.getClasses().getUpdate_time())
                            .build();
                    allClassDoneResponses.add(classResponse);
                }
            });
        });
        Map<String, Object> response = new HashMap<>();
        response.put("classes_doing", allClassDoingResponses);
        response.put("classes_done", allClassDoneResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getClassesStudentForStudentId(Long id) {
        List<GetClassesStudentResponse> allClassResponses = new ArrayList<>();
        List<ClassHasRegisterJoinSemesterClass> listClassHasJoinSemester = classHasRegisterJoinSemesterClassRepository.findAllByStudent(id);
        listClassHasJoinSemester.forEach(classHasRegisterJoinSemesterClass -> {
            // ClassHasRegisterJoinSemesterClass classHasRegisterJoinSemesterClassOpt =
            // student.getClassHasRegisterJoinSemesterClass();
            GetClassesStudentResponse classResponse = GetClassesStudentResponse.builder()
                    .id(classHasRegisterJoinSemesterClass.getClasses().getId())
                    .semester_class_id(
                            classHasRegisterJoinSemesterClass.getClasses().getSemesterClass().getId())
                    .link_url(classHasRegisterJoinSemesterClass.getClasses().getLink_meeting())
                    .teacher_id(classHasRegisterJoinSemesterClass.getClasses()
                            .getTeacher().getId())
                    .teacher_name(classHasRegisterJoinSemesterClass.getClasses()
                    .getTeacher().getUser().getUsername() + " - " + classHasRegisterJoinSemesterClass.getClasses()
                            .getTeacher().getUser().getFirstName()
                            + " "
                            + classHasRegisterJoinSemesterClass.getClasses().getTeacher()
                            .getUser().getLastName())
                    .art_age_id(classHasRegisterJoinSemesterClass.getClasses()
                            .getSemesterClass()
                            .getCourse().getArtAges().getId())
                    .art_age_name(classHasRegisterJoinSemesterClass.getClasses()
                            .getSemesterClass()
                            .getCourse().getArtAges().getName())
                    .art_level_id(classHasRegisterJoinSemesterClass.getClasses()
                            .getSemesterClass()
                            .getCourse().getArtLevels().getId())
                    .art_level_name(classHasRegisterJoinSemesterClass.getClasses()
                            .getSemesterClass()
                            .getCourse().getArtLevels().getName())
                    .art_type_id(classHasRegisterJoinSemesterClass.getClasses()
                            .getSemesterClass()
                            .getCourse().getArtTypes().getId())
                    .art_type_name(classHasRegisterJoinSemesterClass.getClasses()
                            .getSemesterClass()
                            .getCourse().getArtTypes().getName())
                    .total_section(classHasRegisterJoinSemesterClass.getClasses().getSections().size())
                    .total_student(classHasRegisterJoinSemesterClass.getClasses()
                            .getClassHasRegisterJoinSemesterClasses().size())
                    .semester_class_id(
                            classHasRegisterJoinSemesterClass.getClasses().getSemesterClass().getId())
                    .security_code(classHasRegisterJoinSemesterClass.getClasses().getSecurity_code())
                    .name(classHasRegisterJoinSemesterClass.getClasses().getName())
                    .create_time(classHasRegisterJoinSemesterClass.getClasses().getCreate_time())
                    .update_time(classHasRegisterJoinSemesterClass.getClasses().getUpdate_time())
                    .build();
            allClassResponses.add(classResponse);
        });
        Map<String, Object> response = new HashMap<>();
        response.put("classes", allClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getInforScheduleAllClass() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, List<Map<String, List<List<LocalDateTime>>>>>> res = new ArrayList<>();
        List<Classes> classOpt = classRepository.findAll2();
        LocalDateTime time_now = LocalDateTime.now();
        classOpt.forEach(classes -> {
            if (time_now.isAfter(classes.getSemesterClass().getSemester().getStart_time()) && time_now.isBefore(classes.getSemesterClass().getSemester().getEnd_time())) {
                SemesterClass semesterCouse = classes.getSemesterClass();
                List<GetScheduleResponse> allScheduleResponses = new ArrayList<>();
                List<Integer> dayOfWeeks = new ArrayList<>();
                List<LessonTime> lessonTimeResponses = new ArrayList<>();
        
                semesterCouse.getSchedules().forEach(schedule_item -> {
                    GetScheduleResponse scheduleResponse = GetScheduleResponse.builder()
                            .id(schedule_item.getId())
                            .lesson_time(schedule_item.getLessonTime().getStart_time().toString() + " - "
                                    + schedule_item.getLessonTime().getEnd_time().toString())
                            .lesson_time_id(schedule_item.getLessonTime().getId())
                            .date_of_week(schedule_item.getDate_of_week())
                            .build();
                    allScheduleResponses.add(scheduleResponse);
                    dayOfWeeks.add(schedule_item.getDate_of_week());
                    lessonTimeResponses.add(schedule_item.getLessonTime());
                });
        
                Collections.sort(dayOfWeeks);
        
                List<LocalDate> list_holiday = new ArrayList<>();
                List<Holiday> allHoliday = holidayRepository.findBySemesterId(semesterCouse.getSemester().getId());
                allHoliday.forEach(holiday -> {
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
                    if (semesterCouse.getSchedules().size() > 1) {
                        for (int idx = 0; idx < semesterCouse.getSchedules().size(); idx++) {
                            Integer dayOfWeek = dayOfWeeks.get(idx);
                            LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                            LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                            // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                            System.out.printf("Day_of_week: %d\n", dayOfWeek);
                            List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
                            if (dayOfWeek - 1 == start_time.getDayOfWeek().getValue()) {
                                start_time = start_time.plusDays(7);
                            } else {
                                while (start_time.getDayOfWeek().getValue() != dayOfWeek - 1) {
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
                    } else {
                        for (int idx = 0; idx < semesterCouse.getSchedules().size(); idx++) {
                            Integer dayOfWeek = dayOfWeeks.get(idx);
                            LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                            LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                            // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                            System.out.printf("Day_of_week: %d\n", dayOfWeek);
                            List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
                            if (dayOfWeek - 1 == start_time.getDayOfWeek().getValue()) {
                                start_time = start_time.plusDays(7);
                            } else {
                                while (start_time.getDayOfWeek().getValue() != dayOfWeek - 1) {
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
                    }
                    Map<String, List<List<LocalDateTime>>> schedule_in_week = new HashMap<>();
                    String name = "week_" + week_count;
                    schedule_in_week.put(name, lesson_time_in_week);
                    allCalendarForSemesterClass.add(schedule_in_week);
                    // start_time = start_time.plusWeeks(1);
                    week_count++;
                }
                Map<String, List<Map<String, List<List<LocalDateTime>>>>> sheduleForClass = new HashMap<>();
                String class_name = classes.getName();
                sheduleForClass.put(class_name, allCalendarForSemesterClass);
                res.add(sheduleForClass);
            }
        });

        response.put("schedule", res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public LocalDateTime getEndSectionOfClass(Classes classes) {

        SemesterClass semesterCouse = classes.getSemesterClass();

        List<Integer> dayOfWeeks = new ArrayList<>();
        List<LessonTime> lessonTimeResponses = new ArrayList<>();

        semesterCouse.getSchedules().forEach(ele -> {
            dayOfWeeks.add(ele.getDate_of_week());
            lessonTimeResponses.add(ele.getLessonTime());
        });

        Collections.sort(dayOfWeeks);

        List<LocalDate> list_holiday = new ArrayList<>();
        List<Holiday> allHoliday = holidayRepository.findBySemesterId(semesterCouse.getSemester().getId());
        allHoliday.forEach(holiday -> {
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
            if (semesterCouse.getSchedules().size() > 1) {
                for (int idx = 0; idx < semesterCouse.getSchedules().size(); idx++) {
                    Integer dayOfWeek = dayOfWeeks.get(idx);
                    LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                    LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                    // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                    System.out.printf("Day_of_week: %d\n", dayOfWeek);
                    List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
                    if (dayOfWeek - 1 == start_time.getDayOfWeek().getValue()) {
                        start_time = start_time.plusDays(7);
                    } else {
                        while (start_time.getDayOfWeek().getValue() != dayOfWeek - 1) {
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
            } else {
                for (int idx = 0; idx < semesterCouse.getSchedules().size(); idx++) {
                    Integer dayOfWeek = dayOfWeeks.get(idx);
                    LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                    LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                    // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                    System.out.printf("Day_of_week: %d\n", dayOfWeek);
                    List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
                    if (dayOfWeek - 1 == start_time.getDayOfWeek().getValue()) {
                        start_time = start_time.plusDays(7);
                    } else {
                        while (start_time.getDayOfWeek().getValue() != dayOfWeek - 1) {
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

    public List<Map<String, List<List<LocalDateTime>>>> getScheduleDetailOfClass(Classes classes) {

        SemesterClass semesterCouse = classes.getSemesterClass();

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
        List<Holiday> allHoliday = holidayRepository.findBySemesterId(semesterCouse.getSemester().getId());
        allHoliday.forEach(holiday -> {
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
            if (semesterCouse.getSchedules().size() > 1) {
                for (int idx = 0; idx < semesterCouse.getSchedules().size(); idx++) {
                    Integer dayOfWeek = dayOfWeeks.get(idx);
                    LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                    LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                    // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                    System.out.printf("Day_of_week: %d\n", dayOfWeek);
                    List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
                    if (dayOfWeek - 1 == start_time.getDayOfWeek().getValue()) {
                        start_time = start_time.plusDays(7);
                    } else {
                        while (start_time.getDayOfWeek().getValue() != dayOfWeek - 1) {
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
            } else {
                for (int idx = 0; idx < semesterCouse.getSchedules().size(); idx++) {
                    Integer dayOfWeek = dayOfWeeks.get(idx);
                    LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                    LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                    // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                    System.out.printf("Day_of_week: %d\n", dayOfWeek);
                    List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
                    if (dayOfWeek - 1 == start_time.getDayOfWeek().getValue()) {
                        start_time = start_time.plusDays(7);
                    } else {
                        while (start_time.getDayOfWeek().getValue() != dayOfWeek - 1) {
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

    public Map<String, Integer> totalSectionStudyedByClass(Classes classes) {
        Map<String, Integer> res =  new HashMap<>();
        total = 0;

        SemesterClass semesterCouse = classes.getSemesterClass();

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
        List<Holiday> allHoliday = holidayRepository.findBySemesterId(semesterCouse.getSemester().getId());
        allHoliday.forEach(holiday -> {
            list_holiday.add(holiday.getDay());
        });

        Integer total_section = semesterCouse.getCourse().getNum_of_section();
        int total_week = total_section / semesterCouse.getSchedules().size();
        if (total_section % semesterCouse.getSchedules().size() != 0) {
            total_week++;
        }
        System.out.printf("total_week: %d\n", total_week);
        total_section_count = 0;
        LocalDateTime start_time = semesterCouse.getSemester().getStart_time();
        week_count = 0;
        checked_section_next = 0;
        while (total_section_count < total_section) {
            if (semesterCouse.getSchedules().size() > 1) {
                for (int idx = 0; idx < semesterCouse.getSchedules().size(); idx++) {
                    Integer dayOfWeek = dayOfWeeks.get(idx);
                    LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                    LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                    if (dayOfWeek - 1 == start_time.getDayOfWeek().getValue()) {
                        start_time = start_time.plusDays(7);
                    } else {
                        while (start_time.getDayOfWeek().getValue() != dayOfWeek - 1) {
                            start_time = start_time.plusDays(1);
                        }
                    }

                    if (total_section_count < total_section) {
                        LocalDate start_date = start_time.toLocalDate();
                        if (list_holiday.contains(start_date) == false) {
                            LocalDateTime time_now = LocalDateTime.now();
                            if (end_lessontime.atDate(start_date).isBefore(time_now)) {
                                total ++;
                            }
                            else if (start_lessontime.atDate(start_date).isAfter(time_now) && checked_section_next == 0) {
                                schedule_section_next =  start_lessontime.atDate(start_date).toString() + ", " + end_lessontime.atDate(start_date).toString();
                                checked_section_next ++;
                            }
                            total_section_count++;
                        }
                    }
                }
            } else {
                for (int idx = 0; idx < semesterCouse.getSchedules().size(); idx++) {
                    Integer dayOfWeek = dayOfWeeks.get(idx);
                    LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                    LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                    if (dayOfWeek - 1 == start_time.getDayOfWeek().getValue()) {
                        start_time = start_time.plusDays(7);
                    } else {
                        while (start_time.getDayOfWeek().getValue() != dayOfWeek - 1) {
                            start_time = start_time.plusDays(1);
                        }
                    }

                    if (total_section_count < total_section) {
                        LocalDate start_date = start_time.toLocalDate();
                        if (list_holiday.contains(start_date) == false) {
                            LocalDateTime time_now = LocalDateTime.now();
                            if (end_lessontime.atDate(start_date).isBefore(time_now)) {
                                total ++;
                            }
                            else if (start_lessontime.atDate(start_date).isAfter(time_now) && checked_section_next == 0) {
                                schedule_section_next =  start_lessontime.atDate(start_date).toString() + ", " + end_lessontime.atDate(start_date).toString();
                                checked_section_next ++;
                            }
                            total_section_count++;
                        }
                    }
                }
            }
            week_count++;
        }
        res.put(schedule_section_next, total);
        return res;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getInforScheduleChild(Long child_id) {
        List<ClassHasRegisterJoinSemesterClass> allClassHasRegisterJoinSemesterClassByChild = classHasRegisterJoinSemesterClassRepository.findAllByStudent(child_id);
        List<Map<String, List<Map<String, List<List<LocalDateTime>>>>>> allCalendarForChild = new ArrayList<>();
        LocalDateTime time_now = LocalDateTime.now();
        allClassHasRegisterJoinSemesterClassByChild.forEach(class_has_join_semester_class -> {
            if (class_has_join_semester_class.getClasses().getSemesterClass().getSemester().getEnd_time().isAfter(time_now)) {
                List<GetScheduleResponse> allScheduleResponses = new ArrayList<>();
                class_has_join_semester_class.getClasses().getSemesterClass().getSchedules().forEach(schedule_item -> {
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
                class_has_join_semester_class.getClasses().getSemesterClass().getSchedules().forEach(ele -> {
                    dayOfWeeks.add(ele.getDate_of_week());
                });

                Collections.sort(dayOfWeeks);

                List<LessonTime> lessonTimeResponses = new ArrayList<>();
                class_has_join_semester_class.getClasses().getSemesterClass().getSchedules().forEach(schedule_item -> {
                    lessonTimeResponses.add(schedule_item.getLessonTime());
                });

                List<LocalDate> list_holiday = new ArrayList<>();
                List<Holiday> allHolidays = holidayRepository.findBySemesterId(class_has_join_semester_class.getClasses().getSemesterClass().getSemester().getId());
                allHolidays.forEach(holiday -> {
                    list_holiday.add(holiday.getDay());
                });

                List<Map<String, List<List<LocalDateTime>>>> allCalendarForSemesterClass = new ArrayList<>();
                Integer total_section = class_has_join_semester_class.getClasses().getSemesterClass().getCourse().getNum_of_section();
                System.out.printf("total_section: %d\n", total_section);
                System.out.printf("total_number_week: %d\n", class_has_join_semester_class.getClasses().getSemesterClass().getSchedules().size());
                int total_week = total_section / class_has_join_semester_class.getClasses().getSemesterClass().getSchedules().size();
                if (total_section % class_has_join_semester_class.getClasses().getSemesterClass().getSchedules().size() != 0) {
                    total_week++;
                }
                System.out.printf("total_week: %d\n", total_week);
                total_section_count = 0;
                LocalDateTime start_time = class_has_join_semester_class.getClasses().getSemesterClass().getSemester().getStart_time();
                week_count = 0;
                while (total_section_count < total_section) {
                    List<List<LocalDateTime>> lesson_time_in_week = new ArrayList<>();
                    if (class_has_join_semester_class.getClasses().getSemesterClass().getSchedules().size() > 1) {
                        for (int idx = 0; idx < class_has_join_semester_class.getClasses().getSemesterClass().getSchedules().size(); idx++) {
                            Integer dayOfWeek = dayOfWeeks.get(idx);
                            LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                            LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                            // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                            System.out.printf("Day_of_week: %d\n", dayOfWeek);
                            List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
                            if (dayOfWeek - 1 == start_time.getDayOfWeek().getValue()) {
                                start_time = start_time.plusDays(7);
                            } else {
                                while (start_time.getDayOfWeek().getValue() != dayOfWeek - 1) {
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
                    } else {
                        for (int idx = 0; idx < class_has_join_semester_class.getClasses().getSemesterClass().getSchedules().size(); idx++) {
                            Integer dayOfWeek = dayOfWeeks.get(idx);
                            LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                            LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                            // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                            System.out.printf("Day_of_week: %d\n", dayOfWeek);
                            List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
                            if (dayOfWeek - 1 == start_time.getDayOfWeek().getValue()) {
                                start_time = start_time.plusDays(7);
                            } else {
                                while (start_time.getDayOfWeek().getValue() != dayOfWeek - 1) {
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
                    }
                    Map<String, List<List<LocalDateTime>>> schedule_in_week = new HashMap<>();
                    String name = "week_" + week_count;
                    schedule_in_week.put(name, lesson_time_in_week);
                    allCalendarForSemesterClass.add(schedule_in_week);
                    // start_time = start_time.plusWeeks(1);
                    week_count++;
                }
                Map<String, List<Map<String, List<List<LocalDateTime>>>>> schedule_class = new HashMap<>();
                String name = class_has_join_semester_class.getClasses().getSemesterClass().getCourse().getName();
                schedule_class.put(name, allCalendarForSemesterClass);
                allCalendarForChild.add(schedule_class);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("schedules", allCalendarForChild);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getInforScheduleAllChild(Long parent_id) {
        List<Map<String, List<Map<String, List<Map<String, List<List<LocalDateTime>>>>>>>> allCalendarForAllChild = new ArrayList<>();
            List<ClassHasRegisterJoinSemesterClass> allClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository.findAllByParent(parent_id);
            System.out.print(allClassHasRegisterJoinSemesterClass.size());
            LocalDateTime time_now = LocalDateTime.now();
            allClassHasRegisterJoinSemesterClass.forEach(class_has_register_join_semester_class -> {
                if (class_has_register_join_semester_class.getClasses().getSemesterClass().getSemester().getEnd_time().isAfter(time_now)) {
                    List<Map<String, List<Map<String, List<List<LocalDateTime>>>>>> allCalendarForChild = new ArrayList<>();
                    List<GetScheduleResponse> allScheduleResponses = new ArrayList<>();
                    class_has_register_join_semester_class.getClasses().getSemesterClass().getSchedules().forEach(schedule_item -> {
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
                    class_has_register_join_semester_class.getClasses().getSemesterClass().getSchedules().forEach(ele -> {
                        dayOfWeeks.add(ele.getDate_of_week());
                    });

                    Collections.sort(dayOfWeeks);

                    List<LessonTime> lessonTimeResponses = new ArrayList<>();
                    class_has_register_join_semester_class.getClasses().getSemesterClass().getSchedules().forEach(schedule_item -> {
                        lessonTimeResponses.add(schedule_item.getLessonTime());
                    });

                    List<LocalDate> list_holiday = new ArrayList<>();
                    List<Holiday> allHoliday = holidayRepository.findBySemesterId(class_has_register_join_semester_class.getClasses().getSemesterClass().getSemester().getId());
                    allHoliday.forEach(holiday -> {
                        list_holiday.add(holiday.getDay());
                    });

                    List<Map<String, List<List<LocalDateTime>>>> allCalendarForSemesterClass = new ArrayList<>();
                    Integer total_section = class_has_register_join_semester_class.getClasses().getSemesterClass().getCourse().getNum_of_section();
                    System.out.printf("total_section: %d\n", total_section);
                    System.out.printf("total_number_week: %d\n", class_has_register_join_semester_class.getClasses().getSemesterClass().getSchedules().size());
                    int total_week = total_section / class_has_register_join_semester_class.getClasses().getSemesterClass().getSchedules().size();
                    if (total_section % class_has_register_join_semester_class.getClasses().getSemesterClass().getSchedules().size() != 0) {
                        total_week++;
                    }
                    System.out.printf("total_week: %d\n", total_week);
                    total_section_count = 0;
                    LocalDateTime start_time = class_has_register_join_semester_class.getClasses().getSemesterClass().getSemester().getStart_time();
                    week_count = 0;
                    while (total_section_count < total_section) {
                        List<List<LocalDateTime>> lesson_time_in_week = new ArrayList<>();
                        if (class_has_register_join_semester_class.getClasses().getSemesterClass().getSchedules().size() > 1) {
                            for (int idx = 0; idx < class_has_register_join_semester_class.getClasses().getSemesterClass().getSchedules().size(); idx++) {
                                Integer dayOfWeek = dayOfWeeks.get(idx);
                                LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                                LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                                // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                                System.out.printf("Day_of_week: %d\n", dayOfWeek);
                                List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
                                if (dayOfWeek - 1 == start_time.getDayOfWeek().getValue()) {
                                    start_time = start_time.plusDays(7);
                                } else {
                                    while (start_time.getDayOfWeek().getValue() != dayOfWeek - 1) {
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
                        } else {
                            for (int idx = 0; idx < class_has_register_join_semester_class.getClasses().getSemesterClass().getSchedules().size(); idx++) {
                                Integer dayOfWeek = dayOfWeeks.get(idx);
                                LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                                LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                                // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                                System.out.printf("Day_of_week: %d\n", dayOfWeek);
                                List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
                                if (dayOfWeek - 1 == start_time.getDayOfWeek().getValue()) {
                                    start_time = start_time.plusDays(7);
                                } else {
                                    while (start_time.getDayOfWeek().getValue() != dayOfWeek - 1) {
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
                        class_has_register_join_semester_class.getClasses().getSemesterClass().getCourse().getName(),
                            allCalendarForSemesterClass);
                    allCalendarForChild.add(schedule_class);

                    Map<String, List<Map<String, List<Map<String, List<List<LocalDateTime>>>>>>> schedule_child = new HashMap<>();
                    schedule_child.put(class_has_register_join_semester_class.getStudent().getUser().getUsername() + " - " + class_has_register_join_semester_class.getStudent().getUser().getFirstName() + " " + class_has_register_join_semester_class.getStudent().getUser().getLastName(), allCalendarForChild);
                    allCalendarForAllChild.add(schedule_child);
                }
            });

        Map<String, Object> response = new HashMap<>();
        response.put("schedules", allCalendarForAllChild);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getInforDetailOfClassForTeacher(Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Classes> classOpt = classRepository.findById6(id);
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });
        response.put("classes", GetClassResponse.builder()
                .id(classes.getId())
                .semester_class_id(classes.getSemesterClass().getId())
                .security_code(classes.getSecurity_code())
                .name(classes.getName())
                .create_time(classes.getCreate_time())
                .update_time(classes.getUpdate_time())
                .build());

        List<GetStudentResponse> listStudents = new ArrayList<>();
        List<ClassHasRegisterJoinSemesterClass> listClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository
                .findByClassesId3(id);
        listClassHasRegisterJoinSemesterClass.forEach(content -> {
            Long parent_idx = content.getStudent().getParent().getId();
            String parent_namex = content.getStudent().getParent().getUser().getUsername() + " - " + content.getStudent().getParent().getUser().getFirstName() + " "
                    + content.getStudent().getParent().getUser().getLastName();
            GetStudentResponse student = GetStudentResponse.builder()
                    .id(content.getStudent().getId())
                    .username(content.getStudent().getUser().getUsername())
                    .email(content.getStudent().getUser().getEmail())
                    .firstName(content.getStudent().getUser().getFirstName())
                    .lastName(content.getStudent().getUser().getLastName())
                    .dateOfBirth(content.getStudent().getDateOfBirth())
                    .profile_image_url(content.getStudent().getUser().getProfileImageUrl())
                    .sex(content.getStudent().getUser().getSex())
                    .phone(content.getStudent().getPhone())
                    .address(content.getStudent().getUser().getAddress())
                    .parent(parent_namex)
                    .parents(parent_idx)
                    .createTime(content.getStudent().getUser().getCreateTime())
                    .build();
            listStudents.add(student);
        });

        response.put("students", listStudents);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getInforDetailOfClass(Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Classes> classOpt = classRepository.findById5(id);
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });
        response.put("classes", GetClassResponse.builder()
                .id(classes.getId())
                .semester_class_id(classes.getSemesterClass().getId())
                .security_code(classes.getSecurity_code())
                .name(classes.getName())
                .create_time(classes.getCreate_time())
                .update_time(classes.getUpdate_time())
                .build());

        response.put("teacher", GetUserResponse.builder()
                .id(classes.getTeacher().getId())
                .username(classes.getTeacher().getUser().getUsername())
                .email(classes.getTeacher().getUser().getEmail())
                .firstName(classes.getTeacher().getUser().getFirstName())
                .lastName(classes.getTeacher().getUser().getLastName())
                .profile_image_url(classes.getTeacher().getUser().getProfileImageUrl())
                .sex(classes.getTeacher().getUser().getSex())
                .phone(classes.getTeacher().getPhone())
                .address(classes.getTeacher().getUser().getAddress())
                .createTime(classes.getTeacher().getUser().getCreateTime())
                .build());

        SemesterClass semesterCouse = classes.getSemesterClass();

        response.put("course", GetCourseResponse.builder()
                .id(semesterCouse.getCourse().getId())
                .name(semesterCouse.getCourse().getName())
                .description(semesterCouse.getCourse().getDescription())
                .num_of_section(semesterCouse.getCourse().getNum_of_section())
                .image_url(semesterCouse.getCourse().getImage_url())
                .price(semesterCouse.getCourse().getPrice())
               
                .art_age_id(semesterCouse.getCourse().getArtAges().getId())
                .art_type_id(semesterCouse.getCourse().getArtTypes().getId())
                .art_level_id(semesterCouse.getCourse().getArtLevels().getId())
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
                .build());

        List<GetStudentResponse> listStudents = new ArrayList<>();
        List<ClassHasRegisterJoinSemesterClass> listClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository
                .findByClassesId3(id);
        listClassHasRegisterJoinSemesterClass.forEach(content -> {
            Long parent_idx = content.getStudent().getParent().getId();
            String parent_namex = content.getStudent().getParent().getUser().getUsername() + " - " + content.getStudent().getParent().getUser().getFirstName() + " "
                    + content.getStudent().getParent().getUser().getLastName();
            GetStudentResponse student = GetStudentResponse.builder()
                    .id(content.getStudent().getId())
                    .username(content.getStudent().getUser().getUsername())
                    .email(content.getStudent().getUser().getEmail())
                    .firstName(content.getStudent().getUser().getFirstName())
                    .lastName(content.getStudent().getUser().getLastName())
                    .dateOfBirth(content.getStudent().getDateOfBirth())
                    .profile_image_url(content.getStudent().getUser().getProfileImageUrl())
                    .sex(content.getStudent().getUser().getSex())
                    .phone(content.getStudent().getPhone())
                    .address(content.getStudent().getUser().getAddress())
                    .parent(parent_namex)
                    .parents(parent_idx)
                    .createTime(content.getStudent().getUser().getCreateTime())
                    .build();
            listStudents.add(student);
        });

        List<GetScheduleResponse> allScheduleResponses = new ArrayList<>();
        List<Integer> dayOfWeeks = new ArrayList<>();
        List<LessonTime> lessonTimeResponses = new ArrayList<>();

        semesterCouse.getSchedules().forEach(schedule_item -> {
            GetScheduleResponse scheduleResponse = GetScheduleResponse.builder()
                    .id(schedule_item.getId())
                    .lesson_time(schedule_item.getLessonTime().getStart_time().toString() + " - "
                            + schedule_item.getLessonTime().getEnd_time().toString())
                    .lesson_time_id(schedule_item.getLessonTime().getId())
                    .date_of_week(schedule_item.getDate_of_week())
                    .build();
            allScheduleResponses.add(scheduleResponse);
            dayOfWeeks.add(schedule_item.getDate_of_week());
            lessonTimeResponses.add(schedule_item.getLessonTime());
        });

        Collections.sort(dayOfWeeks);

        List<LocalDate> list_holiday = new ArrayList<>();
        List<Holiday> allHoliday = holidayRepository.findBySemesterId(semesterCouse.getSemester().getId());
        allHoliday.forEach(holiday -> {
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
            if (semesterCouse.getSchedules().size() > 1) {
                for (int idx = 0; idx < semesterCouse.getSchedules().size(); idx++) {
                    Integer dayOfWeek = dayOfWeeks.get(idx);
                    LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                    LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                    // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                    System.out.printf("Day_of_week: %d\n", dayOfWeek);
                    List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
                    if (dayOfWeek - 1 == start_time.getDayOfWeek().getValue()) {
                        start_time = start_time.plusDays(7);
                    } else {
                        while (start_time.getDayOfWeek().getValue() != dayOfWeek - 1) {
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
            } else {
                for (int idx = 0; idx < semesterCouse.getSchedules().size(); idx++) {
                    Integer dayOfWeek = dayOfWeeks.get(idx);
                    LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                    LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                    // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                    System.out.printf("Day_of_week: %d\n", dayOfWeek);
                    List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
                    if (dayOfWeek - 1 == start_time.getDayOfWeek().getValue()) {
                        start_time = start_time.plusDays(7);
                    } else {
                        while (start_time.getDayOfWeek().getValue() != dayOfWeek - 1) {
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
    public ResponseEntity<Map<String, Object>> getInforDetailOfClass1(Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Classes> classOpt = classRepository.findById5(id);
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });
        response.put("classes", GetClassResponse.builder()
                .id(classes.getId())
                .semester_class_id(classes.getSemesterClass().getId())
                .security_code(classes.getSecurity_code())
                .name(classes.getName())
                .create_time(classes.getCreate_time())
                .update_time(classes.getUpdate_time())
                .build());

        response.put("teacher", GetUserResponse.builder()
                .id(classes.getTeacher().getId())
                .username(classes.getTeacher().getUser().getUsername())
                .email(classes.getTeacher().getUser().getEmail())
                .firstName(classes.getTeacher().getUser().getFirstName())
                .lastName(classes.getTeacher().getUser().getLastName())
                .profile_image_url(classes.getTeacher().getUser().getProfileImageUrl())
                .sex(classes.getTeacher().getUser().getSex())
                .phone(classes.getTeacher().getPhone())
                .address(classes.getTeacher().getUser().getAddress())
                .createTime(classes.getTeacher().getUser().getCreateTime())
                .build());
                SemesterClass semesterCouse = classes.getSemesterClass();

                response.put("course", GetCourseResponse.builder()
                        .id(semesterCouse.getCourse().getId())
                        .name(semesterCouse.getCourse().getName())
                        .description(semesterCouse.getCourse().getDescription())
                        .num_of_section(semesterCouse.getCourse().getNum_of_section())
                        .image_url(semesterCouse.getCourse().getImage_url())
                        .price(semesterCouse.getCourse().getPrice())
                       
                        .art_age_id(semesterCouse.getCourse().getArtAges().getId())
                        .art_type_id(semesterCouse.getCourse().getArtTypes().getId())
                        .art_level_id(semesterCouse.getCourse().getArtLevels().getId())
                        .create_time(semesterCouse.getCourse().getCreate_time())
                        .update_time(semesterCouse.getCourse().getUpdate_time())
                        .build());

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


        List<GetStudentResponse> listStudents = new ArrayList<>();
        List<ClassHasRegisterJoinSemesterClass> listClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository
                .findByClassesId3(id);
        listClassHasRegisterJoinSemesterClass.forEach(content -> {
            Long parent_idx = content.getStudent().getParent().getId();
            String parent_namex = content.getStudent().getParent().getUser().getUsername() + " - " + content.getStudent().getParent().getUser().getFirstName() + " "
                    + content.getStudent().getParent().getUser().getLastName();
            GetStudentResponse student = GetStudentResponse.builder()
                    .id(content.getStudent().getId())
                    .username(content.getStudent().getUser().getUsername())
                    .email(content.getStudent().getUser().getEmail())
                    .firstName(content.getStudent().getUser().getFirstName())
                    .lastName(content.getStudent().getUser().getLastName())
                    .profile_image_url(content.getStudent().getUser().getProfileImageUrl())
                    .sex(content.getStudent().getUser().getSex())
                    .phone(content.getStudent().getPhone())
                    .address(content.getStudent().getUser().getAddress())
                    .parent(parent_namex)
                    .parents(parent_idx)
                    .createTime(content.getStudent().getUser().getCreateTime())
                    .build();
            listStudents.add(student);
        });


        response.put("students", listStudents);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetClassResponse getClassById(Long id) {
        Optional<Classes> classOpt = classRepository.findById2(id);
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        return GetClassResponse.builder()
                .id(classes.getId())
                .semester_class_id(classes.getSemesterClass().getId())
                .security_code(classes.getSecurity_code())
                .name(classes.getName())
                .create_time(classes.getCreate_time())
                .update_time(classes.getUpdate_time())
                .build();
    }

    @Override
    public Long createClass(CreateClassRequest createClassRequest) {
        Optional<Teacher> teacherOpt = teacherRepository
                .findById(createClassRequest.getTeacher_id());
        Teacher teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.teacher.not_found");
        });

        Optional<SemesterClass> semesterClassOpt = semesterClassRepository.findById1(createClassRequest.getSemester_class_id());
        SemesterClass semester_class = semesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        List<Student> students = new ArrayList<>();
        createClassRequest.getStudent_ids().forEach(student_id -> {
            studentRepository.findById(student_id).<Runnable>map(
                    student -> () -> students.add(student))
                    .orElseThrow(() -> {
                        throw new EntityNotFoundException(String.format("exception.student.invalid",
                                student_id));
                    })
                    .run();
        });      

        Classes savedClass = Classes.builder()
                .semesterClass(semester_class)
                .teacher(teacher)
                .security_code(createClassRequest.getSecurity_code())
                .name(createClassRequest.getName())
                .link_meeting("https://meet.jit.si/" + createClassRequest.getSecurity_code())
                .build();
        classRepository.save(savedClass);

        return savedClass.getId();
    }

    @Override
    public Long removeClassById(Long id) {
        Optional<Classes> classOpt = classRepository.findById1(id);
        Classes classx = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        LocalDateTime time_now = LocalDateTime.now();
        if (time_now.isBefore(classx.getSemesterClass().getSemester().getEnd_time())) {
            throw new ArtAgeNotDeleteException("exception.Classes.not_delete");
        }

        classRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateClassById(Long id, CreateClassRequest createClassRequest) {
        Optional<Classes> classOpt = classRepository.findById1(id);
        Classes updatedClass = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        updatedClass.setName(createClassRequest.getName());
        return updatedClass.getId();
    }
}
