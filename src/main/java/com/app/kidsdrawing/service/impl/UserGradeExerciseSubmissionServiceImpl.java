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

import com.app.kidsdrawing.dto.CreateUserGradeExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetUserGradeExerciseSubmissionResponse;
import com.app.kidsdrawing.entity.UserGradeExerciseSubmission;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.entity.UserGradeExerciseSubmissionKey;
import com.app.kidsdrawing.entity.ExerciseSubmission;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassHasRegisterJoinSemesterClassRepository;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.ExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.UserGradeExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.UserGradeExerciseSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserGradeExerciseSubmissionServiceImpl implements UserGradeExerciseSubmissionService{
    
    private final UserGradeExerciseSubmissionRepository userGradeExerciseSubmissionRepository;
    private final UserRepository userRepository;
    private final ExerciseSubmissionRepository exerciseSubmissionRepository;
    private final ClassesRepository classRepository;
    private final ClassHasRegisterJoinSemesterClassRepository classHasRegisterJoinSemesterClassRepository;
    private static Float exam = (float) 0;
    private static Float middle = (float) 0;
    private static Float final_exam = (float) 0;

    private static int count_exam =  0;

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmission() {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                .teacher_id(content.getTeacher().getId())
                .teacher_name(content.getTeacher().getFirstName() + content.getTeacher().getLastName())
                .student_id(content.getExerciseSubmission().getStudent().getId())
                .student_name(content.getExerciseSubmission().getStudent().getFirstName() + " " + content.getExerciseSubmission().getStudent().getLastName())
                .exercise_name(content.getExerciseSubmission().getExercise().getName())
                .time_submit(content.getExerciseSubmission().getUpdate_time())
                .image_url(content.getExerciseSubmission().getImage_url())
                .description(content.getExerciseSubmission().getExercise().getDescription())
                .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                .exercise_submission_id(content.getExerciseSubmission().getId())
                .feedback(content.getFeedback())
                .score(content.getScore())
                .time(content.getTime())
                .build();
            allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override 
    public ResponseEntity<Map<String, Object>> getFinalGradeAndReviewForStudentAndClasses(UUID student_id, UUID classes_id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        exam = (float) 0;
        middle = (float) 0;
        final_exam = (float) 0;
        count_exam = 0;
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getSection().getClasses().getId().compareTo(classes_id) == 0 && content.getExerciseSubmission().getStudent().getId().compareTo(student_id) == 0) {
                if (content.getExerciseSubmission().getExercise().getExerciseLevel().getName().equals("exam")) {
                    exam = exam + content.getScore() ;
                    count_exam ++;
                }
                else if (content.getExerciseSubmission().getExercise().getExerciseLevel().getName().equals("middle")) {
                    middle = middle + content.getScore() ;
                }
                else {
                    final_exam = final_exam + content.getScore() ;
                }
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getFirstName() + content.getTeacher().getLastName())
                    .student_id(content.getExerciseSubmission().getStudent().getId())
                    .student_name(content.getExerciseSubmission().getStudent().getFirstName() + " " + content.getExerciseSubmission().getStudent().getLastName())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .image_url(content.getExerciseSubmission().getImage_url())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        response.put("final_grade", (exam * 10 / count_exam + middle * 30 + final_exam * 60) / 100);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByTeacherId(UUID id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getTeacher().getId().compareTo(id) == 0){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getFirstName() + content.getTeacher().getLastName())
                    .student_id(content.getExerciseSubmission().getStudent().getId())
                    .student_name(content.getExerciseSubmission().getStudent().getFirstName() + " " + content.getExerciseSubmission().getStudent().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .image_url(content.getExerciseSubmission().getImage_url())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentId(UUID id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getStudent().getId().compareTo(id) == 0){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getFirstName() + content.getTeacher().getLastName())
                    .student_id(content.getExerciseSubmission().getStudent().getId())
                    .student_name(content.getExerciseSubmission().getStudent().getFirstName() + " " + content.getExerciseSubmission().getStudent().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .image_url(content.getExerciseSubmission().getImage_url())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByClassId(UUID id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getSection().getClasses().getId().compareTo(id) == 0){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getFirstName() + content.getTeacher().getLastName())
                    .student_id(content.getExerciseSubmission().getStudent().getId())
                    .student_name(content.getExerciseSubmission().getStudent().getFirstName() + " " + content.getExerciseSubmission().getStudent().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .image_url(content.getExerciseSubmission().getImage_url())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseId(UUID id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getId().compareTo(id) == 0){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getFirstName() + content.getTeacher().getLastName())
                    .student_id(content.getExerciseSubmission().getStudent().getId())
                    .student_name(content.getExerciseSubmission().getStudent().getFirstName() + " " + content.getExerciseSubmission().getStudent().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .image_url(content.getExerciseSubmission().getImage_url())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseSubmissionId(UUID id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getId().compareTo(id) == 0){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getFirstName() + content.getTeacher().getLastName())
                    .student_id(content.getExerciseSubmission().getStudent().getId())
                    .student_name(content.getExerciseSubmission().getStudent().getFirstName() + " " + content.getExerciseSubmission().getStudent().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .image_url(content.getExerciseSubmission().getImage_url())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override 
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseAndClass(UUID exercise_id, UUID classes_id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        Optional<Classes> classOpt = classRepository.findById2(classes_id);
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });
        List<User> user_graded = new ArrayList<>();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getSection().getClasses().getId().compareTo(classes_id) == 0 && content.getExerciseSubmission().getExercise().getId().compareTo(exercise_id) == 0){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getFirstName() + content.getTeacher().getLastName())
                    .student_id(content.getExerciseSubmission().getStudent().getId())
                    .student_name(content.getExerciseSubmission().getStudent().getFirstName() + " " + content.getExerciseSubmission().getStudent().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
                user_graded.add(content.getExerciseSubmission().getStudent());
            }
        });

        List<ClassHasRegisterJoinSemesterClass> listClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository.findByClassesId1(classes.getId());

        listClassHasRegisterJoinSemesterClass.forEach(student -> {
            LocalDateTime time_now = LocalDateTime.now();
            if (user_graded.contains(student.getUserRegisterJoinSemester().getStudent()) == false){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(student.getUserRegisterJoinSemester().getStudent().getId())
                    .student_name(student.getUserRegisterJoinSemester().getStudent().getFirstName() + " " + student.getUserRegisterJoinSemester().getStudent().getLastName())
                    .teacher_id(classes.getUserRegisterTeachSemester().getTeacher().getId())
                    .teacher_name(classes.getUserRegisterTeachSemester().getTeacher().getFirstName() + " " + classes.getUserRegisterTeachSemester().getTeacher().getLastName())
                    .exercise_submission_id(UUID.randomUUID())
                    .image_url("")
                    .feedback("")
                    .description("")
                    .score((float) 0)
                    .time(time_now)
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override  
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentAndExercise(UUID exercise_id, UUID student_id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();

        List<User> user_graded = new ArrayList<>();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getId().compareTo(exercise_id) == 0 && content.getExerciseSubmission().getStudent().getId().compareTo(student_id) == 0){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getFirstName() + content.getTeacher().getLastName())
                    .student_id(content.getExerciseSubmission().getStudent().getId())
                    .student_name(content.getExerciseSubmission().getStudent().getFirstName() + " " + content.getExerciseSubmission().getStudent().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .image_url(content.getExerciseSubmission().getImage_url())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
                user_graded.add(content.getExerciseSubmission().getStudent());
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentAndClass(UUID classes_id, UUID student_id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        
        List<User> teacher_graded = new ArrayList<>();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getSection().getClasses().getId().compareTo(classes_id) == 0 && content.getExerciseSubmission().getStudent().getId().compareTo(student_id) == 0){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .teacher_id(content.getTeacher().getId())
                    .teacher_name(content.getTeacher().getFirstName() + content.getTeacher().getLastName())
                    .exercise_submission_id(content.getExerciseSubmission().getId())
                    .description(content.getExerciseSubmission().getExercise().getDescription())
                    .exercise_name(content.getExerciseSubmission().getExercise().getName())
                    .time_submit(content.getExerciseSubmission().getUpdate_time())
                    .image_url(content.getExerciseSubmission().getImage_url())
                    .deadline(content.getExerciseSubmission().getExercise().getDeadline())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
                teacher_graded.add(content.getTeacher());
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetUserGradeExerciseSubmissionResponse getUserGradeExerciseSubmissionById(UUID teacher_id ,UUID exercise_submission_id) {
        Optional<UserGradeExerciseSubmission> userGradeExerciseSubmissionOpt = userGradeExerciseSubmissionRepository.findByTeacherIdAndExerciseSubmissionId(teacher_id, exercise_submission_id);
        UserGradeExerciseSubmission userGradeExerciseSubmission = userGradeExerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeExerciseSubmission.not_found");
        });

        return GetUserGradeExerciseSubmissionResponse.builder()
            .teacher_id(userGradeExerciseSubmission.getTeacher().getId())
            .teacher_name(userGradeExerciseSubmission.getTeacher().getFirstName() + userGradeExerciseSubmission.getTeacher().getLastName())
            .exercise_submission_id(userGradeExerciseSubmission.getExerciseSubmission().getId())
            .feedback(userGradeExerciseSubmission.getFeedback())
            .score(userGradeExerciseSubmission.getScore())
            .description(userGradeExerciseSubmission.getExerciseSubmission().getExercise().getDescription())
            .image_url(userGradeExerciseSubmission.getExerciseSubmission().getImage_url())
            .exercise_name(userGradeExerciseSubmission.getExerciseSubmission().getExercise().getName())
            .time_submit(userGradeExerciseSubmission.getExerciseSubmission().getUpdate_time())
            .deadline(userGradeExerciseSubmission.getExerciseSubmission().getExercise().getDeadline())
            .time(userGradeExerciseSubmission.getTime())
            .build();
    }

    @Override
    public UUID createUserGradeExerciseSubmission(CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest) {

        Optional <User> userOpt = userRepository.findById1(createUserGradeExerciseSubmissionRequest.getTeacher_id());
        User teacher = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });

        Optional <ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findById1(createUserGradeExerciseSubmissionRequest.getExercise_submission_id());
        ExerciseSubmission exerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
        });

        UserGradeExerciseSubmissionKey id = new UserGradeExerciseSubmissionKey(teacher.getId(),exerciseSubmission.getId());
        
        UserGradeExerciseSubmission savedUserGradeExerciseSubmission = UserGradeExerciseSubmission.builder()
                .id(id)
                .teacher(teacher)
                .exerciseSubmission(exerciseSubmission)
                .score(createUserGradeExerciseSubmissionRequest.getScore())
                .feedback(createUserGradeExerciseSubmissionRequest.getFeedback())
                .build();
        userGradeExerciseSubmissionRepository.save(savedUserGradeExerciseSubmission);

        return savedUserGradeExerciseSubmission.getTeacher().getId();
    }

    @Override
    public UUID removeUserGradeExerciseSubmissionById(UUID teacher_id, UUID exercise_submission_id) {
        Optional<UserGradeExerciseSubmission> userGradeExerciseSubmissionOpt = userGradeExerciseSubmissionRepository.findByTeacherIdAndExerciseSubmissionId(teacher_id, exercise_submission_id);
        userGradeExerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeExerciseSubmission.not_found");
        });

        userGradeExerciseSubmissionRepository.deleteById(teacher_id, exercise_submission_id);
        return teacher_id;
    }

    @Override
    public UUID updateUserGradeExerciseSubmissionById(UUID teacher_id, UUID submission_id, CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest) {
        //UserGradeExerciseSubmissionKey index = new UserGradeExerciseSubmissionKey(teacher_id, submission_id);

        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content-> {
            if (content.getTeacher().getId().compareTo(teacher_id) == 0 && content.getExerciseSubmission().getId().compareTo(submission_id) == 0){
                UserGradeExerciseSubmission updatedUserGradeExerciseSubmission = content;
                Optional <User> userOpt = userRepository.findById1(createUserGradeExerciseSubmissionRequest.getTeacher_id());
                User teacher = userOpt.orElseThrow(() -> {
                    throw new EntityNotFoundException("exception.user_teacher.not_found");
                });
            
                Optional <ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findById1(createUserGradeExerciseSubmissionRequest.getExercise_submission_id());
                ExerciseSubmission exerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
                    throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
                });
            
                UserGradeExerciseSubmissionKey idx = new UserGradeExerciseSubmissionKey(teacher.getId(), exerciseSubmission.getId());
            
                updatedUserGradeExerciseSubmission.setScore(createUserGradeExerciseSubmissionRequest.getScore());
                updatedUserGradeExerciseSubmission.setId(idx);
                updatedUserGradeExerciseSubmission.setFeedback(createUserGradeExerciseSubmissionRequest.getFeedback());
                updatedUserGradeExerciseSubmission.setExerciseSubmission(exerciseSubmission);
                updatedUserGradeExerciseSubmission.setTeacher(teacher);

                userGradeExerciseSubmissionRepository.save(updatedUserGradeExerciseSubmission);
            }
        });

        return teacher_id;
    }
}
