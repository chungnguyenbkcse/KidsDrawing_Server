package com.app.kidsdrawing.service;

import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.PnsRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

@Service
public class FCMService {

    public String pushNotification(PnsRequest pnsRequest) {
        Message message = Message.builder()
                .putData("title", pnsRequest.getTitle())
                .putData("body", pnsRequest.getBody())
                .putData("icon", "https://images.pexels.com/photos/1382734/pexels-photo-1382734.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                .putData("click_action", "https://google.com")
                .setToken(pnsRequest.getFcmToken())
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        return response;
    }
}
