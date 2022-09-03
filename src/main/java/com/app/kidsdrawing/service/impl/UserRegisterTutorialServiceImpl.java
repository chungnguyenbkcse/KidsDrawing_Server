package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateUserRegisterTutorialRequest;
import com.app.kidsdrawing.dto.GetUserRegisterTutorialResponse;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserRegisterTutorial;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.repository.UserRegisterTutorialRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.UserRegisterTutorialService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserRegisterTutorialServiceImpl implements UserRegisterTutorialService{
    
    private final UserRegisterTutorialRepository userRegisterTutorialRepository;
    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserRegisterTutorial() {
        List<GetUserRegisterTutorialResponse> allUserRegisterTutorialResponses = new ArrayList<>();
        List<UserRegisterTutorial> listUserRegisterTutorial = userRegisterTutorialRepository.findAll();
        listUserRegisterTutorial.forEach(content -> {
            GetUserRegisterTutorialResponse UserRegisterTutorialResponse = GetUserRegisterTutorialResponse.builder()
                .id(content.getId())
                .section_id(content.getSection().getId())
                .section_name(content.getSection().getName())
                .creator_id(content.getCreator().getId())
                .creator_name(content.getCreator().getFirstName() + " " + content.getCreator().getLastName())
                .name(content.getName())
                .status(content.getStatus())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allUserRegisterTutorialResponses.add(UserRegisterTutorialResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_tutorial", allUserRegisterTutorialResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserRegisterTutorialBySection(Long id) {
        List<GetUserRegisterTutorialResponse> allUserRegisterTutorialResponses = new ArrayList<>();
        List<UserRegisterTutorial> listUserRegisterTutorial = userRegisterTutorialRepository.findBySectionId(id);
        listUserRegisterTutorial.forEach(content -> {
            GetUserRegisterTutorialResponse UserRegisterTutorialResponse = GetUserRegisterTutorialResponse.builder()
                .id(content.getId())
                .section_id(content.getSection().getId())
                .section_name(content.getSection().getName())
                .creator_id(content.getCreator().getId())
                .creator_name(content.getCreator().getFirstName() + " " + content.getCreator().getLastName())
                .name(content.getName())
                .status(content.getStatus())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allUserRegisterTutorialResponses.add(UserRegisterTutorialResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("user_register_tutorial", allUserRegisterTutorialResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetUserRegisterTutorialResponse getUserRegisterTutorialById(Long id) {
        Optional<UserRegisterTutorial> UserRegisterTutorialOpt = userRegisterTutorialRepository.findById(id);
        UserRegisterTutorial UserRegisterTutorial = UserRegisterTutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterTutorial.not_found");
        });

        return GetUserRegisterTutorialResponse.builder()
            .id(UserRegisterTutorial.getId())
            .section_id(UserRegisterTutorial.getSection().getId())
            .section_name(UserRegisterTutorial.getSection().getName())
            .creator_id(UserRegisterTutorial.getCreator().getId())
            .creator_name(UserRegisterTutorial.getCreator().getFirstName() + " " + UserRegisterTutorial.getCreator().getLastName())
            .name(UserRegisterTutorial.getName())
            .status(UserRegisterTutorial.getStatus())
            .create_time(UserRegisterTutorial.getCreate_time())
            .update_time(UserRegisterTutorial.getUpdate_time())
            .build();
    }

    @Override
    public Long createUserRegisterTutorial(CreateUserRegisterTutorialRequest createUserRegisterTutorialRequest) {
        Optional <Section> sectionOpt = sectionRepository.findById(createUserRegisterTutorialRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <User> userOpt = userRepository.findById(createUserRegisterTutorialRequest.getCreator_id());
        User creator = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });
        
        UserRegisterTutorial savedUserRegisterTutorial = UserRegisterTutorial.builder()
                .creator(creator)
                .section(section)
                .name(createUserRegisterTutorialRequest.getName())
                .status(createUserRegisterTutorialRequest.getStatus())
                .build();
        userRegisterTutorialRepository.save(savedUserRegisterTutorial);

        return savedUserRegisterTutorial.getId();
    }

    @Override
    public Long removeUserRegisterTutorialById(Long id) {
        Optional<UserRegisterTutorial> UserRegisterTutorialOpt = userRegisterTutorialRepository.findById(id);
        UserRegisterTutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterTutorial.not_found");
        });

        userRegisterTutorialRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateUserRegisterTutorialById(Long id, CreateUserRegisterTutorialRequest createUserRegisterTutorialRequest) {
        Optional<UserRegisterTutorial> UserRegisterTutorialOpt = userRegisterTutorialRepository.findById(id);
        UserRegisterTutorial updatedUserRegisterTutorial = UserRegisterTutorialOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserRegisterTutorial.not_found");
        });

        Optional <Section> sectionOpt = sectionRepository.findById(createUserRegisterTutorialRequest.getSection_id());
        Section section = sectionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.section.not_found");
        });

        Optional <User> userOpt = userRepository.findById(createUserRegisterTutorialRequest.getCreator_id());
        User creator = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        updatedUserRegisterTutorial.setCreator(creator);
        updatedUserRegisterTutorial.setSection(section);
        updatedUserRegisterTutorial.setName(createUserRegisterTutorialRequest.getName());
        updatedUserRegisterTutorial.setStatus(createUserRegisterTutorialRequest.getStatus());

        userRegisterTutorialRepository.save(updatedUserRegisterTutorial);

        return updatedUserRegisterTutorial.getId();
    }
}