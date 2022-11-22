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


import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateReviewTeacherLeaveRequest;
import com.app.kidsdrawing.dto.CreateTeacherLeaveRequest;
import com.app.kidsdrawing.dto.GetTeacherLeaveResponse;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.LessonTime;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.entity.TeacherLeave;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.repository.TeacherLeaveRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.TeacherLeaveService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TeacherLeaveServiceImpl implements TeacherLeaveService{
    
    private final TeacherLeaveRepository teacherLeaveRepository;
    private final SectionRepository sectionRepository;
    private final ClassesRepository classRepository;
    private final UserRepository userRepository;
    private static int total_section_count = 0;

    public List<LocalDateTime> getScheduleDetailOfClass(Classes classes, int section_number) {

        UserRegisterTeachSemester userRegisterTeachSemester = classes.getUserRegisterTeachSemester();

        SemesterClass semesterCouse = userRegisterTeachSemester.getSemesterClass();

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

        List<LocalDateTime> res = new ArrayList<>();
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
            } else {
                for (int idx = 0; idx < semesterCouse.getSchedules().size(); idx++) {
                    Integer dayOfWeek = dayOfWeeks.get(idx);
                    LocalTime start_lessontime = lessonTimeResponses.get(idx).getStart_time();
                    LocalTime end_lessontime = lessonTimeResponses.get(idx).getEnd_time();
                    // LocalDateTime end_time = semester.getStart_time().plusWeeks(total_week);
                    System.out.printf("Day_of_week: %d\n", dayOfWeek);
                    List<LocalDateTime> lesson_time_in_day = new ArrayList<>();
                    if (total_section_count > 0) {
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

                    if (total_section_count < total_section) {
                        LocalDate start_date = start_time.toLocalDate();
                        if (list_holiday.contains(start_date) == false) {
                            lesson_time_in_day.add(start_lessontime.atDate(start_date));
                            lesson_time_in_day.add(end_lessontime.atDate(start_date));

                            if (total_section_count == section_number - 1) {
                                return lesson_time_in_day;
                            }
                            total_section_count++;
                        }
                    }
                }
            }
        }

        return res;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacherLeave() {
        List<GetTeacherLeaveResponse> allTeacherLeaveResponses = new ArrayList<>();
        List<TeacherLeave> listTeacherLeave = teacherLeaveRepository.findAll();
        listTeacherLeave.forEach(content -> {
            Optional<Classes> classOpt = classRepository.findById5(content.getClasses().getId());
            Classes classes = classOpt.orElseThrow(() -> {
                throw new EntityNotFoundException("exception.Class.not_found");
            });
            List<LocalDateTime> time = getScheduleDetailOfClass(classes, content.getSection().getNumber());

            GetTeacherLeaveResponse TeacherLeaveResponse = GetTeacherLeaveResponse.builder()
                .id(content.getId())
                .teacher_id(content.getTeacher().getId())
                .teacher_name(content.getTeacher().getFirstName() + " " + content.getTeacher().getLastName())
                .reviewer_id(content.getReviewer().getId())
                .section_id(content.getSection().getId())
                .section_number(content.getSection().getNumber())
                .section_name(content.getSection().getName())
                .classes_id(content.getClasses().getId())
                .start_time(time.get(0))
                .end_time(time.get(1))
                .class_name(content.getClasses().getName())
                .substitute_teacher_id(content.getSubstitute_teacher().getId())
                .substitute_teacher_name(content.getSubstitute_teacher().getFirstName() + " " + content.getSubstitute_teacher().getLastName())
                .status(content.getStatus())
                .description(content.getDescription())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allTeacherLeaveResponses.add(TeacherLeaveResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_leave", allTeacherLeaveResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getTeacherLeaveByTeacher(Long id) {
        List<GetTeacherLeaveResponse> allTeacherLeaveResponses = new ArrayList<>();
        List<TeacherLeave> listTeacherLeave = teacherLeaveRepository.findByTeacherId2(id);
        listTeacherLeave.forEach(content -> {
            Optional<Classes> classOpt = classRepository.findById5(content.getClasses().getId());
            Classes classes = classOpt.orElseThrow(() -> {
                throw new EntityNotFoundException("exception.Class.not_found");
            });
            List<LocalDateTime> time = getScheduleDetailOfClass(classes, content.getSection().getNumber());
            GetTeacherLeaveResponse TeacherLeaveResponse = GetTeacherLeaveResponse.builder()
                .id(content.getId())
                .teacher_id(content.getTeacher().getId())
                .start_time(time.get(0))
                .end_time(time.get(1))
                .teacher_name(content.getTeacher().getFirstName() + " " + content.getTeacher().getLastName())
                .reviewer_id(content.getReviewer().getId())
                .section_id(content.getSection().getId())
                .section_number(content.getSection().getNumber())
                .section_name(content.getSection().getName())
                .classes_id(content.getClasses().getId())
                .class_name(content.getClasses().getName())
                .substitute_teacher_id(content.getSubstitute_teacher().getId())
                .substitute_teacher_name(content.getSubstitute_teacher().getFirstName() + " " + content.getSubstitute_teacher().getLastName())
                .status(content.getStatus())
                .description(content.getDescription())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allTeacherLeaveResponses.add(TeacherLeaveResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_leave", allTeacherLeaveResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getTeacherLeaveByClassId(Long id) {
        List<GetTeacherLeaveResponse> allTeacherLeaveResponses = new ArrayList<>();
        List<TeacherLeave> listTeacherLeave = teacherLeaveRepository.findByClassesId2(id);
        listTeacherLeave.forEach(content -> {
            Optional<Classes> classOpt = classRepository.findById5(content.getClasses().getId());
            Classes classes = classOpt.orElseThrow(() -> {
                throw new EntityNotFoundException("exception.Class.not_found");
            });
            List<LocalDateTime> time = getScheduleDetailOfClass(classes, content.getSection().getNumber());
                GetTeacherLeaveResponse TeacherLeaveResponse = GetTeacherLeaveResponse.builder()
                    .id(content.getId())
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getFirstName() + " " + content.getTeacher().getLastName())
                    .reviewer_id(content.getReviewer().getId())
                    .section_id(content.getSection().getId())
                    .start_time(time.get(0))
                    .end_time(time.get(1))
                    .section_number(content.getSection().getNumber())
                    .section_name(content.getSection().getName())
                    .classes_id(content.getClasses().getId())
                    .class_name(content.getClasses().getName())
                    .substitute_teacher_id(content.getSubstitute_teacher().getId())
                    .substitute_teacher_name(content.getSubstitute_teacher().getFirstName() + " " + content.getSubstitute_teacher().getLastName())
                    .status(content.getStatus())
                    .description(content.getDescription())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allTeacherLeaveResponses.add(TeacherLeaveResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_leave", allTeacherLeaveResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetTeacherLeaveResponse getTeacherLeaveById(Long id) {
        Optional<TeacherLeave> teacherLeaveOpt = teacherLeaveRepository.findById2(id);
        TeacherLeave teacherLeave = teacherLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherLeave.not_found");
        });

        Optional<Classes> classOpt = classRepository.findById5(teacherLeave.getClasses().getId());
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });
        List<LocalDateTime> time = getScheduleDetailOfClass(classes, teacherLeave.getSection().getNumber());

        return GetTeacherLeaveResponse.builder()
            .id(teacherLeave.getId())
            .teacher_id(teacherLeave.getTeacher().getId())
            .teacher_name(teacherLeave.getTeacher().getFirstName() + " " + teacherLeave.getTeacher().getLastName())
            .reviewer_id(teacherLeave.getReviewer().getId())
            .section_id(teacherLeave.getSection().getId())
            .start_time(time.get(0))
            .end_time(time.get(1))
            .section_name(teacherLeave.getSection().getName())
            .classes_id(teacherLeave.getClasses().getId())
            .section_number(teacherLeave.getSection().getNumber())
            .class_name(teacherLeave.getClasses().getName())
            .substitute_teacher_id(teacherLeave.getSubstitute_teacher().getId())
            .substitute_teacher_name(teacherLeave.getSubstitute_teacher().getFirstName()  + " " + teacherLeave.getSubstitute_teacher().getLastName())
            .status(teacherLeave.getStatus())
            .description(teacherLeave.getDescription())
            .create_time(teacherLeave.getCreate_time())
            .update_time(teacherLeave.getUpdate_time())
            .build();
    }

    @Override
    public GetTeacherLeaveResponse createTeacherLeave(CreateTeacherLeaveRequest createTeacherLeaveRequest) {

        Optional <User> teacherOpt = userRepository.findById1(createTeacherLeaveRequest.getTeacher_id());
        User teacher = teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });

        Optional <User> reivewerOpt = userRepository.findById1(createTeacherLeaveRequest.getTeacher_id());
        User reviewer = reivewerOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });

        Optional <User> substitute_teacherOpt = userRepository.findById1(createTeacherLeaveRequest.getTeacher_id());
        User substitute_teacher = substitute_teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_substitute_teacher.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById1(createTeacherLeaveRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <Classes> classOpt = classRepository.findById1(createTeacherLeaveRequest.getClasses_id());
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.class.not_found");
        });
        
        TeacherLeave savedTeacherLeave = TeacherLeave.builder()
                .classes(classes)
                .section(section)
                .teacher(teacher)
                .reviewer(reviewer)
                .status("Not approved now")
                .substitute_teacher(substitute_teacher)
                .description(createTeacherLeaveRequest.getDescription())
                .build();
        teacherLeaveRepository.save(savedTeacherLeave);

        return GetTeacherLeaveResponse.builder()
        .id(savedTeacherLeave.getId())
        .teacher_id(savedTeacherLeave.getTeacher().getId())
        .teacher_name(savedTeacherLeave.getTeacher().getFirstName() + " " + savedTeacherLeave.getTeacher().getLastName())
        .reviewer_id(savedTeacherLeave.getReviewer().getId())
        .section_id(savedTeacherLeave.getSection().getId())
        .section_name(savedTeacherLeave.getSection().getName())
        .classes_id(savedTeacherLeave.getClasses().getId())
        .section_number(savedTeacherLeave.getSection().getNumber())
        .class_name(savedTeacherLeave.getClasses().getName())
        .substitute_teacher_id(savedTeacherLeave.getSubstitute_teacher().getId())
        .substitute_teacher_name(savedTeacherLeave.getSubstitute_teacher().getFirstName()  + " " + savedTeacherLeave.getSubstitute_teacher().getLastName())
        .status(savedTeacherLeave.getStatus())
        .description(savedTeacherLeave.getDescription())
        .create_time(savedTeacherLeave.getCreate_time())
        .update_time(savedTeacherLeave.getUpdate_time())
        .build();
    }

    @Override
    public Long removeTeacherLeaveById(Long id) {
        Optional<TeacherLeave> teacherLeaveOpt = teacherLeaveRepository.findById1(id);
        teacherLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherLeave.not_found");
        });

        teacherLeaveRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateTeacherLeaveById(Long id, CreateTeacherLeaveRequest createTeacherLeaveRequest) {
        Optional<TeacherLeave> TeacherLeaveOpt = teacherLeaveRepository.findById1(id);
        TeacherLeave updatedTeacherLeave = TeacherLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherLeave.not_found");
        });

        Optional <User> substitute_teacherOpt = userRepository.findById1(createTeacherLeaveRequest.getTeacher_id());
        User substitute_teacher = substitute_teacherOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_substitute_teacher.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById1(createTeacherLeaveRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        updatedTeacherLeave.setSection(section);
        updatedTeacherLeave.setSubstitute_teacher(substitute_teacher);
        updatedTeacherLeave.setDescription(createTeacherLeaveRequest.getDescription());
        teacherLeaveRepository.save(updatedTeacherLeave);

        return updatedTeacherLeave.getId();
    }

    @Override
    public GetTeacherLeaveResponse updateStatusTeacherLeaveById(Long id, CreateReviewTeacherLeaveRequest createReviewTeacherLeaveRequest) {
        Optional<TeacherLeave> TeacherLeaveOpt = teacherLeaveRepository.findById2(id);
        TeacherLeave updatedTeacherLeave = TeacherLeaveOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherLeave.not_found");
        });

        if (updatedTeacherLeave.getStatus().equals("Teacher approved")) {
            if (createReviewTeacherLeaveRequest.getStatus().equals("Admin approved")) {
                updatedTeacherLeave.setStatus("Approved");
                teacherLeaveRepository.save(updatedTeacherLeave);
            }
            else {
                updatedTeacherLeave.setStatus(createReviewTeacherLeaveRequest.getStatus());
                teacherLeaveRepository.save(updatedTeacherLeave);
            }
        }
        else if (updatedTeacherLeave.getStatus().equals("Admin approved")) {
            if (createReviewTeacherLeaveRequest.getStatus().equals("Teacher approved")) {
                updatedTeacherLeave.setStatus("Approved");
                teacherLeaveRepository.save(updatedTeacherLeave);
            }
            else {
                updatedTeacherLeave.setStatus(createReviewTeacherLeaveRequest.getStatus());
                teacherLeaveRepository.save(updatedTeacherLeave);
            }
        }
        else if (updatedTeacherLeave.getStatus().equals("Not approve now")) {
            updatedTeacherLeave.setStatus(createReviewTeacherLeaveRequest.getStatus());
            teacherLeaveRepository.save(updatedTeacherLeave);
        }

        return GetTeacherLeaveResponse.builder()
        .id(updatedTeacherLeave.getId())
        .teacher_id(updatedTeacherLeave.getTeacher().getId())
        .teacher_name(updatedTeacherLeave.getTeacher().getFirstName() + " " + updatedTeacherLeave.getTeacher().getLastName())
        .reviewer_id(updatedTeacherLeave.getReviewer().getId())
        .section_id(updatedTeacherLeave.getSection().getId())
        .section_name(updatedTeacherLeave.getSection().getName())
        .classes_id(updatedTeacherLeave.getClasses().getId())
        .section_number(updatedTeacherLeave.getSection().getNumber())
        .class_name(updatedTeacherLeave.getClasses().getName())
        .substitute_teacher_id(updatedTeacherLeave.getSubstitute_teacher().getId())
        .substitute_teacher_name(updatedTeacherLeave.getSubstitute_teacher().getFirstName()  + " " + updatedTeacherLeave.getSubstitute_teacher().getLastName())
        .status(updatedTeacherLeave.getStatus())
        .description(updatedTeacherLeave.getDescription())
        .create_time(updatedTeacherLeave.getCreate_time())
        .update_time(updatedTeacherLeave.getUpdate_time())
        .build();
    }
}
