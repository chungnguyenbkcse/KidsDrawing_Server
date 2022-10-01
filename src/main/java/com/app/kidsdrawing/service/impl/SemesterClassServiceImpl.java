package com.app.kidsdrawing.service.impl;

import java.time.LocalDateTime;
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

import com.app.kidsdrawing.dto.CreateSemesterClassRequest;
import com.app.kidsdrawing.dto.GetCourseTeacherResponse;
import com.app.kidsdrawing.dto.GetSemesterClassForScheduleClassResponse;
import com.app.kidsdrawing.dto.GetSemesterClassResponse;
import com.app.kidsdrawing.dto.GetSemesterClassStudentResponse;
import com.app.kidsdrawing.entity.Course;
import com.app.kidsdrawing.entity.Semester;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.exception.SemesterClassAlreadyCreateException;
import com.app.kidsdrawing.repository.CourseRepository;
import com.app.kidsdrawing.repository.ScheduleRepository;
import com.app.kidsdrawing.repository.SemesterClassRepository;
import com.app.kidsdrawing.repository.SemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinSemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterTeachSemesterRepository;
import com.app.kidsdrawing.service.SemesterClassService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SemesterClassServiceImpl implements SemesterClassService {

    private final SemesterClassRepository semesterClassRepository;
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;
    private final UserRegisterTeachSemesterRepository userRegisterTeachSemesterRepository;
    private final UserRegisterJoinSemesterRepository userRegisterJoinSemesterRepository;
    private final ScheduleRepository scheduleRepository;
    private static String schedule = "";

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
        List<SemesterClass> allSemesterClass = semesterClassRepository.findAll();
        allSemesterClass.forEach(semesterClass -> {
            if (semesterClass.getRegistration_time().isAfter(time_now) == false && semesterClass.getSemester().getStart_time().isAfter(time_now)) {
                schedule = "";
                scheduleRepository.findAll().forEach(schedule_item -> {
                    if (schedule_item.getSemesterClass().getId().compareTo(semesterClass.getId()) == 0) {
                        if (schedule.equals("")) {
                            schedule = schedule + "Thứ " + schedule_item.getDate_of_week() + " ("
                                    + schedule_item.getLessonTime().getStart_time().toString() + " - "
                                    + schedule_item.getLessonTime().getEnd_time().toString() + ")";
                        } else {
                            schedule = schedule + ", Thứ " + schedule_item.getDate_of_week() + " ("
                                    + schedule_item.getLessonTime().getStart_time().toString() + " - "
                                    + schedule_item.getLessonTime().getEnd_time().toString() + ")";
                        }
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
                        .registration_deadline(semesterClass.getSemester().getStart_time())
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
    public ResponseEntity<Map<String, Object>> getAllSemesterClassNewByStudentAndCourse(UUID id, UUID course_id) {
        List<GetSemesterClassStudentResponse> allSemesterClassResponses = new ArrayList<>();
        LocalDateTime time_now = LocalDateTime.now();
        List<SemesterClass> allSemesterClass = semesterClassRepository.findByCourseId(course_id);

        List<UserRegisterJoinSemester> userRegisterJoinSemesters = userRegisterJoinSemesterRepository
                .findByStudentId(id);
        List<SemesterClass> listSemesterClass = new ArrayList<>();
        userRegisterJoinSemesters.forEach(user_register_join_semester -> {
            if (user_register_join_semester.getSemesterClass().getRegistration_time().isAfter(time_now) == false && user_register_join_semester.getSemesterClass().getSemester().getStart_time().isAfter(time_now) && allSemesterClass.contains(user_register_join_semester.getSemesterClass())) {
                listSemesterClass.add(user_register_join_semester.getSemesterClass());
                schedule = "";
                scheduleRepository.findAll().forEach(schedule_item -> {
                    if (schedule_item.getSemesterClass().getId().compareTo(user_register_join_semester.getSemesterClass()
                    .getId()) == 0) {
                        if (schedule.equals("")) {
                            schedule = schedule + "Thứ " + schedule_item.getDate_of_week() + " ("
                                    + schedule_item.getLessonTime().getStart_time().toString() + " - "
                                    + schedule_item.getLessonTime().getEnd_time().toString() + ")";
                        } else {
                            schedule = schedule + ", Thứ " + schedule_item.getDate_of_week() + " ("
                                    + schedule_item.getLessonTime().getStart_time().toString() + " - "
                                    + schedule_item.getLessonTime().getEnd_time().toString() + ")";
                        }
                    }
                });
                if (user_register_join_semester.getStatus().equals("Completed") == true
                        && allSemesterClass.contains(user_register_join_semester.getSemesterClass())) {
                    GetSemesterClassStudentResponse semesterClassResponse = GetSemesterClassStudentResponse.builder()
                            .course_id(user_register_join_semester.getSemesterClass().getCourse().getId())
                            .semster_class_id(user_register_join_semester.getSemesterClass().getId())
                            .id(user_register_join_semester.getSemesterClass().getId())
                            .semester_name(user_register_join_semester.getSemesterClass().getSemester().getName())
                            .description(user_register_join_semester.getSemesterClass().getCourse().getDescription())
                            .image_url(user_register_join_semester.getSemesterClass().getCourse().getImage_url())
                            .name(user_register_join_semester.getSemesterClass().getName())
                            .course_name(user_register_join_semester.getSemesterClass().getCourse().getName())
                            .art_age_name(
                                    user_register_join_semester.getSemesterClass().getCourse().getArtAges().getName())
                            .art_level_name(
                                    user_register_join_semester.getSemesterClass().getCourse().getArtLevels().getName())
                            .art_type_name(
                                    user_register_join_semester.getSemesterClass().getCourse().getArtTypes().getName())
                            .price(user_register_join_semester.getSemesterClass().getCourse().getPrice())
                            .registration_deadline(
                                    user_register_join_semester.getSemesterClass().getSemester().getStart_time())
                            .num_of_section(
                                    user_register_join_semester.getSemesterClass().getCourse().getNum_of_section())
                            .schedule(schedule)
                            .max_participant(user_register_join_semester.getSemesterClass().getMax_participant())
                            .semester_id(user_register_join_semester.getSemesterClass().getSemester().getId())
                            .status("Payed")
                            .build();
                    allSemesterClassResponses.add(semesterClassResponse);
                } else if (user_register_join_semester.getStatus().equals("Not Payed Now") == true
                && allSemesterClass.contains(user_register_join_semester.getSemesterClass())){
                    GetSemesterClassStudentResponse semesterClassResponse = GetSemesterClassStudentResponse.builder()
                            .course_id(user_register_join_semester.getSemesterClass().getCourse().getId())
                            .semster_class_id(user_register_join_semester.getSemesterClass().getId())
                            .id(user_register_join_semester.getSemesterClass().getId())
                            .semester_name(user_register_join_semester.getSemesterClass().getSemester().getName())
                            .description(user_register_join_semester.getSemesterClass().getCourse().getDescription())
                            .image_url(user_register_join_semester.getSemesterClass().getCourse().getImage_url())
                            .name(user_register_join_semester.getSemesterClass().getName())
                            .course_name(user_register_join_semester.getSemesterClass().getCourse().getName())
                            .art_age_name(
                                    user_register_join_semester.getSemesterClass().getCourse().getArtAges().getName())
                            .art_level_name(
                                    user_register_join_semester.getSemesterClass().getCourse().getArtLevels().getName())
                            .art_type_name(
                                    user_register_join_semester.getSemesterClass().getCourse().getArtTypes().getName())
                            .price(user_register_join_semester.getSemesterClass().getCourse().getPrice())
                            .registration_deadline(
                                    user_register_join_semester.getSemesterClass().getSemester().getStart_time())
                            .num_of_section(
                                    user_register_join_semester.getSemesterClass().getCourse().getNum_of_section())
                            .schedule(schedule)
                            .max_participant(user_register_join_semester.getSemesterClass().getMax_participant())
                            .semester_id(user_register_join_semester.getSemesterClass().getSemester().getId())
                            .status("Not Payed now")
                            .build();
                    allSemesterClassResponses.add(semesterClassResponse);
                }
            }
        });

        allSemesterClass.forEach(semester_class -> {
            if (listSemesterClass.contains(semester_class) == false
                    && semester_class.getRegistration_time().isAfter(time_now) == false && semester_class.getSemester().getStart_time().isAfter(time_now)) {
                        schedule = "";
                scheduleRepository.findAll().forEach(schedule_item -> {
                    if (schedule_item.getSemesterClass().getId().compareTo(semester_class.getId()) == 0) {
                        if (schedule == "") {
                            schedule = schedule + "Thứ " + schedule_item.getDate_of_week() + " ("
                                    + schedule_item.getLessonTime().getStart_time().toString() + " - "
                                    + schedule_item.getLessonTime().getEnd_time().toString() + ")";
                        } else {
                            schedule = schedule + ", Thứ " + schedule_item.getDate_of_week() + " ("
                                    + schedule_item.getLessonTime().getStart_time().toString() + " - "
                                    + schedule_item.getLessonTime().getEnd_time().toString() + ")";
                        }
                    }
                });
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
                        .registration_deadline(semester_class.getSemester().getStart_time())
                        .num_of_section(semester_class.getCourse().getNum_of_section())
                        .schedule(schedule)
                        .max_participant(semester_class.getMax_participant())
                        .semester_id(semester_class.getSemester().getId())
                        .status("Not Payed")
                        .build();
                allSemesterClassResponses.add(semesterClassResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassHistoryOfStudent(UUID id) {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> userRegisterJoinSemester = userRegisterJoinSemesterRepository
                .findByStudentId(id);
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
    public ResponseEntity<Map<String, Object>> getAllSemesterClassPresentOfStudent(UUID id) {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<UserRegisterJoinSemester> userRegisterJoinSemester = userRegisterJoinSemesterRepository
                .findByStudentId(id);
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
    public GetSemesterClassResponse getSemesterClassById(UUID id) {
        Optional<SemesterClass> semesterClassOpt = semesterClassRepository.findById(id);
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
    public ResponseEntity<Map<String, Object>> getAllSemesterClassBySemester(UUID id) {
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
    public ResponseEntity<Map<String, Object>> getAllSemesterClassBySemesterScheduleClass(UUID id) {
        List<GetSemesterClassForScheduleClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<SemesterClass> pageSemesterClass = semesterClassRepository.findAll();
        pageSemesterClass.forEach(semesterClass -> {
            if (semesterClass.getSemester().getId().compareTo(id) == 0) {
                GetSemesterClassForScheduleClassResponse semesterClassResponse = GetSemesterClassForScheduleClassResponse.builder()
                        .id(semesterClass.getId())
                        .build();
                allSemesterClassResponses.add(semesterClassResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassByCourse(UUID id) {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<SemesterClass> pageSemesterClass = semesterClassRepository.findAll();
        pageSemesterClass.forEach(semesterClass -> {
            if (semesterClass.getCourse().getId().compareTo(id) == 0) {
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
    public GetSemesterClassResponse createSemesterClass(CreateSemesterClassRequest createSemesterClassRequest) {
        Optional<Semester> semesterOpt = semesterRepository.findById(createSemesterClassRequest.getSemester_id());
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester.not_found");
        });

        Optional<Course> courseOpt = courseRepository.findById(createSemesterClassRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        if (semesterClassRepository.existsByName(createSemesterClassRequest.getName())) {
            throw new SemesterClassAlreadyCreateException("exception.semester_name.semester_class_taken");
        }

        SemesterClass savedSemesterClass = SemesterClass.builder()
                .semester(semester)
                .course(course)
                .name(createSemesterClassRequest.getName())
                .registration_time(createSemesterClassRequest.getRegistration_time())
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
    public UUID removeSemesterClassById(UUID id) {
        Optional<SemesterClass> SemesterClassOpt = semesterClassRepository.findById(id);
        SemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        semesterClassRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateSemesterClassById(UUID id, CreateSemesterClassRequest createSemesterClassRequest) {
        Optional<SemesterClass> SemesterClassOpt = semesterClassRepository.findById(id);
        SemesterClass updatedSemesterClass = SemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        Optional<Semester> semesterOpt = semesterRepository.findById(createSemesterClassRequest.getSemester_id());
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester.not_found");
        });

        Optional<Course> courseOpt = courseRepository.findById(createSemesterClassRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        if (createSemesterClassRequest.getName().equals(updatedSemesterClass.getName()) == false) {
            if (semesterClassRepository.existsByName(createSemesterClassRequest.getName())) {
                throw new SemesterClassAlreadyCreateException("exception.semester_name.semester_class_taken");
            }
        }

        updatedSemesterClass.setSemester(semester);
        updatedSemesterClass.setCourse(course);
        updatedSemesterClass.setName(createSemesterClassRequest.getName());
        updatedSemesterClass.setRegistration_time(createSemesterClassRequest.getRegistration_time());
        semesterClassRepository.save(updatedSemesterClass);

        return updatedSemesterClass.getId();
    }

    @Override
    public String updateSemesterClassMaxParticipantById(UUID id) {
        Optional<SemesterClass> SemesterClassOpt = semesterClassRepository.findById(id);
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
