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

import com.app.kidsdrawing.dto.CreateTeacherTeachSemesterRequest;
import com.app.kidsdrawing.dto.GetTeacherTeachSemesterResponse;
import com.app.kidsdrawing.dto.GetUserRegisterTeachSemesterScheduleClassResponse;
import com.app.kidsdrawing.entity.LessonTime;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.SemesterClassRepository;
import com.app.kidsdrawing.repository.UserRegisterTeachSemesterRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.SemesterClassService;
import com.app.kidsdrawing.service.UserRegisterTeachSemesterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserRegisterTeachSemesterServiceImpl implements UserRegisterTeachSemesterService{
    
    private final UserRegisterTeachSemesterRepository userRegisterTeachSemesterRepository;
    private final SemesterClassRepository semesterClassRepository;
    private final UserRepository userRepository;
    private final SemesterClassService semesterCourseService;

    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacherTeachSemester() {
        List<GetTeacherTeachSemesterResponse> allTeacherTeachSemesterResponses = new ArrayList<>();
        List<UserRegisterTeachSemester> listTeacherTeachSemester = userRegisterTeachSemesterRepository.findAll();
        listTeacherTeachSemester.forEach(content -> {
            GetTeacherTeachSemesterResponse teacherTeachSemesterResponse = GetTeacherTeachSemesterResponse.builder()
                .id(content.getId())
                .teacher_id(content.getTeacher().getId())
                .semester_classes_id(content.getSemesterClass().getId())
                .time(content.getTime())
                .build();
            allTeacherTeachSemesterResponses.add(teacherTeachSemesterResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_semester", allTeacherTeachSemesterResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacherTeachSemesterBySemesterClassSchedule(Long id) {
        List<GetUserRegisterTeachSemesterScheduleClassResponse> allTeacherTeachSemesterResponses = new ArrayList<>();
        List<UserRegisterTeachSemester> listTeacherTeachSemester = userRegisterTeachSemesterRepository.findBySemesterClassId1(id);
        listTeacherTeachSemester.forEach(content -> {
            GetUserRegisterTeachSemesterScheduleClassResponse teacherTeachSemesterResponse = GetUserRegisterTeachSemesterScheduleClassResponse.builder()
                .id(content.getId())
                .build();
            allTeacherTeachSemesterResponses.add(teacherTeachSemesterResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_semester", allTeacherTeachSemesterResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetTeacherTeachSemesterResponse getTeacherTeachSemesterById(Long id) {
        Optional<UserRegisterTeachSemester> teacherTeachSemesterOpt = userRegisterTeachSemesterRepository.findById2(id);
        UserRegisterTeachSemester teacherTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherTeachSemester.not_found");
        });

        return GetTeacherTeachSemesterResponse.builder()
            .id(teacherTeachSemester.getId())
            .teacher_id(teacherTeachSemester.getTeacher().getId())
            .semester_classes_id(teacherTeachSemester.getSemesterClass().getId())
            .time(teacherTeachSemester.getTime())
            .build();
    }

    @Override 
    public Boolean checkScheduleForTeacher(Long teacher_id, Long semester_class_id) {
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

        System.out.println(lessonTimes);
        System.out.println("\n");

        List<Map<Integer, LessonTime>> lessonTimesAll = new ArrayList<>();
        List<UserRegisterTeachSemester> userRegisterTeachSemesters = userRegisterTeachSemesterRepository.findBySemester(semesterCouse.getSemester().getId(), teacher_id);
        userRegisterTeachSemesters.forEach(ele -> {
            ele.getSemesterClass().getSchedules().forEach(schedule -> {
                Map<Integer, LessonTime> item = new HashMap<>();
                item.put(schedule.getDate_of_week(), schedule.getLessonTime());
                lessonTimesAll.add(item);
            });
        });

        System.out.println(lessonTimesAll);
        System.out.println("\n");

        if (!Collections.disjoint(lessonTimes, lessonTimesAll)) {
            return false;
        }
        return true;
    }

    @Override
    public Long createTeacherTeachSemester(CreateTeacherTeachSemesterRequest createTeacherTeachSemesterRequest) {
        if (checkScheduleForTeacher(createTeacherTeachSemesterRequest.getTeacher_id(), createTeacherTeachSemesterRequest.getSemester_classes_id()) == false) {
            throw new EntityNotFoundException("exception.schedule.duplicate");
        }
        
        Optional <SemesterClass> semester_classOpt = semesterClassRepository.findById1(createTeacherTeachSemesterRequest.getSemester_classes_id());
        SemesterClass semesterCouse = semester_classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_class.not_found");
        });


        LocalDateTime time_now = LocalDateTime.now();

        if (time_now.isAfter((semesterCouse.getRegistration_time()))) {
            throw new EntityNotFoundException("exception.deadline.not_found");
        }

        Optional <User> teacherOpt = userRepository.findById1(createTeacherTeachSemesterRequest.getTeacher_id());
        User teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });
        
        UserRegisterTeachSemester savedTeacherTeachSemester = UserRegisterTeachSemester.builder()
                .semesterClass(semesterCouse)
                .teacher(teacher)
                .build();
        userRegisterTeachSemesterRepository.save(savedTeacherTeachSemester);

        semesterCourseService.updateSemesterClassMaxParticipantById(savedTeacherTeachSemester.getSemesterClass().getId());

        return savedTeacherTeachSemester.getId();
    }

    @Override
    public Long removeTeacherTeachSemesterById(Long id) {
        Optional<UserRegisterTeachSemester> teacherTeachSemesterOpt = userRegisterTeachSemesterRepository.findById1(id);
        teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherTeachSemester.not_found");
        });

        userRegisterTeachSemesterRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateTeacherTeachSemesterById(Long id, CreateTeacherTeachSemesterRequest createTeacherTeachSemesterRequest) {
        Optional<UserRegisterTeachSemester> teacherTeachSemesterOpt = userRegisterTeachSemesterRepository.findById1(id);
        UserRegisterTeachSemester updatedTeacherTeachSemester = teacherTeachSemesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherTeachSemester.not_found");
        });

        Optional <SemesterClass> semester_classOpt = semesterClassRepository.findById1(createTeacherTeachSemesterRequest.getSemester_classes_id());
        SemesterClass semesterCouse = semester_classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester_class.not_found");
        });

        Optional <User> teacherOpt = userRepository.findById1(createTeacherTeachSemesterRequest.getTeacher_id());
        User teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });

        updatedTeacherTeachSemester.setSemesterClass(semesterCouse);
        updatedTeacherTeachSemester.setTeacher(teacher);

        return updatedTeacherTeachSemester.getId();
    }
}
