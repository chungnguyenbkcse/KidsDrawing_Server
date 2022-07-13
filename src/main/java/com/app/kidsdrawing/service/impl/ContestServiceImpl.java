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

import com.app.kidsdrawing.dto.CreateContestRequest;
import com.app.kidsdrawing.dto.GetContestResponse;
import com.app.kidsdrawing.entity.ArtAge;
import com.app.kidsdrawing.entity.ArtType;
import com.app.kidsdrawing.entity.Contest;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.ContestAlreadyCreateException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ArtAgeRepository;
import com.app.kidsdrawing.repository.ArtTypeRepository;
import com.app.kidsdrawing.repository.ContestRepository;
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

    @Override
    public ResponseEntity<Map<String, Object>> getAllContest(int page, int size) {
        List<GetContestResponse> allContestResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Contest> pageContest = contestRepository.findAll(paging);
        pageContest.getContent().forEach(contest -> {
            GetContestResponse contestResponse = GetContestResponse.builder()
                    .id(contest.getId())
                    .name(contest.getName())
                    .description(contest.getDescription())
                    .max_participant(contest.getMax_participant())
                    .registration_time(contest.getRegistration_time())
                    .image_url(contest.getImage_url())
                    .start_time(contest.getStart_time())
                    .end_time(contest.getEnd_time())
                    .is_enabled(contest.getIs_enabled())
                    .art_age_id(contest.getArtAges().getId())
                    .art_type_id(contest.getArtTypes().getId())
                    .creater_id(contest.getUser().getId())
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
    public ResponseEntity<Map<String, Object>> getAllContestByArtTypeId(int page, int size, Long id) {
        List<GetContestResponse> allContestResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Contest> pageContest = contestRepository.findAll(paging);
        pageContest.getContent().forEach(contest -> {
            if (contest.getArtTypes().getId() == id) {
                GetContestResponse contestResponse = GetContestResponse.builder()
                    .id(contest.getId())
                    .name(contest.getName())
                    .description(contest.getDescription())
                    .max_participant(contest.getMax_participant())
                    .registration_time(contest.getRegistration_time())
                    .image_url(contest.getImage_url())
                    .start_time(contest.getStart_time())
                    .end_time(contest.getEnd_time())
                    .is_enabled(contest.getIs_enabled())
                    .art_age_id(contest.getArtAges().getId())
                    .art_type_id(contest.getArtTypes().getId())
                    .creater_id(contest.getUser().getId())
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
    public ResponseEntity<Map<String, Object>> getAllContestByArtAgeId(int page, int size, Long id) {
        List<GetContestResponse> allContestResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Contest> pageContest = contestRepository.findAll(paging);
        pageContest.getContent().forEach(contest -> {
            if (contest.getArtAges().getId() == id) {
                GetContestResponse contestResponse = GetContestResponse.builder()
                    .id(contest.getId())
                    .name(contest.getName())
                    .description(contest.getDescription())
                    .max_participant(contest.getMax_participant())
                    .registration_time(contest.getRegistration_time())
                    .image_url(contest.getImage_url())
                    .start_time(contest.getStart_time())
                    .end_time(contest.getEnd_time())
                    .is_enabled(contest.getIs_enabled())
                    .art_age_id(contest.getArtAges().getId())
                    .art_type_id(contest.getArtTypes().getId())
                    .creater_id(contest.getUser().getId())
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
        Optional<Contest> contestOpt = contestRepository.findByName(name);
        Contest contest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Contest.not_found");
        });

        return GetContestResponse.builder()
                .id(contest.getId())
                .name(contest.getName())
                .description(contest.getDescription())
                .max_participant(contest.getMax_participant())
                .registration_time(contest.getRegistration_time())
                .image_url(contest.getImage_url())
                .start_time(contest.getStart_time())
                .end_time(contest.getEnd_time())
                .is_enabled(contest.getIs_enabled())
                .art_age_id(contest.getArtAges().getId())
                .art_type_id(contest.getArtTypes().getId())
                .creater_id(contest.getUser().getId())
                .build();
    }

    @Override
    public GetContestResponse getContestById(Long id){
        Optional<Contest> contestOpt = contestRepository.findById(id);
        Contest contest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Contest.not_found");
        });

        return GetContestResponse.builder()
                .id(contest.getId())
                .name(contest.getName())
                .description(contest.getDescription())
                .max_participant(contest.getMax_participant())
                .registration_time(contest.getRegistration_time())
                .image_url(contest.getImage_url())
                .start_time(contest.getStart_time())
                .end_time(contest.getEnd_time())
                .is_enabled(contest.getIs_enabled())
                .art_age_id(contest.getArtAges().getId())
                .art_type_id(contest.getArtTypes().getId())
                .creater_id(contest.getUser().getId())
                .build();
    }

    @Override
    public Long createContest(CreateContestRequest createContestRequest) {
        if (contestRepository.existsByName(createContestRequest.getName())) {
            throw new ContestAlreadyCreateException("exception.contest.contest_taken");
        }

        Optional<User> userOpt = userRepository.findById(createContestRequest.getCreater_id());
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
    public Long removeContestById(Long id) {
        Optional<Contest> contestOpt = contestRepository.findById(id);
        contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Contest.not_found");
        });
        contestRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateContestById(Long id, CreateContestRequest createContestRequest) {
        Optional<Contest> contestOpt = contestRepository.findById(id);
        Contest updatedContest = contestOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Contest.not_found");
        });

        Optional<User> userOpt = userRepository.findById(createContestRequest.getCreater_id());
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
