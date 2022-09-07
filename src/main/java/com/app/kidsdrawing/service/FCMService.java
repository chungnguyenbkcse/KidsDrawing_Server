package com.app.kidsdrawing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.PnsRequest;
import com.app.kidsdrawing.entity.Role;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.repository.ClassHasRegisterJoinSemesterClassRepository;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FCMService {

    private final UserRepository userRepository;
    private final ClassesRepository classRepository;
    private final ClassHasRegisterJoinSemesterClassRepository classHasRegisterJoinSemesterClassRepository;

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


    public String pushNotificationForTeacher(PnsRequest pnsRequest) {
        List<User> allUser = userRepository.findAll();
        allUser.forEach(user -> {           
            List<String> role_name = new ArrayList<>();
            Set<Role> role = user.getRoles();
            role.forEach(ele -> {
                role_name.add(ele.getName());
            });

            if (role_name.contains("TEACHER_USER")){
                Message message = Message.builder()
                    .putData("title", pnsRequest.getTitle())
                    .putData("body", pnsRequest.getBody())
                    .putData("icon", "https://images.pexels.com/photos/1382734/pexels-photo-1382734.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                    .putData("click_action", "https://google.com")
                    .setToken(user.getStatus())
                .build();
                try {
                    FirebaseMessaging.getInstance().send(message);
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
            }
        });
        return "Send successfull";
    }


    public String pushNotificationForStudent(PnsRequest pnsRequest) {
        List<User> allUser = userRepository.findAll();
        allUser.forEach(user -> {           
            List<String> role_name = new ArrayList<>();
            Set<Role> role = user.getRoles();
            role.forEach(ele -> {
                role_name.add(ele.getName());
            });

            if (role_name.contains("PARENT_USER") || role_name.contains("STUDENT_USER")){
                Message message = Message.builder()
                    .putData("title", pnsRequest.getTitle())
                    .putData("body", pnsRequest.getBody())
                    .putData("icon", "https://images.pexels.com/photos/1382734/pexels-photo-1382734.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                    .putData("click_action", "https://google.com")
                    .setToken(user.getStatus())
                .build();
                try {
                    FirebaseMessaging.getInstance().send(message);
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
            }
        });
        return "Send successfull";
    }


    public String pushNotificationForAll(PnsRequest pnsRequest) {
        List<User> allUser = userRepository.findAll();
        allUser.forEach(user -> {           
            Message message = Message.builder()
                .putData("title", pnsRequest.getTitle())
                .putData("body", pnsRequest.getBody())
                .putData("icon", "https://images.pexels.com/photos/1382734/pexels-photo-1382734.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                .putData("click_action", "https://google.com")
                .setToken(user.getStatus())
            .build();
            try {
                FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }
        });
        return "Send successfull";
    }

    public String pushNotificationForClass(Long id, PnsRequest pnsRequest) {

        Optional<Classes> classOpt = classRepository.findById(id);
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        List<ClassHasRegisterJoinSemesterClass> listClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository.findByClassesId(classes.getId());
        listClassHasRegisterJoinSemesterClass.forEach(ele -> {
            Message message = Message.builder()
                .putData("title", pnsRequest.getTitle())
                .putData("body", pnsRequest.getBody())
                .putData("icon", "https://images.pexels.com/photos/1382734/pexels-photo-1382734.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                .putData("click_action", "https://google.com")
                .setToken(ele.getUserRegisterJoinSemester().getStudent().getStatus())
                .build();
            try {
                FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }

            Message message_1 = Message.builder()
                .putData("title", pnsRequest.getTitle())
                .putData("body", pnsRequest.getBody())
                .putData("icon", "https://images.pexels.com/photos/1382734/pexels-photo-1382734.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                .putData("click_action", "https://google.com")
                .setToken(ele.getUserRegisterJoinSemester().getStudent().getParent().getStatus())
                .build();
            try {
                FirebaseMessaging.getInstance().send(message_1);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }
        });

        return "Send successfull";
    }
}
