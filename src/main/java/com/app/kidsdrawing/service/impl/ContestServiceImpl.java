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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateContestRequest;
import com.app.kidsdrawing.dto.GetContestResponse;
import com.app.kidsdrawing.dto.GetContestStudentResponse;
import com.app.kidsdrawing.dto.GetContestTeacherResponse;
import com.app.kidsdrawing.entity.ArtAge;
import com.app.kidsdrawing.entity.ArtType;
import com.app.kidsdrawing.entity.Contest;
import com.app.kidsdrawing.entity.ContestSubmission;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserGradeContest;
import com.app.kidsdrawing.entity.UserRegisterJoinContest;
import com.app.kidsdrawing.exception.ContestAlreadyCreateException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ArtAgeRepository;
import com.app.kidsdrawing.repository.ArtTypeRepository;
import com.app.kidsdrawing.repository.ContestRepository;
import com.app.kidsdrawing.repository.ContestSubmissionRepository;
import com.app.kidsdrawing.repository.UserGradeContestRepository;
import com.app.kidsdrawing.repository.UserGradeContestSubmissionRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinContestRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.ContestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepository;
    private final UserRepository userRepository;
    private final ArtAgeRepository artAgeRepository;
    private final ArtTypeRepository artTypeRepository;
    private final UserGradeContestRepository userGradeContestRepository;
    private final UserRegisterJoinContestRepository userRegisterJoinContestRepository;
    private final ContestSubmissionRepository contestSubmissionRepository;
    private final UserGradeContestSubmissionRepository userGradeContestSubmissionRepository;
    private static int total = 0;

    @Override
    public ResponseEntity<Map<String, Object>> getAllContestByTeacher(UUID id) {
        List<GetContestTeacherResponse> allContestNotOpenNowResponses = new ArrayList<>();
        List<GetContestTeacherResponse> allContestOpeningResponses = new ArrayList<>();
        List<GetContestTeacherResponse> allContestEndResponses = new ArrayList<>();
        List<GetContestTeacherResponse> allContestNotOpenNowNotTeacherResponses = new ArrayList<>();
        List<UserGradeContest> pageUserGradeContest = userGradeContestRepository.findAll();
        List<Contest> pageContest = contestRepository.findAll();
        LocalDateTime time_now = LocalDateTime.now();

        pageContest.forEach(contest -> {
            List<UUID> teachers = new ArrayList<>();
            pageUserGradeContest.forEach(user_grade_contest -> {
                if (user_grade_contest.getContest().getId().compareTo(contest.getId())  == 0) {
                    teachers.add(user_grade_contest.getUser().getId());
                }
            });

            total = 0;
            List<UserRegisterJoinContest> listUserRegisterContestByContest = userRegisterJoinContestRepository
                    .findByContestId1(contest.getId());
            List<ContestSubmission> listContestSubmissionByContest = contestSubmissionRepository
                    .findByContestId1(contest.getId());
            listContestSubmissionByContest.forEach(contest_submission -> {
                if (userGradeContestSubmissionRepository.existsByContestSubmissionId(contest_submission.getId())) {
                    total = total + 1;
                }
            });
            if (teachers.contains(id)) {
                if (time_now.isAfter(contest.getStart_time()) == false) {
                    GetContestTeacherResponse contestNotOpenNowResponse = GetContestTeacherResponse.builder()
                            .id(contest.getId())
                            .name(contest.getName())
                            .description(contest.getDescription())
                            .max_participant(contest.getMax_participant())
                            .total_register_contest(listUserRegisterContestByContest.size())
                            .total_contest_submission(listContestSubmissionByContest.size())
                            .total_contest_submission_graded(total)
                            .registration_time(contest.getRegistration_time())
                            .image_url(contest.getImage_url())
                            .start_time(contest.getStart_time())
                            .end_time(contest.getEnd_time())
                            .is_enabled(contest.getIs_enabled())
                            .art_age_name(contest.getArtAges().getName())
                            .art_type_name(contest.getArtTypes().getName())
                            .build();
                    allContestNotOpenNowResponses.add(contestNotOpenNowResponse);
                } else if (time_now.isAfter(contest.getEnd_time()) == true) {
                    GetContestTeacherResponse contestEndResponse = GetContestTeacherResponse.builder()
                            .id(contest.getId())
                            .name(contest.getName())
                            .description(contest.getDescription())
                            .max_participant(contest.getMax_participant())
                            .total_register_contest(listUserRegisterContestByContest.size())
                            .total_contest_submission(listContestSubmissionByContest.size())
                            .total_contest_submission_graded(total)
                            .registration_time(contest.getRegistration_time())
                            .image_url(contest.getImage_url())
                            .start_time(contest.getStart_time())
                            .end_time(contest.getEnd_time())
                            .is_enabled(contest.getIs_enabled())
                            .art_age_name(contest.getArtAges().getName())
                            .art_type_name(contest.getArtTypes().getName())
                            .build();
                    allContestEndResponses.add(contestEndResponse);
                } else if (time_now.isAfter(contest.getStart_time()) == true
                        && time_now.isAfter(contest.getEnd_time()) == false) {
                    GetContestTeacherResponse contestOpeningResponse = GetContestTeacherResponse.builder()
                            .id(contest.getId())
                            .name(contest.getName())
                            .description(contest.getDescription())
                            .max_participant(contest.getMax_participant())
                            .total_register_contest(listUserRegisterContestByContest.size())
                            .total_contest_submission(listContestSubmissionByContest.size())
                            .total_contest_submission_graded(total)
                            .registration_time(contest.getRegistration_time())
                            .image_url(contest.getImage_url())
                            .start_time(contest.getStart_time())
                            .end_time(contest.getEnd_time())
                            .is_enabled(contest.getIs_enabled())
                            .art_age_name(contest.getArtAges().getName())
                            .art_type_name(contest.getArtTypes().getName())
                            .build();
                    allContestOpeningResponses.add(contestOpeningResponse);
                }
            } else {
                if (time_now.isAfter(contest.getStart_time()) == false) {
                    GetContestTeacherResponse contestNotOpenNowNotTeacherResponse = GetContestTeacherResponse.builder()
                            .id(contest.getId())
                            .name(contest.getName())
                            .description(contest.getDescription())
                            .max_participant(contest.getMax_participant())
                            .total_register_contest(listUserRegisterContestByContest.size())
                            .total_contest_submission(listContestSubmissionByContest.size())
                            .total_contest_submission_graded(total)
                            .registration_time(contest.getRegistration_time())
                            .image_url(contest.getImage_url())
                            .start_time(contest.getStart_time())
                            .end_time(contest.getEnd_time())
                            .is_enabled(contest.getIs_enabled())
                            .art_age_name(contest.getArtAges().getName())
                            .art_type_name(contest.getArtTypes().getName())
                            .build();
                    allContestNotOpenNowNotTeacherResponses.add(contestNotOpenNowNotTeacherResponse);
                }
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("contest_not_open_now", allContestNotOpenNowResponses);
        response.put("contest_opening", allContestOpeningResponses);
        response.put("contest_end", allContestEndResponses);
        response.put("contest_not_open_now_not_teacher", allContestNotOpenNowNotTeacherResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllContest(int page, int size) {
        List<GetContestResponse> allContestResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Contest> pageContest = contestRepository.findAll(paging);
        pageContest.getContent().forEach(contest -> {
            total = 0;
            List<UserRegisterJoinContest> listUserRegisterContestByContest = userRegisterJoinContestRepository
                    .findByContestId1(contest.getId());
            List<ContestSubmission> listContestSubmissionByContest = contestSubmissionRepository
                    .findByContestId1(contest.getId());
            listContestSubmissionByContest.forEach(contest_submission -> {
                if (userGradeContestSubmissionRepository.existsByContestSubmissionId(contest_submission.getId())) {
                    total = total + 1;
                }
            });

            GetContestResponse contestResponse = GetContestResponse.builder()
                    .id(contest.getId())
                    .name(contest.getName())
                    .description(contest.getDescription())
                    .max_participant(contest.getMax_participant())
                    .total_register_contest(listUserRegisterContestByContest.size())
                    .total_contest_submission(listContestSubmissionByContest.size())
                    .total_contest_submission_graded(total)
                    .registration_time(contest.getRegistration_time())
                    .image_url(contest.getImage_url())
                    .start_time(contest.getStart_time())
                    .end_time(contest.getEnd_time())
                    .is_enabled(contest.getIs_enabled())
                    .art_age_id(contest.getArtAges().getId())
                    .art_type_id(contest.getArtTypes().getId())
                    .creator_id(contest.getUser().getId())
                    .create_time(contest.getCreate_time())
                    .update_time(contest.getUpdate_time())
                    .build();
            allContestResponses.add(contestResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("art_age", allContestResponses);
        response.put("currentPage", pageContest.getNumber());
        response.put("totalItems", pageContest.getTotalElements());
        response.put("totalPages", pageContest.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllContestByArtTypeId(int page, int size, UUID id) {
        List<GetContestResponse> allContestResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Contest> pageContest = contestRepository.findAll(paging);
        pageContest.getContent().forEach(contest -> {
            if (contest.getArtTypes().getId().compareTo(id) == 0) {
                total = 0;
                List<UserRegisterJoinContest> listUserRegisterContestByContest = userRegisterJoinContestRepository
                    .findByContestId1(contest.getId());
                List<ContestSubmission> listContestSubmissionByContest = contestSubmissionRepository
                        .findByContestId1(contest.getId());
                listContestSubmissionByContest.forEach(contest_submission -> {
                    if (userGradeContestSubmissionRepository.existsByContestSubmissionId(contest_submission.getId())) {
                        total = total + 1;
                    }
                });

                GetContestResponse contestResponse = GetContestResponse.builder()
                        .id(contest.getId())
                        .name(contest.getName())
                        .description(contest.getDescription())
                        .max_participant(contest.getMax_participant())
                        .total_register_contest(listUserRegisterContestByContest.size())
                        .total_contest_submission(listContestSubmissionByContest.size())
                        .total_contest_submission_graded(total)
                        .registration_time(contest.getRegistration_time())
                        .image_url(contest.getImage_url())
                        .start_time(contest.getStart_time())
                        .end_time(contest.getEnd_time())
                        .is_enabled(contest.getIs_enabled())
                        .art_age_id(contest.getArtAges().getId())
                        .art_type_id(contest.getArtTypes().getId())
                        .creator_id(contest.getUser().getId())
                        .create_time(contest.getCreate_time())
                        .update_time(contest.getUpdate_time())
                        .build();
                allContestResponses.add(contestResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("art_age", allContestResponses);
        response.put("currentPage", pageContest.getNumber());
        response.put("totalItems", pageContest.getTotalElements());
        response.put("totalPages", pageContest.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllContestByStudent(UUID student_id) {
        List<GetContestStudentResponse> allContestNotOpenNowResponses = new ArrayList<>();
        List<GetContestStudentResponse> allContestOpeningResponses = new ArrayList<>();
        List<GetContestStudentResponse> allContestEndResponses = new ArrayList<>();
        List<GetContestStudentResponse> allContestNewResponses = new ArrayList<>();
        List<UserRegisterJoinContest> listRegisterJoinContest = userRegisterJoinContestRepository
                .findByStudentId2(student_id);
        LocalDateTime time_now = LocalDateTime.now();
        List<Contest> allContest = contestRepository.findAll();
        List<Contest> allContestForStudent = new ArrayList<>();

        listRegisterJoinContest.forEach(ele -> {
            total = 0;
            allContestForStudent.add(ele.getContest());
            List<UserRegisterJoinContest> listUserRegisterContestByContest = userRegisterJoinContestRepository
                    .findByContestId1(ele.getContest().getId());
            List<ContestSubmission> listContestSubmissionByContest = contestSubmissionRepository
                    .findByContestId1(ele.getContest().getId());
            listContestSubmissionByContest.forEach(contest_submission -> {
                if (userGradeContestSubmissionRepository.existsByContestSubmissionId(contest_submission.getId())) {
                    total = total + 1;
                }
            });
            if (time_now.isAfter(ele.getContest().getRegistration_time()) == false) {
                GetContestStudentResponse contestNotOpenNowResponse = GetContestStudentResponse.builder()
                        .id(ele.getContest().getId())
                        .name(ele.getContest().getName())
                        .description(ele.getContest().getDescription())
                        .max_participant(ele.getContest().getMax_participant())
                        .total_register_contest(listUserRegisterContestByContest.size())
                        .total_contest_submission(listContestSubmissionByContest.size())
                        .total_contest_submission_graded(total)
                        .registration_time(ele.getContest().getRegistration_time())
                        .image_url(ele.getContest().getImage_url())
                        .start_time(ele.getContest().getStart_time())
                        .end_time(ele.getContest().getEnd_time())
                        .is_enabled(ele.getContest().getIs_enabled())
                        .art_age_name(ele.getContest().getArtAges().getName())
                        .art_type_name(ele.getContest().getArtTypes().getName())
                        .build();
                allContestNotOpenNowResponses.add(contestNotOpenNowResponse);
            } else if (time_now.isAfter(ele.getContest().getEnd_time()) == true) {
                GetContestStudentResponse contestEndResponse = GetContestStudentResponse.builder()
                        .id(ele.getContest().getId())
                        .name(ele.getContest().getName())
                        .description(ele.getContest().getDescription())
                        .max_participant(ele.getContest().getMax_participant())
                        .total_register_contest(listUserRegisterContestByContest.size())
                        .total_contest_submission(listContestSubmissionByContest.size())
                        .total_contest_submission_graded(total)
                        .registration_time(ele.getContest().getRegistration_time())
                        .image_url(ele.getContest().getImage_url())
                        .start_time(ele.getContest().getStart_time())
                        .end_time(ele.getContest().getEnd_time())
                        .is_enabled(ele.getContest().getIs_enabled())
                        .art_age_name(ele.getContest().getArtAges().getName())
                        .art_type_name(ele.getContest().getArtTypes().getName())
                        .build();
                allContestEndResponses.add(contestEndResponse);
            } else if (time_now.isAfter(ele.getContest().getStart_time()) == true
                    && time_now.isAfter(ele.getContest().getEnd_time()) == false) {
                GetContestStudentResponse contestOpeningResponse = GetContestStudentResponse.builder()
                        .id(ele.getContest().getId())
                        .name(ele.getContest().getName())
                        .description(ele.getContest().getDescription())
                        .max_participant(ele.getContest().getMax_participant())
                        .total_register_contest(listUserRegisterContestByContest.size())
                        .total_contest_submission(listContestSubmissionByContest.size())
                        .total_contest_submission_graded(total)
                        .registration_time(ele.getContest().getRegistration_time())
                        .image_url(ele.getContest().getImage_url())
                        .start_time(ele.getContest().getStart_time())
                        .end_time(ele.getContest().getEnd_time())
                        .is_enabled(ele.getContest().getIs_enabled())
                        .art_age_name(ele.getContest().getArtAges().getName())
                        .art_type_name(ele.getContest().getArtTypes().getName())
                        .build();
                allContestOpeningResponses.add(contestOpeningResponse);
            }
        });

        allContest.forEach(contest -> {
            if (!allContestForStudent.contains(contest) && time_now.isBefore(contest.getRegistration_time())) {
                List<UserRegisterJoinContest> listUserRegisterContestByContest = userRegisterJoinContestRepository
                    .findByContestId1(contest.getId());
                GetContestStudentResponse contestOpeningResponse = GetContestStudentResponse.builder()
                        .id(contest.getId())
                        .name(contest.getName())
                        .description(contest.getDescription())
                        .max_participant(contest.getMax_participant())
                        .total_register_contest(listUserRegisterContestByContest.size())
                        .total_contest_submission_graded(total)
                        .registration_time(contest.getRegistration_time())
                        .image_url(contest.getImage_url())
                        .start_time(contest.getStart_time())
                        .end_time(contest.getEnd_time())
                        .is_enabled(contest.getIs_enabled())
                        .art_age_name(contest.getArtAges().getName())
                        .art_type_name(contest.getArtTypes().getName())
                        .build();
                allContestNewResponses.add(contestOpeningResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("contest_not_open_now", allContestNotOpenNowResponses);
        response.put("contest_opening", allContestOpeningResponses);
        response.put("contest_end", allContestEndResponses);
        response.put("contest_new", allContestNewResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllContestByParent(UUID parent_id) {
        List<GetContestStudentResponse> allContestNotOpenNowResponses = new ArrayList<>();
        List<GetContestStudentResponse> allContestOpeningResponses = new ArrayList<>();
        List<GetContestStudentResponse> allContestEndResponses = new ArrayList<>();
        List<GetContestStudentResponse> allContestNewResponses = new ArrayList<>();
        LocalDateTime time_now = LocalDateTime.now();
        List<User> pageUser = userRepository.findByParentId(parent_id);
        pageUser.forEach(student -> {
            List<UserRegisterJoinContest> listRegisterJoinContest = userRegisterJoinContestRepository
                    .findByStudentId2(student.getId());
            List<Contest> allContest = contestRepository.findAll();
            List<Contest> allContestForStudent = new ArrayList<>();

            listRegisterJoinContest.forEach(ele -> {
                total = 0;
                allContestForStudent.add(ele.getContest());
                List<UserRegisterJoinContest> listUserRegisterContestByContest = userRegisterJoinContestRepository
                    .findByContestId1(ele.getContest().getId());
                List<ContestSubmission> listContestSubmissionByContest = contestSubmissionRepository
                        .findByContestId1(ele.getContest().getId());
                listContestSubmissionByContest.forEach(contest_submission -> {
                    if (userGradeContestSubmissionRepository.existsByContestSubmissionId(contest_submission.getId())) {
                        total = total + 1;
                    }
                });
                if (time_now.isAfter(ele.getContest().getRegistration_time()) == false) {
                    GetContestStudentResponse contestNotOpenNowResponse = GetContestStudentResponse.builder()
                            .id(ele.getContest().getId())
                            .name(ele.getContest().getName())
                            .description(ele.getContest().getDescription())
                            .student_id(student.getId())
                            .student_name(student.getFirstName() + " " + student.getLastName())
                            .max_participant(ele.getContest().getMax_participant())
                            .total_register_contest(listUserRegisterContestByContest.size())
                            .total_contest_submission(listContestSubmissionByContest.size())
                            .total_contest_submission_graded(total)
                            .registration_time(ele.getContest().getRegistration_time())
                            .image_url(ele.getContest().getImage_url())
                            .start_time(ele.getContest().getStart_time())
                            .end_time(ele.getContest().getEnd_time())
                            .is_enabled(ele.getContest().getIs_enabled())
                            .art_age_name(ele.getContest().getArtAges().getName())
                            .art_type_name(ele.getContest().getArtTypes().getName())
                            .build();
                    allContestNotOpenNowResponses.add(contestNotOpenNowResponse);
                } else if (time_now.isAfter(ele.getContest().getEnd_time()) == true) {
                    GetContestStudentResponse contestEndResponse = GetContestStudentResponse.builder()
                            .id(ele.getContest().getId())
                            .name(ele.getContest().getName())
                            .description(ele.getContest().getDescription())
                            .student_id(student.getId())
                            .student_name(student.getFirstName() + " " + student.getLastName())
                            .max_participant(ele.getContest().getMax_participant())
                            .total_register_contest(listUserRegisterContestByContest.size())
                            .total_contest_submission(listContestSubmissionByContest.size())
                            .total_contest_submission_graded(total)
                            .registration_time(ele.getContest().getRegistration_time())
                            .image_url(ele.getContest().getImage_url())
                            .start_time(ele.getContest().getStart_time())
                            .end_time(ele.getContest().getEnd_time())
                            .is_enabled(ele.getContest().getIs_enabled())
                            .art_age_name(ele.getContest().getArtAges().getName())
                            .art_type_name(ele.getContest().getArtTypes().getName())
                            .build();
                    allContestEndResponses.add(contestEndResponse);
                } else if (time_now.isAfter(ele.getContest().getStart_time()) == true
                        && time_now.isAfter(ele.getContest().getEnd_time()) == false) {
                    GetContestStudentResponse contestOpeningResponse = GetContestStudentResponse.builder()
                            .id(ele.getContest().getId())
                            .name(ele.getContest().getName())
                            .student_id(student.getId())
                            .student_name(student.getFirstName() + " " + student.getLastName())
                            .description(ele.getContest().getDescription())
                            .max_participant(ele.getContest().getMax_participant())
                            .total_register_contest(listUserRegisterContestByContest.size())
                            .total_contest_submission(listContestSubmissionByContest.size())
                            .total_contest_submission_graded(total)
                            .registration_time(ele.getContest().getRegistration_time())
                            .image_url(ele.getContest().getImage_url())
                            .start_time(ele.getContest().getStart_time())
                            .end_time(ele.getContest().getEnd_time())
                            .is_enabled(ele.getContest().getIs_enabled())
                            .art_age_name(ele.getContest().getArtAges().getName())
                            .art_type_name(ele.getContest().getArtTypes().getName())
                            .build();
                    allContestOpeningResponses.add(contestOpeningResponse);
                }
            });

            allContest.forEach(contest -> {
                if (!allContestForStudent.contains(contest) && time_now.isBefore(contest.getRegistration_time())) {
                    List<UserRegisterJoinContest> listUserRegisterContestByContest = userRegisterJoinContestRepository
                            .findByContestId1(contest.getId());
                    GetContestStudentResponse contestOpeningResponse = GetContestStudentResponse.builder()
                            .id(contest.getId())
                            .name(contest.getName())
                            .description(contest.getDescription())
                            .student_id(student.getId())
                            .student_name(student.getFirstName() + " " + student.getLastName())
                            .max_participant(contest.getMax_participant())
                            .total_register_contest(listUserRegisterContestByContest.size())
                            .total_contest_submission_graded(total)
                            .total_contest_submission(0)
                            .registration_time(contest.getRegistration_time())
                            .image_url(contest.getImage_url())
                            .start_time(contest.getStart_time())
                            .end_time(contest.getEnd_time())
                            .is_enabled(contest.getIs_enabled())
                            .art_age_name(contest.getArtAges().getName())
                            .art_type_name(contest.getArtTypes().getName())
                            .create_time(contest.getCreate_time())
                            .update_time(contest.getUpdate_time())
                            .art_age_id(contest.getArtAges().getId())
                            .art_type_id(contest.getArtTypes().getId())
                            .creator_id(contest.getUser().getId())
                            .build();
                    allContestNewResponses.add(contestOpeningResponse);
                }
            });
        });

        Map<String, Object> response = new HashMap<>();
        response.put("contest_not_open_now", allContestNotOpenNowResponses);
        response.put("contest_opening", allContestOpeningResponses);
        response.put("contest_end", allContestEndResponses);
        response.put("contest_new", allContestNewResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllContestByArtAgeId(int page, int size, UUID id) {
        List<GetContestResponse> allContestResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Contest> pageContest = contestRepository.findAll(paging);
        pageContest.getContent().forEach(contest -> {
            if (contest.getArtAges().getId().compareTo(id) == 0) {
                total = 0;
                List<UserRegisterJoinContest> listUserRegisterContestByContest = userRegisterJoinContestRepository
                    .findByContestId1(contest.getId());
                List<ContestSubmission> listContestSubmissionByContest = contestSubmissionRepository
                        .findByContestId1(contest.getId());
                listContestSubmissionByContest.forEach(contest_submission -> {
                    if (userGradeContestSubmissionRepository.existsByContestSubmissionId(contest_submission.getId())) {
                        total = total + 1;
                    }
                });
                GetContestResponse contestResponse = GetContestResponse.builder()
                        .id(contest.getId())
                        .name(contest.getName())
                        .description(contest.getDescription())
                        .max_participant(contest.getMax_participant())
                        .total_register_contest(listUserRegisterContestByContest.size())
                        .total_contest_submission(listContestSubmissionByContest.size())
                        .total_contest_submission_graded(total)
                        .registration_time(contest.getRegistration_time())
                        .image_url(contest.getImage_url())
                        .start_time(contest.getStart_time())
                        .end_time(contest.getEnd_time())
                        .is_enabled(contest.getIs_enabled())
                        .art_age_id(contest.getArtAges().getId())
                        .art_type_id(contest.getArtTypes().getId())
                        .creator_id(contest.getUser().getId())
                        .create_time(contest.getCreate_time())
                        .update_time(contest.getUpdate_time())
                        .build();
                allContestResponses.add(contestResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("art_age", allContestResponses);
        response.put("currentPage", pageContest.getNumber());
        response.put("totalItems", pageContest.getTotalElements());
        response.put("totalPages", pageContest.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetContestResponse getContestByName(String name) {
        Optional<Contest> contestOpt = contestRepository.findByName2(name);
        Contest contest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Contest.not_found");
        });

        total = 0;
        List<UserRegisterJoinContest> listUserRegisterContestByContest = userRegisterJoinContestRepository
                    .findByContestId1(contest.getId());
        List<ContestSubmission> listContestSubmissionByContest = contestSubmissionRepository
                .findByContestId1(contest.getId());
        listContestSubmissionByContest.forEach(contest_submission -> {
            if (userGradeContestSubmissionRepository.existsByContestSubmissionId(contest_submission.getId())) {
                total = total + 1;
            }
        });

        return GetContestResponse.builder()
                .id(contest.getId())
                .name(contest.getName())
                .description(contest.getDescription())
                .max_participant(contest.getMax_participant())
                .total_register_contest(listUserRegisterContestByContest.size())
                .total_contest_submission(listContestSubmissionByContest.size())
                .total_contest_submission_graded(total)
                .registration_time(contest.getRegistration_time())
                .image_url(contest.getImage_url())
                .start_time(contest.getStart_time())
                .end_time(contest.getEnd_time())
                .is_enabled(contest.getIs_enabled())
                .art_age_id(contest.getArtAges().getId())
                .art_type_id(contest.getArtTypes().getId())
                .creator_id(contest.getUser().getId())
                .create_time(contest.getCreate_time())
                .update_time(contest.getUpdate_time())
                .build();
    }

    @Override
    public GetContestResponse getContestById(UUID id) {
        Optional<Contest> contestOpt = contestRepository.findById2(id);
        Contest contest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Contest.not_found");
        });

        total = 0;
        List<UserRegisterJoinContest> listUserRegisterContestByContest = userRegisterJoinContestRepository
                    .findByContestId1(contest.getId());
        List<ContestSubmission> listContestSubmissionByContest = contestSubmissionRepository
                .findByContestId1(contest.getId());
        listContestSubmissionByContest.forEach(contest_submission -> {
            if (userGradeContestSubmissionRepository.existsByContestSubmissionId(contest_submission.getId())) {
                total = total + 1;
            }
        });

        return GetContestResponse.builder()
                .id(contest.getId())
                .name(contest.getName())
                .description(contest.getDescription())
                .max_participant(contest.getMax_participant())
                .total_register_contest(listUserRegisterContestByContest.size())
                .total_contest_submission(listContestSubmissionByContest.size())
                .total_contest_submission_graded(total)
                .registration_time(contest.getRegistration_time())
                .image_url(contest.getImage_url())
                .start_time(contest.getStart_time())
                .end_time(contest.getEnd_time())
                .is_enabled(contest.getIs_enabled())
                .art_age_id(contest.getArtAges().getId())
                .art_type_id(contest.getArtTypes().getId())
                .creator_id(contest.getUser().getId())
                .create_time(contest.getCreate_time())
                .update_time(contest.getUpdate_time())
                .build();
    }

    @Override
    public UUID createContest(CreateContestRequest createContestRequest) {
        if (contestRepository.existsByName(createContestRequest.getName())) {
            throw new ContestAlreadyCreateException("exception.contest.contest_taken");
        }

        Optional<User> userOpt = userRepository.findById1(createContestRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Optional<ArtAge> artAgeOpt = artAgeRepository.findById(createContestRequest.getArt_age_id());
        ArtAge artAge = artAgeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtAge.not_found");
        });

        Optional<ArtType> artTypeOpt = artTypeRepository.findById(createContestRequest.getArt_type_id());
        ArtType artType = artTypeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtType.not_found");
        });

        Contest savedContest = Contest.builder()
                .name(createContestRequest.getName())
                .description(createContestRequest.getDescription())
                .max_participant(createContestRequest.getMax_participant())
                .registration_time(createContestRequest.getRegistration_time())
                .image_url(createContestRequest.getImage_url())
                .start_time(createContestRequest.getStart_time())
                .end_time(createContestRequest.getEnd_time())
                .is_enabled(createContestRequest.getIs_enabled())
                .user(user)
                .artAges(artAge)
                .artTypes(artType)
                .build();
        contestRepository.save(savedContest);

        return savedContest.getId();
    }

    @Override
    public UUID removeContestById(UUID id) {
        Optional<Contest> contestOpt = contestRepository.findById1(id);
        contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Contest.not_found");
        });
        contestRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateContestById(UUID id, CreateContestRequest createContestRequest) {
        Optional<Contest> contestOpt = contestRepository.findById1(id);
        Contest updatedContest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Contest.not_found");
        });

        Optional<User> userOpt = userRepository.findById1(createContestRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Optional<ArtAge> artAgeOpt = artAgeRepository.findById(createContestRequest.getArt_age_id());
        ArtAge artAge = artAgeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtAge.not_found");
        });

        Optional<ArtType> artTypeOpt = artTypeRepository.findById(createContestRequest.getArt_type_id());
        ArtType artType = artTypeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtType.not_found");
        });
        updatedContest.setName(createContestRequest.getName());
        updatedContest.setDescription(createContestRequest.getDescription());
        updatedContest.setMax_participant(createContestRequest.getMax_participant());
        updatedContest.setRegistration_time(createContestRequest.getRegistration_time());
        updatedContest.setImage_url(createContestRequest.getImage_url());
        updatedContest.setStart_time(createContestRequest.getStart_time());
        updatedContest.setEnd_time(createContestRequest.getEnd_time());
        updatedContest.setUser(user);
        updatedContest.setArtAges(artAge);
        updatedContest.setArtTypes(artType);
        contestRepository.save(updatedContest);

        return updatedContest.getId();
    }
}
