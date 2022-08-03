package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
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
import com.app.kidsdrawing.dto.GetClassResponse;
import com.app.kidsdrawing.dto.GetCourseResponse;
import com.app.kidsdrawing.dto.GetScheduleItemResponse;
import com.app.kidsdrawing.dto.GetSemesterResponse;
import com.app.kidsdrawing.dto.GetUserResponse;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.entity.Class;
import com.app.kidsdrawing.entity.Schedule;
import com.app.kidsdrawing.entity.ScheduleItem;
import com.app.kidsdrawing.entity.SemesterCourse;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.TeacherTeachSemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinSemesterRepository;
import com.app.kidsdrawing.repository.ClassRepository;
import com.app.kidsdrawing.repository.ScheduleItemRepository;
import com.app.kidsdrawing.repository.ScheduleRepository;
import com.app.kidsdrawing.repository.SemesterCourseRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.ClassesService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ClassesServiceImpl implements ClassesService{
    
    private final ClassRepository classRepository;
    private final TeacherTeachSemesterRepository teacherTeachSemesterRepository;
    private final UserRepository userRepository;
    private final UserRegisterJoinSemesterRepository userRegisterJoinSemesterRepository;
    private final SemesterCourseRepository semesterCourseRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleItemRepository scheduleItemRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllClass() {
        List<GetClassResponse> allClassResponses = new ArrayList<>();
        List<Class> listClass = classRepository.findAll();
        listClass.forEach(content -> {
            GetClassResponse classResponse = GetClassResponse.builder()
                .id(content.getId())
                .creator_id(content.getUser().getId())
                .registration_id(content.getTeachSemester().getId())
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
    public ResponseEntity<Map<String, Object>> getInforDetailAllClass() {
        List<GetClassResponse> allClassResponses = new ArrayList<>();
        List<GetSemesterResponse> allSemesterResponses = new ArrayList<>();
        List<GetCourseResponse> allCourseResponses = new ArrayList<>();
        List<GetUserResponse> allUserResponses = new ArrayList<>();
        List<List<GetScheduleItemResponse>> allScheduleItemOfScheduleResponses = new ArrayList<>();
        List<Class> listClass = classRepository.findAll();
        listClass.forEach(content -> {
            GetClassResponse classResponse = GetClassResponse.builder()
                .id(content.getId())
                .creator_id(content.getUser().getId())
                .registration_id(content.getTeachSemester().getId())
                .security_code(content.getSecurity_code())
                .name(content.getName())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allClassResponses.add(classResponse);

            Optional<UserRegisterTeachSemester> teacherTeachSemesterOpt = teacherTeachSemesterRepository.findById(content.getTeachSemester().getId());
            UserRegisterTeachSemester userRegisterTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
                throw new EntityNotFoundException("exception.TeacherTeachSemester.not_found");
            });

            Optional <SemesterCourse> semester_courseOpt = semesterCourseRepository.findById(userRegisterTeachSemester.getSemesterCourse().getId());
            SemesterCourse semesterCouse = semester_courseOpt.orElseThrow(() -> {
                throw new EntityNotFoundException("exception.semester_course.not_found");
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
                    .max_participant(semesterCouse.getCourse().getMax_participant())
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

            List<String> parent_names = new ArrayList<>();
            userRegisterTeachSemester.getTeacher().getParents().forEach(parent -> {
                String parent_name = parent.getUsername();
                parent_names.add(parent_name);
            });

            GetUserResponse userResponse = GetUserResponse.builder()
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
                    .parents(parent_names)
                    .createTime(userRegisterTeachSemester.getTeacher().getCreateTime())
                    .build();
            allUserResponses.add(userResponse);

            Optional<Schedule> scheduleOpt = scheduleRepository.findById(semesterCouse.getSchedule().getId());
            Schedule schedule = scheduleOpt.orElseThrow(() -> {
                throw new EntityNotFoundException("exception.Schedule.not_found");
            });

            List<GetScheduleItemResponse> allScheduleItemResponses = new ArrayList<>();
            List<ScheduleItem> pageScheduleItem = scheduleItemRepository.findAll();
            pageScheduleItem.forEach(schedule_item -> {
                if (schedule_item.getSchedule().getId() == schedule.getId()) {
                    GetScheduleItemResponse scheduleItemResponse = GetScheduleItemResponse.builder()
                        .id(schedule_item.getId())
                        .schedule_id(schedule_item.getSchedule().getId())
                        .lesson_time(schedule_item.getLessonTime().getId())
                        .date_of_week(schedule_item.getDate_of_week())
                        .build();
                    allScheduleItemResponses.add(scheduleItemResponse);
                }
            });
            allScheduleItemOfScheduleResponses.add(allScheduleItemResponses);
            
        });

        Map<String, Object> response = new HashMap<>();
        response.put("classes", allClassResponses);
        response.put("courses", allCourseResponses);
        response.put("semesters", allSemesterResponses);
        response.put("teachers", allUserResponses);
        response.put("schedules", allScheduleItemOfScheduleResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
            .registration_id(classes.getTeachSemester().getId())
            .security_code(classes.getSecurity_code())
            .name(classes.getName())
            .create_time(classes.getCreate_time())
            .update_time(classes.getUpdate_time())
            .build()
        );

        Optional<UserRegisterTeachSemester> teacherTeachSemesterOpt = teacherTeachSemesterRepository.findById(classes.getTeachSemester().getId());
        UserRegisterTeachSemester userRegisterTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherTeachSemester.not_found");
        });

        List<String> parent_names = new ArrayList<>();
        userRegisterTeachSemester.getTeacher().getParents().forEach(parent -> {
            String parent_name = parent.getUsername();
            parent_names.add(parent_name);
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
            .parents(parent_names)
            .createTime(userRegisterTeachSemester.getTeacher().getCreateTime())
            .build()
        );

        Optional <SemesterCourse> semester_courseOpt = semesterCourseRepository.findById(userRegisterTeachSemester.getSemesterCourse().getId());
        SemesterCourse semesterCouse = semester_courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_course.not_found");
        });

        response.put("course", GetCourseResponse.builder()
            .id(semesterCouse.getCourse().getId())
            .name(semesterCouse.getCourse().getName())
            .description(semesterCouse.getCourse().getDescription())
            .max_participant(semesterCouse.getCourse().getMax_participant())
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
        
        Optional<Schedule> scheduleOpt = scheduleRepository.findById(semesterCouse.getSchedule().getId());
        Schedule schedule = scheduleOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Schedule.not_found");
        });
        
        List<GetScheduleItemResponse> allScheduleItemResponses = new ArrayList<>();
        List<ScheduleItem> pageScheduleItem = scheduleItemRepository.findAll();
        pageScheduleItem.forEach(schedule_item -> {
            if (schedule_item.getSchedule().getId() == schedule.getId()) {
                GetScheduleItemResponse scheduleItemResponse = GetScheduleItemResponse.builder()
                    .id(schedule_item.getId())
                    .schedule_id(schedule_item.getSchedule().getId())
                    .lesson_time(schedule_item.getLessonTime().getId())
                    .date_of_week(schedule_item.getDate_of_week())
                    .build();
                allScheduleItemResponses.add(scheduleItemResponse);
            }
        });

        response.put("schedule", allScheduleItemResponses);

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
            .registration_id(classes.getTeachSemester().getId())
            .security_code(classes.getSecurity_code())
            .name(classes.getName())
            .create_time(classes.getCreate_time())
            .update_time(classes.getUpdate_time())
            .build();
    }

    @Override
    public Long createClass(CreateClassRequest createClassRequest) {
        Optional <UserRegisterTeachSemester> teacherTeachSemesterOpt = teacherTeachSemesterRepository.findById(createClassRequest.getRegistration_id());
        UserRegisterTeachSemester teacherTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.teacher_teach_semester.not_found");
        });

        List<UserRegisterJoinSemester> validUserRegisterSemesters = new ArrayList<>();
        createClassRequest.getUser_register_join_semester().forEach(user_register_join_semester_id -> {
            userRegisterJoinSemesterRepository.findById(user_register_join_semester_id).<Runnable>map(user_register_join_semester -> () -> validUserRegisterSemesters.add(user_register_join_semester))
                    .orElseThrow(() -> {
                        throw new EntityNotFoundException(String.format("exception.user_register_join_semester.invalid", user_register_join_semester_id));
                    })
                    .run();
        });

        Optional <User> userOpt = userRepository.findById(createClassRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });
        
        Class savedClass = Class.builder()
                .user(user)
                .teachSemester(teacherTeachSemester)
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

        Optional <UserRegisterTeachSemester> teacherTeachSemesterOpt = teacherTeachSemesterRepository.findById(createClassRequest.getRegistration_id());
        UserRegisterTeachSemester teacherTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.teacher_teach_semester.not_found");
        });

        Optional <User> userOpt = userRepository.findById(createClassRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        updatedClass.setName(createClassRequest.getName());
        updatedClass.setSecurity_code(createClassRequest.getSecurity_code());
        updatedClass.setUser(user);
        updatedClass.setTeachSemester(teacherTeachSemester);

        return updatedClass.getId();
    }
}
