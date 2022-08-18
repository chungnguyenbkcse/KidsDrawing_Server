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

import com.app.kidsdrawing.dto.CreateAnonymousNotificationRequest;
import com.app.kidsdrawing.dto.GetAnonymousNotificationResponse;
import com.app.kidsdrawing.entity.AnonymousNotification;
import com.app.kidsdrawing.entity.Notification;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.AnonymousNotificationRepository;
import com.app.kidsdrawing.repository.NotificationRepository;
import com.app.kidsdrawing.service.AnonymousNotificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class AnonymousNotificationServiceImpl implements AnonymousNotificationService {

    private final AnonymousNotificationRepository anonymousNoficationRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllAnonymousNotification() {
        List<GetAnonymousNotificationResponse> allAnonymousNotificationResponses = new ArrayList<>();
        List<AnonymousNotification> pageAnonymousNotification = anonymousNoficationRepository.findAll();
        pageAnonymousNotification.forEach(anonymous_nofication -> {
            GetAnonymousNotificationResponse anonymousNoficationResponse = GetAnonymousNotificationResponse.builder()
                    .notification_id(anonymous_nofication.getNotification().getId())
                    .notification_name(anonymous_nofication.getNotification().getName())
                    .notification_body(anonymous_nofication.getNotification().getDescription())
                    .time(anonymous_nofication.getNotification().getTime())
                    .email(anonymous_nofication.getEmail())
                    .phone(anonymous_nofication.getPhone())
                    .build();
            allAnonymousNotificationResponses.add(anonymousNoficationResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("anonymous_nofication", allAnonymousNotificationResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public GetAnonymousNotificationResponse getAnonymousNotificationById(Long id){
        Optional<AnonymousNotification> anonymousNoficationOpt = anonymousNoficationRepository.findById(id);
        AnonymousNotification anonymousNofication = anonymousNoficationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.AnonymousNotification.not_found");
        });

        return GetAnonymousNotificationResponse.builder()
                .notification_id(anonymousNofication.getNotification().getId())
                .notification_name(anonymousNofication.getNotification().getName())
                .notification_body(anonymousNofication.getNotification().getDescription())
                .time(anonymousNofication.getNotification().getTime())
                .email(anonymousNofication.getEmail())
                .phone(anonymousNofication.getPhone())
                .build();
    }

    @Override
    public Long createAnonymousNotification(CreateAnonymousNotificationRequest createAnonymousNotificationRequest) {
        Optional<Notification> notificationOpt = notificationRepository.findById(createAnonymousNotificationRequest.getNotification_id());
        Notification notification = notificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Notification.not_found");
        });
        AnonymousNotification savedAnonymousNotification = AnonymousNotification.builder()
                .notification(notification)
                .email(createAnonymousNotificationRequest.getEmail())
                .phone(createAnonymousNotificationRequest.getPhone())
                .user_full_name(createAnonymousNotificationRequest.getUser_full_name())
                .build();
        anonymousNoficationRepository.save(savedAnonymousNotification);

        return savedAnonymousNotification.getNotification().getId();
    }

    @Override
    public Long removeAnonymousNotificationByNotificationId(Long id) {
        Optional<AnonymousNotification> anonymousNoficationOpt = anonymousNoficationRepository.findByNotificationId(id);
        anonymousNoficationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.AnonymousNotification.not_found");
        });

        anonymousNoficationRepository.deleteByNotificationId(id);
        return id;
    }

    @Override
    public Long updateAnonymousNotificationById(CreateAnonymousNotificationRequest createAnonymousNotificationRequest) {
        Optional<AnonymousNotification> anonymousNoficationOpt = anonymousNoficationRepository.findByNotificationId(createAnonymousNotificationRequest.getNotification_id());
        AnonymousNotification updatedAnonymousNotification = anonymousNoficationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.AnonymousNotification.not_found");
        });

        Optional<Notification> notificationOpt = notificationRepository.findById(createAnonymousNotificationRequest.getNotification_id());
        Notification notification = notificationOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Notification.not_found");
        });
        updatedAnonymousNotification.setNotification(notification);
        updatedAnonymousNotification.setEmail(createAnonymousNotificationRequest.getEmail());
        updatedAnonymousNotification.setPhone(createAnonymousNotificationRequest.getPhone());
        updatedAnonymousNotification.setUser_full_name(createAnonymousNotificationRequest.getUser_full_name());
        anonymousNoficationRepository.save(updatedAnonymousNotification);

        return updatedAnonymousNotification.getNotification().getId();
    }
}
