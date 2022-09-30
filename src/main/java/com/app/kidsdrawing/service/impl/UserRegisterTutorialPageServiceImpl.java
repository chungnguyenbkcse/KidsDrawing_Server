package com.app.kidsdrawing.service.impl;

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

import com.app.kidsdrawing.dto.CreateUserRegisterTutorialPageRequest;
import com.app.kidsdrawing.dto.GetUserRegisterTutorialPageResponse;
import com.app.kidsdrawing.entity.UserRegisterTutorial;
import com.app.kidsdrawing.entity.UserRegisterTutorialPage;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.UserRegisterTutorialRepository;
import com.app.kidsdrawing.repository.UserRegisterTutorialPageRepository;
import com.app.kidsdrawing.service.UserRegisterTutorialPageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserRegisterTutorialPageServiceImpl implements UserRegisterTutorialPageService{
    
    private final UserRegisterTutorialPageRepository userRegisterTutorialPageRepository;
    private final UserRegisterTutorialRepository userRegisterTutorialRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserRegisterTutorialPage() {
        List<GetUserRegisterTutorialPageResponse> allUserRegisterTutorialPageResponses = new ArrayList<>();
        List<UserRegisterTutorialPage> listUserRegisterTutorialPage = userRegisterTutorialPageRepository.findAll();
        listUserRegisterTutorialPage.forEach(content -> {
            GetUserRegisterTutorialPageResponse UserRegisterTutorialPageResponse = GetUserRegisterTutorialPageResponse.builder()
                .id(content.getId())
                .user_register_tutorial_id(content.getUserRegisterTutorial().getId())
                .name(content.getName())
                .description(content.getDescription())
                .number(content.getNumber())
                .build();
            allUserRegisterTutorialPageResponses.add(UserRegisterTutorialPageResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_tutorial_page", allUserRegisterTutorialPageResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserRegisterTutorialPageByUserRegiseterTutorial(UUID id) {
        List<GetUserRegisterTutorialPageResponse> allUserRegisterTutorialPageResponses = new ArrayList<>();
        List<UserRegisterTutorialPage> listUserRegisterTutorialPage = userRegisterTutorialPageRepository.findByUserRegisterTutorialId(id);
        listUserRegisterTutorialPage.forEach(content -> {
            GetUserRegisterTutorialPageResponse UserRegisterTutorialPageResponse = GetUserRegisterTutorialPageResponse.builder()
                .id(content.getId())
                .user_register_tutorial_id(content.getUserRegisterTutorial().getId())
                .name(content.getName())
                .description(content.getDescription())
                .number(content.getNumber())
                .build();
            allUserRegisterTutorialPageResponses.add(UserRegisterTutorialPageResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_tutorial_page", allUserRegisterTutorialPageResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetUserRegisterTutorialPageResponse getUserRegisterTutorialPageById(UUID id) {
        Optional<UserRegisterTutorialPage> UserRegisterTutorialPageOpt = userRegisterTutorialPageRepository.findById(id);
        UserRegisterTutorialPage UserRegisterTutorialPage = UserRegisterTutorialPageOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterTutorialPage.not_found");
        });

        return GetUserRegisterTutorialPageResponse.builder()
            .id(UserRegisterTutorialPage.getId())
            .user_register_tutorial_id(UserRegisterTutorialPage.getUserRegisterTutorial().getId())
            .name(UserRegisterTutorialPage.getName())
            .description(UserRegisterTutorialPage.getDescription())
            .number(UserRegisterTutorialPage.getNumber())
            .build();
    }

    @Override
    public UUID createUserRegisterTutorialPage(CreateUserRegisterTutorialPageRequest createUserRegisterTutorialPageRequest) {
        Optional <UserRegisterTutorial> user_register_tutorialOpt = userRegisterTutorialRepository.findById(createUserRegisterTutorialPageRequest.getUser_register_tutorial_id());
        UserRegisterTutorial userRegisterTutorial = user_register_tutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_register_tutorial.not_found");
        });
        
        UserRegisterTutorialPage savedUserRegisterTutorialPage = UserRegisterTutorialPage.builder()
                .userRegisterTutorial(userRegisterTutorial)
                .name(createUserRegisterTutorialPageRequest.getName())
                .description(createUserRegisterTutorialPageRequest.getDescription())
                .number(createUserRegisterTutorialPageRequest.getNumber())
                .build();
        userRegisterTutorialPageRepository.save(savedUserRegisterTutorialPage);

        return savedUserRegisterTutorialPage.getId();
    }

    @Override
    public UUID removeUserRegisterTutorialPageById(UUID id) {
        Optional<UserRegisterTutorialPage> UserRegisterTutorialPageOpt = userRegisterTutorialPageRepository.findById(id);
        UserRegisterTutorialPageOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterTutorialPage.not_found");
        });

        userRegisterTutorialPageRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateUserRegisterTutorialPageById(UUID id, CreateUserRegisterTutorialPageRequest createUserRegisterTutorialPageRequest) {
        Optional<UserRegisterTutorialPage> UserRegisterTutorialPageOpt = userRegisterTutorialPageRepository.findById(id);
        UserRegisterTutorialPage updatedUserRegisterTutorialPage = UserRegisterTutorialPageOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterTutorialPage.not_found");
        });

        Optional <UserRegisterTutorial> user_register_tutorialOpt = userRegisterTutorialRepository.findById(createUserRegisterTutorialPageRequest.getUser_register_tutorial_id());
        UserRegisterTutorial userRegisterTutorial = user_register_tutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_register_tutorial.not_found");
        });

        updatedUserRegisterTutorialPage.setUserRegisterTutorial(userRegisterTutorial);
        updatedUserRegisterTutorialPage.setName(createUserRegisterTutorialPageRequest.getName());
        updatedUserRegisterTutorialPage.setDescription(createUserRegisterTutorialPageRequest.getDescription());
        updatedUserRegisterTutorialPage.setNumber(createUserRegisterTutorialPageRequest.getNumber());

        userRegisterTutorialPageRepository.save(updatedUserRegisterTutorialPage);

        return updatedUserRegisterTutorialPage.getId();
    }
}
