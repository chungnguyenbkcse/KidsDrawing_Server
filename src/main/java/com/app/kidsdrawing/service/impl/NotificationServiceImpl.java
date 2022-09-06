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

import com.app.kidsdrawing.dto.CreateNotificationRequest;
import com.app.kidsdrawing.dto.GetNotificationResponse;
import com.app.kidsdrawing.entity.Notification;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.NotificationRepository;
import com.app.kidsdrawing.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllNotification() {
        List<GetNotificationResponse> allNotificationResponses = new ArrayList<>();
        List<Notification> pageNotification = notificationRepository.findAll();
        pageNotification.forEach(notification -> {
            GetNotificationResponse notificationResponse = GetNotificationResponse.builder()
                    .id(notification.getId())
                    .name(notification.getName())
                    .description(notification.getDescription())
                    .time(notification.getTime())
                    .build();
            allNotificationResponses.add(notificationResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("notifications", allNotificationResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetNotificationResponse getNotificationById(Long id){
        Optional<Notification> notificationOpt = notificationRepository.findById(id);
        Notification notification = notificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Notification.not_found");
        });

        return GetNotificationResponse.builder()
                .id(notification.getId())
                .name(notification.getName())
                .description(notification.getDescription())
                .time(notification.getTime())
                .build();
    }

    @Override
    public Long createNotification(CreateNotificationRequest createNotificationRequest) {

        Notification savedNotification = Notification.builder()
            .name(createNotificationRequest.getName())
            .description(createNotificationRequest.getDescription())
            .build();
        notificationRepository.save(savedNotification);
        return savedNotification.getId();
    }

    @Override
    public Long removeNotificationById(Long id) {
        Optional<Notification> notificationOpt = notificationRepository.findById(id);
        notificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Notification.not_found");
        });

        notificationRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateNotificationById(Long id, CreateNotificationRequest createNotificationRequest) {
        Optional<Notification> notificationOpt = notificationRepository.findById(id);
        Notification updatedNotification = notificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Notification.not_found");
        });
        updatedNotification.setName(createNotificationRequest.getName());
        updatedNotification.setDescription(createNotificationRequest.getDescription());
        notificationRepository.save(updatedNotification);

        return updatedNotification.getId();
    }
}
