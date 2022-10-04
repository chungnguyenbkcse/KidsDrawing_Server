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

import com.app.kidsdrawing.dto.CreateUserReadNotificationRequest;
import com.app.kidsdrawing.dto.GetUserReadNotificationResponse;
import com.app.kidsdrawing.entity.UserReadNotification;
import com.app.kidsdrawing.entity.UserReadNotificationKey;
import com.app.kidsdrawing.entity.Notification;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.NotificationRepository;
import com.app.kidsdrawing.repository.UserReadNotificationRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.UserReadNotificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserReadNotificationServiceImpl implements UserReadNotificationService{
    
    private final UserReadNotificationRepository uuserReadNotificationRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserReadNotification() {
        List<GetUserReadNotificationResponse> allUserReadNotificationResponses = new ArrayList<>();
        List<UserReadNotification> listUserReadNotification = uuserReadNotificationRepository.findAll();
        listUserReadNotification.forEach(content -> {
            GetUserReadNotificationResponse uuserReadNotificationResponse = GetUserReadNotificationResponse.builder()
                .user_id(content.getUser().getId())
                .notification_id(content.getNotification().getId())
                .is_read(content.getIs_read())
                .build();
            allUserReadNotificationResponses.add(uuserReadNotificationResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserReadNotification", allUserReadNotificationResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserReadNotificationByUserId(UUID id) {
        List<GetUserReadNotificationResponse> allUserReadNotificationResponses = new ArrayList<>();
        List<UserReadNotification> listUserReadNotification = uuserReadNotificationRepository.findByUserId(id);
        listUserReadNotification.forEach(content -> {
            if (content.getUser().getId().compareTo(id) == 0){
                GetUserReadNotificationResponse uuserReadNotificationResponse = GetUserReadNotificationResponse.builder()
                    .user_id(content.getUser().getId())
                    .notification_id(content.getNotification().getId())
                    .is_read(content.getIs_read())
                    .build();
                allUserReadNotificationResponses.add(uuserReadNotificationResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserReadNotification", allUserReadNotificationResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUserReadNotificationBynNotificationId(UUID id) {
        List<GetUserReadNotificationResponse> allUserReadNotificationResponses = new ArrayList<>();
        List<UserReadNotification> listUserReadNotification = uuserReadNotificationRepository.findAll();
        listUserReadNotification.forEach(content -> {
            if (content.getNotification().getId().compareTo(id) == 0){
                GetUserReadNotificationResponse uuserReadNotificationResponse = GetUserReadNotificationResponse.builder()
                    .user_id(content.getUser().getId())
                    .notification_id(content.getNotification().getId())
                    .is_read(content.getIs_read())
                    .build();
                allUserReadNotificationResponses.add(uuserReadNotificationResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserReadNotification", allUserReadNotificationResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public UUID createUserReadNotification(CreateUserReadNotificationRequest createUserReadNotificationRequest) {

        Optional <User> userOpt = userRepository.findById1(createUserReadNotificationRequest.getUser_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Optional <Notification> notificationOpt = notificationRepository.findById(createUserReadNotificationRequest.getNotification_id());
        Notification notification = notificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.notification.not_found");
        });

        UserReadNotificationKey id = new UserReadNotificationKey(user.getId(), notification.getId());
        
        UserReadNotification savedUserReadNotification = UserReadNotification.builder()
                .id(id)
                .notification(notification)
                .user(user)
                .is_read(false)
                .build();
        uuserReadNotificationRepository.save(savedUserReadNotification);

        return savedUserReadNotification.getNotification().getId();
    }

    @Override
    public UUID removeUserReadNotificationById(UUID id) {
        Optional<UserReadNotification> uuserReadNotificationOpt = uuserReadNotificationRepository.findById(id);
        uuserReadNotificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.UserReadNotification.not_found");
        });

        uuserReadNotificationRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateUserReadNotificationById(CreateUserReadNotificationRequest createUserReadNotificationRequest) {
        //UserReadNotificationKey index = new UserReadNotificationKey(student_id, submission_id);
        Optional <User> userOpt = userRepository.findById1(createUserReadNotificationRequest.getUser_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Optional <Notification> notificationOpt = notificationRepository.findById(createUserReadNotificationRequest.getNotification_id());
        Notification notification = notificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.notification.not_found");
        });
        List<UserReadNotification> listUserReadNotification = uuserReadNotificationRepository.findAll();
        listUserReadNotification.forEach(content-> {
            if (content.getUser().getId().compareTo(createUserReadNotificationRequest.getUser_id()) == 0 && content.getNotification().getId().compareTo(createUserReadNotificationRequest.getNotification_id()) == 0){
                UserReadNotification updatedUserReadNotification = content;
                UserReadNotificationKey idx = new UserReadNotificationKey(user.getId(), notification.getId());
            
                updatedUserReadNotification.setIs_read(createUserReadNotificationRequest.getIs_read());
                updatedUserReadNotification.setId(idx);
                updatedUserReadNotification.setNotification(notification);
                updatedUserReadNotification.setUser(user);
            }
        });

        return createUserReadNotificationRequest.getNotification_id();
    }
}
