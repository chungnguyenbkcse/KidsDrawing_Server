package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateTeacherRegisterQualificationRequest;
import com.app.kidsdrawing.dto.GetTeacherRegisterQualificationResponse;
import com.app.kidsdrawing.entity.ArtAge;
import com.app.kidsdrawing.entity.ArtType;
import com.app.kidsdrawing.entity.TeacherRegisterQualification;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ArtAgeRepository;
import com.app.kidsdrawing.repository.ArtTypeRepository;
import com.app.kidsdrawing.repository.TeacherRegisterQualificationRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.TeacherRegisterQualificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TeacherRegisterQualificationServiceImpl implements TeacherRegisterQualificationService{
    
    private final TeacherRegisterQualificationRepository teacherRegisterQualificationRepository;
    private final ArtAgeRepository artAgeRepository;
    private final ArtTypeRepository artTypeRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacherRegisterQualificationByTeacherId(int page, int size, Long id) {
        List<GetTeacherRegisterQualificationResponse> allTeacherRegisterQualificationResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<TeacherRegisterQualification> pageTeacherRegisterQualification = teacherRegisterQualificationRepository.findAll(paging);
        pageTeacherRegisterQualification.getContent().forEach(content -> {
            if (content.getTeacher().getId() == id){
                GetTeacherRegisterQualificationResponse teacherRegisterQualificationResponse = GetTeacherRegisterQualificationResponse.builder()
                    .id(content.getId())
                    .art_type_id(content.getArtTypes().getId())
                    .art_age_id(content.getArtAges().getId())
                    .degree_photo_url(content.getDegree_photo_url())
                    .status(content.getStatus())
                    .build();
                    allTeacherRegisterQualificationResponses.add(teacherRegisterQualificationResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("teacher_register_qualification", allTeacherRegisterQualificationResponses);
        response.put("currentPage", pageTeacherRegisterQualification.getNumber());
        response.put("totalItems", pageTeacherRegisterQualification.getTotalElements());
        response.put("totalPages", pageTeacherRegisterQualification.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetTeacherRegisterQualificationResponse getTeacherRegisterQualificationById(Long id) {
        Optional<TeacherRegisterQualification> teacherRegisterQualificationOpt = teacherRegisterQualificationRepository.findById(id);
        TeacherRegisterQualification teacherRegisterQualification = teacherRegisterQualificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterQualification.not_found");
        });

        return GetTeacherRegisterQualificationResponse.builder()
                .id(teacherRegisterQualification.getId())
                .art_type_id(teacherRegisterQualification.getArtTypes().getId())
                .art_age_id(teacherRegisterQualification.getArtAges().getId())
                .degree_photo_url(teacherRegisterQualification.getDegree_photo_url())
                .status(teacherRegisterQualification.getStatus())
                .build();
    }

    @Override
    public Long createTeacherRegisterQualification(CreateTeacherRegisterQualificationRequest createTeacherRegisterQualificationRequest) {
        ArtType art_type = artTypeRepository.getById(createTeacherRegisterQualificationRequest.getArt_type_id());
        ArtAge art_age = artAgeRepository.getById(createTeacherRegisterQualificationRequest.getArt_age_id());
        User teacher = userRepository.getById(createTeacherRegisterQualificationRequest.getTeacher_id());
        TeacherRegisterQualification savedTeacherRegisterQualification = TeacherRegisterQualification.builder()
                .artTypes(art_type)
                .artAges(art_age)
                .status(createTeacherRegisterQualificationRequest.getStatus())
                .degree_photo_url(createTeacherRegisterQualificationRequest.getDegree_photo_url())
                .teacher(teacher)
                .build();
        teacherRegisterQualificationRepository.save(savedTeacherRegisterQualification);

        return savedTeacherRegisterQualification.getId();
    }

    @Override
    public Long removeTeacherRegisterQualificationById(Long id) {
        Optional<TeacherRegisterQualification> teacherRegisterQualificationOpt = teacherRegisterQualificationRepository.findById(id);
        teacherRegisterQualificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterQualification.not_found");
        });

        teacherRegisterQualificationRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateTeacherRegisterQualificationById(Long id, CreateTeacherRegisterQualificationRequest createTeacherRegisterQualificationRequest) {
        Optional<TeacherRegisterQualification> teacherRegisterQualificationOpt = teacherRegisterQualificationRepository.findById(id);
        TeacherRegisterQualification updatedTeacherRegisterQualification = teacherRegisterQualificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterQualification.not_found");
        });
        ArtType art_type = artTypeRepository.getById(createTeacherRegisterQualificationRequest.getArt_type_id());
        ArtAge art_age = artAgeRepository.getById(createTeacherRegisterQualificationRequest.getArt_age_id());
        updatedTeacherRegisterQualification.setArtAges(art_age);
        updatedTeacherRegisterQualification.setArtTypes(art_type);
        updatedTeacherRegisterQualification.setDegree_photo_url(createTeacherRegisterQualificationRequest.getDegree_photo_url());
        teacherRegisterQualificationRepository.save(updatedTeacherRegisterQualification);

        return updatedTeacherRegisterQualification.getId();
    }

    @Override
    public Long updateStatusTeacherRegisterQualificationById(Long id, Long admin_id) {
        Optional<TeacherRegisterQualification> teacherRegisterQualificationOpt = teacherRegisterQualificationRepository.findById(id);
        User reviewer = userRepository.getById(admin_id);
        
        TeacherRegisterQualification updatedTeacherRegisterQualification = teacherRegisterQualificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.TeacherRegisterQualification.not_found");
        });

        updatedTeacherRegisterQualification.setStatus(true);
        updatedTeacherRegisterQualification.setReviewer(reviewer);
        teacherRegisterQualificationRepository.save(updatedTeacherRegisterQualification);

        return updatedTeacherRegisterQualification.getId();
    }
}
