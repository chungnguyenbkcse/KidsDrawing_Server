package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateCourseRequest;
import com.app.kidsdrawing.dto.GetCourseResponse;
import com.app.kidsdrawing.entity.ArtAge;
import com.app.kidsdrawing.entity.ArtLevel;
import com.app.kidsdrawing.entity.ArtType;
import com.app.kidsdrawing.entity.Course;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.CourseAlreadyCreateException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ArtAgeRepository;
import com.app.kidsdrawing.repository.ArtLevelRepository;
import com.app.kidsdrawing.repository.ArtTypeRepository;
import com.app.kidsdrawing.repository.CourseRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.CourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ArtAgeRepository artAgeRepository;
    private final ArtTypeRepository artTypeRepository;
    private final ArtLevelRepository artLevelRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourse(int page, int size) {
        List<GetCourseResponse> allCourseResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Course> pageCourse = courseRepository.findAll(paging);
        pageCourse.getContent().forEach(course -> {
            GetCourseResponse courseResponse = GetCourseResponse.builder()
                    .id(course.getId())
                    .name(course.getName())
                    .description(course.getDescription())
                    .max_participant(course.getMax_participant())
                    .num_of_section(course.getNum_of_section())
                    .image_url(course.getImage_url())
                    .price(course.getPrice())
                    .is_enabled(course.getIs_enabled())
                    .art_age_id(course.getArtAges().getId())
                    .art_type_id(course.getArtTypes().getId())
                    .art_level_id(course.getArtLevels().getId())
                    .creator_id(course.getUser().getId())
                    .build();
            allCourseResponses.add(courseResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("courses", allCourseResponses);
        response.put("currentPage", pageCourse.getNumber());
        response.put("totalItems", pageCourse.getTotalElements());
        response.put("totalPages", pageCourse.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourseByArtTypeId(int page, int size, Long id) {
        List<GetCourseResponse> allCourseResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Course> pageCourse = courseRepository.findAll(paging);
        pageCourse.getContent().forEach(course -> {
            if (course.getArtTypes().getId() == id){
                GetCourseResponse courseResponse = GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .max_participant(course.getMax_participant())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .build();
            allCourseResponses.add(courseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("courses", allCourseResponses);
        response.put("currentPage", pageCourse.getNumber());
        response.put("totalItems", pageCourse.getTotalElements());
        response.put("totalPages", pageCourse.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourseByArtAgeId(int page, int size, Long id) {
        List<GetCourseResponse> allCourseResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Course> pageCourse = courseRepository.findAll(paging);
        pageCourse.getContent().forEach(course -> {
            if (course.getArtAges().getId() == id){
                GetCourseResponse courseResponse = GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .max_participant(course.getMax_participant())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .build();
            allCourseResponses.add(courseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("courses", allCourseResponses);
        response.put("currentPage", pageCourse.getNumber());
        response.put("totalItems", pageCourse.getTotalElements());
        response.put("totalPages", pageCourse.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourseByArtLevelId(int page, int size, Long id) {
        List<GetCourseResponse> allCourseResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Course> pageCourse = courseRepository.findAll(paging);
        pageCourse.getContent().forEach(course -> {
            if (course.getArtLevels().getId() == id){
                GetCourseResponse courseResponse = GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .max_participant(course.getMax_participant())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .build();
            allCourseResponses.add(courseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("courses", allCourseResponses);
        response.put("currentPage", pageCourse.getNumber());
        response.put("totalItems", pageCourse.getTotalElements());
        response.put("totalPages", pageCourse.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetCourseResponse getCourseByName(String name) {
        Optional<Course> courseOpt = courseRepository.findByName(name);
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Course.not_found");
        });

        return GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .max_participant(course.getMax_participant())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .build();
    }

    @Override
    public GetCourseResponse getCourseById(Long id){
        Optional<Course> courseOpt = courseRepository.findById(id);
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Course.not_found");
        });

        return GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .max_participant(course.getMax_participant())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .build();
    }

    @Override
    public Long createCourse(CreateCourseRequest createCourseRequest) {
        if (courseRepository.existsByName(createCourseRequest.getName())) {
            throw new CourseAlreadyCreateException("exception.course.course_taken");
        }

        Optional<User> userOpt = userRepository.findById(createCourseRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Optional<ArtAge> artAgeOpt = artAgeRepository.findById(createCourseRequest.getArt_age_id());
        ArtAge artAge = artAgeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtAge.not_found");
        });

        Optional<ArtType> artTypeOpt = artTypeRepository.findById(createCourseRequest.getArt_type_id());
        ArtType artType = artTypeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtType.not_found");
        });

        Optional<ArtLevel> artLevelOpt = artLevelRepository.findById(createCourseRequest.getArt_level_id());
        ArtLevel artLevel = artLevelOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtLevel.not_found");
        });


        Course savedCourse = Course.builder()
                .name(createCourseRequest.getName())
                .description(createCourseRequest.getDescription())
                .max_participant(createCourseRequest.getMax_participant())
                .num_of_section(createCourseRequest.getNum_of_section())
                .image_url(createCourseRequest.getImage_url())
                .price(createCourseRequest.getPrice())
                .is_enabled(createCourseRequest.getIs_enabled())
                .user(user)
                .artAges(artAge)
                .artTypes(artType)
                .artLevels(artLevel)
                .build();
        courseRepository.save(savedCourse);

        return savedCourse.getId();
    }

    @Override
    public Long removeCourseById(Long id) {
        Optional<Course> courseOpt = courseRepository.findById(id);
        courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Course.not_found");
        });
        courseRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateCourseById(Long id, CreateCourseRequest createCourseRequest) {
        Optional<Course> courseOpt = courseRepository.findById(id);
        Course updatedCourse = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Course.not_found");
        });

        Optional<User> userOpt = userRepository.findById(createCourseRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Optional<ArtAge> artAgeOpt = artAgeRepository.findById(createCourseRequest.getArt_age_id());
        ArtAge artAge = artAgeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtAge.not_found");
        });

        Optional<ArtType> artTypeOpt = artTypeRepository.findById(createCourseRequest.getArt_type_id());
        ArtType artType = artTypeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtType.not_found");
        });

        Optional<ArtLevel> artLevelOpt = artLevelRepository.findById(createCourseRequest.getArt_level_id());
        ArtLevel artLevel = artLevelOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtLevel.not_found");
        });
        updatedCourse.setName(createCourseRequest.getName());
        updatedCourse.setDescription(createCourseRequest.getDescription());
        updatedCourse.setMax_participant(createCourseRequest.getMax_participant());
        updatedCourse.setNum_of_section(createCourseRequest.getNum_of_section());
        updatedCourse.setImage_url(createCourseRequest.getImage_url());
        updatedCourse.setPrice(createCourseRequest.getPrice());
        updatedCourse.setUser(user);
        updatedCourse.setArtAges(artAge);
        updatedCourse.setArtTypes(artType);
        updatedCourse.setArtLevels(artLevel);
        courseRepository.save(updatedCourse);

        return updatedCourse.getId();
    }
}
