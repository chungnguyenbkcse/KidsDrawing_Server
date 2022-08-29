package com.app.kidsdrawing.service.impl;

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

import com.app.kidsdrawing.dto.CreateUserGradeExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetUserGradeExerciseSubmissionResponse;
import com.app.kidsdrawing.entity.UserGradeExerciseSubmission;
import com.app.kidsdrawing.entity.Class;
import com.app.kidsdrawing.entity.UserGradeExerciseSubmissionKey;
import com.app.kidsdrawing.entity.ExerciseSubmission;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassRepository;
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
    private final ClassRepository classRepository;

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
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByTeacherId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getTeacher().getId() == id){
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
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getStudent().getId() == id){
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
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByClassId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getSection().getClass1().getId() == id){
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
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getId() == id){
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
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseSubmissionId(Long id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getId() == id){
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
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByExerciseAndClass(Long exercise_id, Long class_id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        Optional<Class> classOpt = classRepository.findById(class_id);
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });
        List<User> user_graded = new ArrayList<>();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getSection().getClass1().getId() == class_id && content.getExerciseSubmission().getExercise().getId() == exercise_id){
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

        classes.getUserRegisterJoinSemesters().forEach(student -> {
            LocalDateTime time_now = LocalDateTime.now();
            if (user_graded.contains(student.getStudent()) == false){
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(student.getStudent().getId())
                    .student_name(student.getStudent().getFirstName() + " " + student.getStudent().getLastName())
                    .teacher_id(classes.getUserRegisterTeachSemester().getTeacher().getId())
                    .teacher_name(classes.getUserRegisterTeachSemester().getTeacher().getFirstName() + " " + classes.getUserRegisterTeachSemester().getTeacher().getLastName())
                    .exercise_submission_id((long) 0)
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
    public ResponseEntity<Map<String, Object>> getAllUserGradeExerciseSubmissionByStudentAndClass(Long class_id, Long student_id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        
        List<User> teacher_graded = new ArrayList<>();
        listUserGradeExerciseSubmission.forEach(content -> {
            if (content.getExerciseSubmission().getExercise().getSection().getClass1().getId() == class_id && content.getExerciseSubmission().getStudent().getId() == student_id){
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
    public GetUserGradeExerciseSubmissionResponse getUserGradeExerciseSubmissionById(Long id) {
        Optional<UserGradeExerciseSubmission> userGradeExerciseSubmissionOpt = userGradeExerciseSubmissionRepository.findById(id);
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
    public Long createUserGradeExerciseSubmission(CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest) {

        Optional <User> userOpt = userRepository.findById(createUserGradeExerciseSubmissionRequest.getTeacher_id());
        User teacher = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_teacher.not_found");
        });

        Optional <ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findById(createUserGradeExerciseSubmissionRequest.getExercise_submission_id());
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
    public Long removeUserGradeExerciseSubmissionById(Long id) {
        Optional<UserGradeExerciseSubmission> userGradeExerciseSubmissionOpt = userGradeExerciseSubmissionRepository.findById(id);
        userGradeExerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserGradeExerciseSubmission.not_found");
        });

        userGradeExerciseSubmissionRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateUserGradeExerciseSubmissionById(Long teacher_id, Long submission_id, CreateUserGradeExerciseSubmissionRequest createUserGradeExerciseSubmissionRequest) {
        //UserGradeExerciseSubmissionKey index = new UserGradeExerciseSubmissionKey(teacher_id, submission_id);

        List<UserGradeExerciseSubmission> listUserGradeExerciseSubmission = userGradeExerciseSubmissionRepository.findAll();
        listUserGradeExerciseSubmission.forEach(content-> {
            if (content.getTeacher().getId() == teacher_id && content.getExerciseSubmission().getId() == submission_id){
                UserGradeExerciseSubmission updatedUserGradeExerciseSubmission = content;
                Optional <User> userOpt = userRepository.findById(createUserGradeExerciseSubmissionRequest.getTeacher_id());
                User teacher = userOpt.orElseThrow(() -> {
                    throw new EntityNotFoundException("exception.user_teacher.not_found");
                });
            
                Optional <ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findById(createUserGradeExerciseSubmissionRequest.getExercise_submission_id());
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
