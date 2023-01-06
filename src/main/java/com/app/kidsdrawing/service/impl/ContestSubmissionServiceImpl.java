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

import com.app.kidsdrawing.dto.CreateContestSubmissionRequest;
import com.app.kidsdrawing.dto.GetContestSubmissionResponse;
import com.app.kidsdrawing.entity.ContestSubmission;
import com.app.kidsdrawing.entity.ContestSubmissionKey;
import com.app.kidsdrawing.entity.Notification;
import com.app.kidsdrawing.entity.Student;
import com.app.kidsdrawing.entity.UserGradeContest;
import com.app.kidsdrawing.entity.UserReadNotification;
import com.app.kidsdrawing.entity.UserReadNotificationKey;
import com.app.kidsdrawing.entity.Contest;
import com.app.kidsdrawing.exception.ArtAgeNotDeleteException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ContestRepository;
import com.app.kidsdrawing.repository.ContestSubmissionRepository;
import com.app.kidsdrawing.repository.NotificationRepository;
import com.app.kidsdrawing.repository.StudentRepository;
import com.app.kidsdrawing.repository.UserGradeContestRepository;
import com.app.kidsdrawing.repository.UserReadNotificationRepository;
import com.app.kidsdrawing.service.ContestSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ContestSubmissionServiceImpl implements ContestSubmissionService {
    
    private final ContestSubmissionRepository contestSubmissionRepository;
    private final ContestRepository contestRepository;
    private final StudentRepository studentRepository;
    private final UserGradeContestRepository userGradeContestRepository;
    private final NotificationRepository notificationRepository;
    private final UserReadNotificationRepository uuserReadNotificationRepository;
    
    private static int count = 0;


    @Override
    public ResponseEntity<Map<String, Object>> getAllContestSubmission() {
        List<GetContestSubmissionResponse> allContestSubmissionResponses = new ArrayList<>();
        List<ContestSubmission> listContestSubmission = contestSubmissionRepository.findAll();
        listContestSubmission.forEach(content -> {
            GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                .score(content.getScore())
                .feedback(content.getFeedback())
                .time(content.getTime())
                .contest_id(content.getContest().getId())
                .student_id(content.getStudent().getId())
                .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                .image_url(content.getImage_url())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allContestSubmissionResponses.add(contestSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("ContestSubmission", allContestSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllContestSubmissionByTeacherAndContest(Long teacher_id, Long contest_id) {
        List<GetContestSubmissionResponse> allContestSubmissionNotGradeResponses = new ArrayList<>();
        List<GetContestSubmissionResponse> allContestSubmissionGradeResponses = new ArrayList<>();
       
        List<ContestSubmission> listContestSubmission = contestSubmissionRepository.findByContestId1(contest_id);
        List<UserGradeContest> pageUserGradeContest = userGradeContestRepository.findByContestId2(contest_id);
        
        int total_teacher = pageUserGradeContest.size();
        int total_contest_submission = listContestSubmission.size();

        int total = total_contest_submission / total_teacher;
        count = 0;
        pageUserGradeContest.forEach(ele -> {
            if (ele.getTeacher().getId() == teacher_id) {
                count = ele.getNumber();
            }
        });

        if (count-1 == total_teacher - 1) {
            List<ContestSubmission> contestSubmissions = listContestSubmission.subList((count-1)*total, total_contest_submission);
            contestSubmissions.forEach(ele -> {
                if (ele.getScore() == null) {
                    GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                        .score(ele.getScore())
                        .feedback(ele.getFeedback())
                        .time(ele.getTime())
                        .student_name(ele.getStudent().getUser().getUsername() + " - " + ele.getStudent().getUser().getFirstName() + " " + ele.getStudent().getUser().getLastName())
                        .contest_id(ele.getContest().getId())
                        .student_id(ele.getStudent().getId())
                        .image_url(ele.getImage_url())
                        .create_time(ele.getCreate_time())
                        .update_time(ele.getUpdate_time())
                        .build();
                    allContestSubmissionNotGradeResponses.add(contestSubmissionResponse);
                }
                else {
                    GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                        .score(ele.getScore())
                        .feedback(ele.getFeedback())
                        .time(ele.getTime())
                        .student_name(ele.getStudent().getUser().getUsername() + " - " + ele.getStudent().getUser().getFirstName() + " " + ele.getStudent().getUser().getLastName())
                        .contest_id(ele.getContest().getId())
                        .student_id(ele.getStudent().getId())
                        .image_url(ele.getImage_url())
                        .create_time(ele.getCreate_time())
                        .update_time(ele.getUpdate_time())
                        .build();
                        allContestSubmissionGradeResponses.add(contestSubmissionResponse);
                }
            });
        }
        else {
            List<ContestSubmission> contestSubmissions = listContestSubmission.subList((count-1)*total, (count)*total);
            contestSubmissions.forEach(ele -> {
                if (ele.getScore() == null) {
                    GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                        .score(ele.getScore())
                        .feedback(ele.getFeedback())
                        .time(ele.getTime())
                        .student_name(ele.getStudent().getUser().getUsername() + " - " + ele.getStudent().getUser().getFirstName() + " " + ele.getStudent().getUser().getLastName())
                        .contest_id(ele.getContest().getId())
                        .student_id(ele.getStudent().getId())
                        .image_url(ele.getImage_url())
                        .create_time(ele.getCreate_time())
                        .update_time(ele.getUpdate_time())
                        .build();
                    allContestSubmissionNotGradeResponses.add(contestSubmissionResponse);
                }
                else {
                    GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                        .score(ele.getScore())
                        .feedback(ele.getFeedback())
                        .time(ele.getTime())
                        .student_name(ele.getStudent().getUser().getUsername() + " - " + ele.getStudent().getUser().getFirstName() + " " + ele.getStudent().getUser().getLastName())
                        .contest_id(ele.getContest().getId())
                        .student_id(ele.getStudent().getId())
                        .image_url(ele.getImage_url())
                        .create_time(ele.getCreate_time())
                        .update_time(ele.getUpdate_time())
                        .build();
                        allContestSubmissionGradeResponses.add(contestSubmissionResponse);
                }
            });
        } 
        

        /* List<ContestSubmission> userGradeContestSubmissions = contestSubmissionRepository.findByContestAndTeacher(contest_id, teacher_id);
        userGradeContestSubmissions.forEach(ele -> {
            if (ele.getScore() == null) {
                GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                    
                    .contest_id(ele.getContest().getId())
                    .student_id(ele.getStudent().getId())
                    .image_url(ele.getImage_url())
                    .create_time(ele.getCreate_time())
                    .update_time(ele.getUpdate_time())
                    .build();
                allContestSubmissionNotGradeResponses.add(contestSubmissionResponse);
            }
            else {
                GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                    
                    .contest_id(ele.getContest().getId())
                    .student_id(ele.getStudent().getId())
                    .image_url(ele.getImage_url())
                    .create_time(ele.getCreate_time())
                    .update_time(ele.getUpdate_time())
                    .build();
                    allContestSubmissionGradeResponses.add(contestSubmissionResponse);
            }
        }); */

        Map<String, Object> response = new HashMap<>();
        response.put("contest_submission_not_grade", allContestSubmissionNotGradeResponses);
        response.put("contest_submission_grade", allContestSubmissionGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override 
    public Long generationContestSubmissionForTeacher(Long contest_id){
        List<ContestSubmission> listContestSubmission = contestSubmissionRepository.findByContestId1(contest_id);

        List<UserGradeContest> pageUserGradeContest = userGradeContestRepository.findByContestId2(contest_id);
        
        int total_teacher = pageUserGradeContest.size();
        int total_contest_submission = listContestSubmission.size();

        int total = total_contest_submission / total_teacher;

        count = 0;
        for (count = 0; count < total_teacher; count++) {
            if (count == total_teacher - 1) {
                List<ContestSubmission> contestSubmissions = listContestSubmission.subList(count*total, total_contest_submission);
                contestSubmissions.forEach(ele -> {
                    
                });
            }
            else {
                List<ContestSubmission> contestSubmissions = listContestSubmission.subList(count*total, (count+1)*total);
                contestSubmissions.forEach(ele -> {
                    
                });
            } 
        }

        return contest_id;

    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllContestSubmissionByStudentId(Long id) {
        List<GetContestSubmissionResponse> allContestSubmissionResponses = new ArrayList<>();
        List<ContestSubmission> listContestSubmission = contestSubmissionRepository.findAll();
        listContestSubmission.forEach(content -> {
            if (content.getStudent().getId().compareTo(id) == 0){
                GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                    .score(content.getScore())
                    .feedback(content.getFeedback())
                    .time(content.getTime())
                    .contest_id(content.getContest().getId())
                    .student_id(content.getStudent().getId())
                    .contest_name(content.getContest().getName())
                    .student_name(content.getStudent().getUser().getUsername() + " - " + content.getStudent().getUser().getFirstName() + " " + content.getStudent().getUser().getLastName())
                    .image_url(content.getImage_url())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                allContestSubmissionResponses.add(contestSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("ContestSubmission", allContestSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override 
    public Long checkGenerationContestSubmissionForTeacher(Long contest_id)  {
        List<ContestSubmission> userGradeContestSubmissions = contestSubmissionRepository.findByContestId3(contest_id);

        if (userGradeContestSubmissions.size() > 0 ) {
            return (long) 1;
        }
        else {
            throw new EntityNotFoundException("exception.check_generation.not_found");
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllContestSubmissionByContestId(Long id) {
        List<GetContestSubmissionResponse> allContestSubmissionGradedResponses = new ArrayList<>();
        List<GetContestSubmissionResponse> allContestSubmissionNotGradedResponses = new ArrayList<>();
        List<ContestSubmission> listContestSubmissionByContest = contestSubmissionRepository.findByContestId2(id);
        List <UserGradeContest> userGradeContests = userGradeContestRepository.findByContestId2(id);
  
        int total_teacher = userGradeContests.size();
        int total_contest_submission = listContestSubmissionByContest.size();

        int total = total_contest_submission / total_teacher;

        for (int index = 0; index < total_teacher; index++) {
            if (index == total_teacher - 1) {
                List<ContestSubmission> contestSubmissions = listContestSubmissionByContest.subList(index*total, total_contest_submission);
                System.out.print(contestSubmissions.size());
                for (int y = 0; y < contestSubmissions.size(); y++) {
                    if (contestSubmissions.get(y).getScore() != null) {
                        GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                            .score(contestSubmissions.get(y).getScore())
                            .avatar(contestSubmissions.get(y).getStudent().getUser().getProfileImageUrl())
                            .feedback(contestSubmissions.get(y).getFeedback())
                            .time(contestSubmissions.get(y).getTime())
                            .teacher_id(userGradeContests.get(index).getTeacher().getId())
                            .teacher_name(userGradeContests.get(index).getTeacher().getUser().getUsername() + " - " + userGradeContests.get(index).getTeacher().getUser().getFirstName() + " " + userGradeContests.get(index).getTeacher().getUser().getLastName())
                            .contest_id(contestSubmissions.get(y).getContest().getId())
                            .student_id(contestSubmissions.get(y).getStudent().getId())
                            .contest_name(contestSubmissions.get(y).getContest().getName())
                            .student_name(contestSubmissions.get(y).getStudent().getUser().getUsername() + " - " + contestSubmissions.get(y).getStudent().getUser().getFirstName() + " " + contestSubmissions.get(y).getStudent().getUser().getLastName())
                            .image_url(contestSubmissions.get(y).getImage_url())
                            .create_time(contestSubmissions.get(y).getCreate_time())
                            .update_time(contestSubmissions.get(y).getUpdate_time())
                            .build();
                        allContestSubmissionGradedResponses.add(contestSubmissionResponse);
                    }
                    else {
                        GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                            .score(contestSubmissions.get(y).getScore())
                            .avatar(contestSubmissions.get(y).getStudent().getUser().getProfileImageUrl())
                            .feedback(contestSubmissions.get(y).getFeedback())
                            .time(contestSubmissions.get(y).getTime())
                            .teacher_name(userGradeContests.get(index).getTeacher().getUser().getUsername() + " - " + userGradeContests.get(index).getTeacher().getUser().getFirstName() + " " + userGradeContests.get(index).getTeacher().getUser().getLastName())
                            .teacher_id(userGradeContests.get(index).getTeacher().getId())
                            .contest_id(contestSubmissions.get(y).getContest().getId())
                            .student_id(contestSubmissions.get(y).getStudent().getId())
                            .contest_name(contestSubmissions.get(y).getContest().getName())
                            .student_name(contestSubmissions.get(y).getStudent().getUser().getUsername() + " - " + contestSubmissions.get(y).getStudent().getUser().getFirstName() + " " + contestSubmissions.get(y).getStudent().getUser().getLastName())
                            .image_url(contestSubmissions.get(y).getImage_url())
                            .create_time(contestSubmissions.get(y).getCreate_time())
                            .update_time(contestSubmissions.get(y).getUpdate_time())
                            .build();
                        allContestSubmissionNotGradedResponses.add(contestSubmissionResponse);
                    }
                }
            }
            else {
                List<ContestSubmission> contestSubmissions = listContestSubmissionByContest.subList(index*total, (index+1)*total);
                System.out.print(contestSubmissions.size());
                for (int y = 0; y < contestSubmissions.size(); y++) {
                    if (contestSubmissions.get(y).getScore() != null) {
                        GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                            .score(contestSubmissions.get(y).getScore())
                            .avatar(contestSubmissions.get(y).getStudent().getUser().getProfileImageUrl())
                            .feedback(contestSubmissions.get(y).getFeedback())
                            .time(contestSubmissions.get(y).getTime())
                            .teacher_name(userGradeContests.get(index).getTeacher().getUser().getUsername() + " - " + userGradeContests.get(index).getTeacher().getUser().getFirstName() + " " + userGradeContests.get(index).getTeacher().getUser().getLastName())
                            .teacher_id(userGradeContests.get(index).getTeacher().getId())
                            .contest_id(contestSubmissions.get(y).getContest().getId())
                            .student_id(contestSubmissions.get(y).getStudent().getId())
                            .contest_name(contestSubmissions.get(y).getContest().getName())
                            .student_name(contestSubmissions.get(y).getStudent().getUser().getUsername() + " - " + contestSubmissions.get(y).getStudent().getUser().getFirstName() + " " + contestSubmissions.get(y).getStudent().getUser().getLastName())
                            .image_url(contestSubmissions.get(y).getImage_url())
                            .create_time(contestSubmissions.get(y).getCreate_time())
                            .update_time(contestSubmissions.get(y).getUpdate_time())
                            .build();
                        allContestSubmissionGradedResponses.add(contestSubmissionResponse);
                    }
                    else {
                        GetContestSubmissionResponse contestSubmissionResponse = GetContestSubmissionResponse.builder()
                            .score(contestSubmissions.get(y).getScore())
                            .avatar(contestSubmissions.get(y).getStudent().getUser().getProfileImageUrl())
                            .feedback(contestSubmissions.get(y).getFeedback())
                            .time(contestSubmissions.get(y).getTime())
                            .teacher_id(userGradeContests.get(index).getTeacher().getId())
                            .teacher_name(userGradeContests.get(index).getTeacher().getUser().getUsername() + " - " + userGradeContests.get(index).getTeacher().getUser().getFirstName() + " " + userGradeContests.get(index).getTeacher().getUser().getLastName())
                            .contest_id(contestSubmissions.get(y).getContest().getId())
                            .student_id(contestSubmissions.get(y).getStudent().getId())
                            .contest_name(contestSubmissions.get(y).getContest().getName())
                            .student_name(contestSubmissions.get(y).getStudent().getUser().getUsername() + " - " + contestSubmissions.get(y).getStudent().getUser().getFirstName() + " " + contestSubmissions.get(y).getStudent().getUser().getLastName())
                            .image_url(contestSubmissions.get(y).getImage_url())
                            .create_time(contestSubmissions.get(y).getCreate_time())
                            .update_time(contestSubmissions.get(y).getUpdate_time())
                            .build();
                        allContestSubmissionNotGradedResponses.add(contestSubmissionResponse);
                    }
                }
            } 
        }

        Map<String, Object> response = new HashMap<>();
        response.put("contest_not_graded", allContestSubmissionNotGradedResponses);
        response.put("contest_graded", allContestSubmissionGradedResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetContestSubmissionResponse getContestSubmissionByConetestAndStudent(Long contest_id, Long student_id) {
        Optional<ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findByContestAndStudent(contest_id, student_id);
        ContestSubmission contestSubmission = contestSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ContestSubmission.not_found");
        });

        return GetContestSubmissionResponse.builder()
            .score(contestSubmission.getScore())
            .feedback(contestSubmission.getFeedback())
            .time(contestSubmission.getTime())
            .contest_id(contestSubmission.getContest().getId())
            .student_id(contestSubmission.getStudent().getId())
            .contest_name(contestSubmission.getContest().getName())
            .student_name(contestSubmission.getStudent().getUser().getUsername() + " - " + contestSubmission.getStudent().getUser().getFirstName() + " " + contestSubmission.getStudent().getUser().getLastName())
            .image_url(contestSubmission.getImage_url())
            .create_time(contestSubmission.getCreate_time())
            .update_time(contestSubmission.getUpdate_time())
            .build();
    }

    

    @Override
    public Long createContestSubmission(CreateContestSubmissionRequest createContestSubmissionRequest) {

        Optional <Student> studentOpt = studentRepository.findById1(createContestSubmissionRequest.getStudent_id());
        Student student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <Contest> contestOpt = contestRepository.findById1(createContestSubmissionRequest.getContest_id());
        Contest contest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Contest.not_found");
        });

        ContestSubmissionKey id = new ContestSubmissionKey(student.getId(),contest.getId());

        LocalDateTime time_now = LocalDateTime.now();

        if (time_now.isAfter((contest.getEnd_time()))) {
            throw new EntityNotFoundException("exception.deadline.not_found");
        }
        
        ContestSubmission savedContestSubmission = ContestSubmission.builder()
                .id(id)
                .student(student)
                .contest(contest)
                .image_url(createContestSubmissionRequest.getImage_url())
                .build();
        contestSubmissionRepository.save(savedContestSubmission);

        return student.getId();
    }

    @Override
    public Long removeContestSubmissionById(Long contest_id, Long student_id) {
        Optional<ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findByContestAndStudent(contest_id, student_id);
        ContestSubmission contestSubmission = contestSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ContestSubmission.not_found");
        });

        LocalDateTime time_now = LocalDateTime.now();
        if (time_now.isAfter((contestSubmission.getContest().getEnd_time()))) {
            throw new ArtAgeNotDeleteException("exception.contest.deadline");
        }

        ContestSubmissionKey id = new ContestSubmissionKey(student_id, contest_id);

        contestSubmissionRepository.deleteById(id);
        return student_id;
    }

    @Override
    public Long updateContestSubmissionByStudent(CreateContestSubmissionRequest createContestSubmissionRequest) {

        Optional<ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findByContestAndStudent(createContestSubmissionRequest.getContest_id(), createContestSubmissionRequest.getStudent_id());
        ContestSubmission updatedContestSubmission = contestSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ContestSubmission.not_found");
        });

        updatedContestSubmission.setImage_url(createContestSubmissionRequest.getImage_url());

        return updatedContestSubmission.getStudent().getId();
    }


    @Override
    public Long updateContestSubmissionByTeacher(CreateContestSubmissionRequest createContestSubmissionRequest) {

        Optional<ContestSubmission> contestSubmissionOpt = contestSubmissionRepository.findByContestAndStudent(createContestSubmissionRequest.getContest_id(), createContestSubmissionRequest.getStudent_id());
        ContestSubmission updatedContestSubmission = contestSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ContestSubmission.not_found");
        });

        LocalDateTime time_now = LocalDateTime.now();

        updatedContestSubmission.setScore(createContestSubmissionRequest.getScore());
        updatedContestSubmission.setFeedback(createContestSubmissionRequest.getFeedback());
        updatedContestSubmission.setTime(time_now);

        Notification savedNotification = Notification.builder()
            .name("Điểm cuộc thi")
            .description("Chào bạn!\n Hệ thống thong báo bài nộp của bạn cho cuộc thi \"" + updatedContestSubmission.getContest().getName() + "\" đã được chấm!\n Vui lòng vào cuộc thi để xem kết quả!")
            .build();
        notificationRepository.save(savedNotification);

        UserReadNotificationKey id = new UserReadNotificationKey(updatedContestSubmission.getStudent().getUser().getId(), savedNotification.getId());
        
        UserReadNotification savedUserReadNotification = UserReadNotification.builder()
                .id(id)
                .notification(savedNotification)
                .user(updatedContestSubmission.getStudent().getUser())
                .is_read(false)
                .build();
        uuserReadNotificationRepository.save(savedUserReadNotification);

        Notification savedNotification1 = Notification.builder()
            .name("Điểm cuộc thi")
            .description("Chào bạn!\n Hệ thống thong báo bài nộp của tài khoản con \"" + updatedContestSubmission.getStudent().getUser().getUsername() + " - " + updatedContestSubmission.getStudent().getUser().getFirstName() + " " + updatedContestSubmission.getStudent().getUser().getLastName() + "\" cho cuộc thi \"" + updatedContestSubmission.getContest().getName() + "\" đã được chấm!\n Vui lòng vào cuộc thi để xem kết quả!")
            .build();
        notificationRepository.save(savedNotification1);

        UserReadNotificationKey id1 = new UserReadNotificationKey(updatedContestSubmission.getStudent().getParent().getUser().getId(), savedNotification1.getId());
        
        UserReadNotification savedUserReadNotification1 = UserReadNotification.builder()
                .id(id1)
                .notification(savedNotification1)
                .user(updatedContestSubmission.getStudent().getParent().getUser())
                .is_read(false)
                .build();
        uuserReadNotificationRepository.save(savedUserReadNotification1);

        return updatedContestSubmission.getStudent().getId();
    }
}
