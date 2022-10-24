package com.app.kidsdrawing.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateEmailDetailRequest;
import com.app.kidsdrawing.entity.EmailDetails;
import com.app.kidsdrawing.entity.Notification;
import com.app.kidsdrawing.entity.Role;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserReadNotification;
import com.app.kidsdrawing.entity.UserReadNotificationKey;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.repository.ClassHasRegisterJoinSemesterClassRepository;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.NotificationRepository;
import com.app.kidsdrawing.repository.UserReadNotificationRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.EmailService;
 
// Annotation
@Service
// Class
// Implementing EmailService interface
public class EmailServiceImpl implements EmailService {
 
    @Autowired private JavaMailSender javaMailSender;
    @Autowired private UserRepository userRepository;
    @Autowired private ClassesRepository classRepository;
    @Autowired private NotificationRepository notificationRepository;
    @Autowired private UserReadNotificationRepository uuserReadNotificationRepository;
    @Autowired private ClassHasRegisterJoinSemesterClassRepository classHasRegisterJoinSemesterClassRepository;
 
    @Value("${spring.mail.username}") private String sender;
 
    // Method 1
    // To send a simple email
    public String sendSimpleMail(EmailDetails details)
    {
        System.out.println(sender);
 
        // Try block to check for exceptions
        try {
 
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                = new SimpleMailMessage();
 
            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
 
            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }
 
        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    public String sendMailByAdmin(CreateEmailDetailRequest details){
        // Creating a mime message
        Notification savedNotification = Notification.builder()
            .name(details.getSubject())
            .description(details.getMsgBody())
            .build();
        notificationRepository.save(savedNotification);

        List<User> allUser = userRepository.findAll();

        allUser.forEach(ele -> {
            EmailDetails email = new EmailDetails();
            email.setRecipient(ele.getEmail());
            email.setSubject(details.getSubject());
            email.setMsgBody(details.getMsgBody());
            sendSimpleMail(email);
            UserReadNotificationKey id = new UserReadNotificationKey(ele.getId(), savedNotification.getId());
            UserReadNotification savedUserReadNotification = UserReadNotification.builder()
                .id(id)
                .notification(savedNotification)
                .user(ele)
                .is_read(false)
                .build();
            uuserReadNotificationRepository.save(savedUserReadNotification);
        });
 
        return "Mail Sent Successfully...";
    }

    public String sendMailWithAttachmentByAdmin(CreateEmailDetailRequest details){
        // Creating a mime message
        List<User> allUser = userRepository.findAll();

        allUser.forEach(ele -> {
            EmailDetails email = new EmailDetails();
            email.setRecipient(ele.getEmail());
            email.setSubject(details.getSubject());
            email.setMsgBody(details.getMsgBody());
            email.setAttachment(details.getAttachment());
            sendMailWithAttachment(email);
        });
 
        return "Mail Sent Successfully...";
    }

    public String sendMailByClass(CreateEmailDetailRequest details, UUID id) {
        Optional<Classes> classOpt = classRepository.findById1(id);
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        Notification savedNotification = Notification.builder()
            .name(details.getSubject())
            .description(details.getMsgBody())
            .build();
        notificationRepository.save(savedNotification);

        List<ClassHasRegisterJoinSemesterClass> listClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository.findByClassesId1(classes.getId());

        listClassHasRegisterJoinSemesterClass.forEach(ele -> {
            EmailDetails email = new EmailDetails();
            email.setRecipient(ele.getUserRegisterJoinSemester().getStudent().getEmail());
            email.setSubject(details.getSubject());
            email.setMsgBody(details.getMsgBody());
            sendSimpleMail(email);

            EmailDetails email_1 = new EmailDetails();
            email_1.setRecipient(ele.getUserRegisterJoinSemester().getStudent().getParent().getEmail());
            email_1.setSubject(details.getSubject());
            email_1.setMsgBody(details.getMsgBody());
            sendSimpleMail(email_1);

            UserReadNotificationKey idx = new UserReadNotificationKey(ele.getUserRegisterJoinSemester().getStudent().getId(), savedNotification.getId());
            UserReadNotification savedUserReadNotification = UserReadNotification.builder()
                .id(idx)
                .notification(savedNotification)
                .user(ele.getUserRegisterJoinSemester().getStudent())
                .is_read(false)
                .build();
            uuserReadNotificationRepository.save(savedUserReadNotification);

            UserReadNotificationKey id_1 = new UserReadNotificationKey(ele.getUserRegisterJoinSemester().getStudent().getParent().getId(), savedNotification.getId());
            UserReadNotification savedUserReadNotification_1 = UserReadNotification.builder()
                .id(id_1)
                .notification(savedNotification)
                .user(ele.getUserRegisterJoinSemester().getStudent().getParent())
                .is_read(false)
                .build();
            uuserReadNotificationRepository.save(savedUserReadNotification_1);
        });
        return "Mail Sent Successfully...";
    }

    public String sendMailWithAttachmentByClass(CreateEmailDetailRequest details, UUID id) {
        Optional<Classes> classOpt = classRepository.findById1(id);
        Classes classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        List<ClassHasRegisterJoinSemesterClass> listClassHasRegisterJoinSemesterClass = classHasRegisterJoinSemesterClassRepository.findByClassesId1(classes.getId());

        listClassHasRegisterJoinSemesterClass.forEach(ele -> {
            EmailDetails email = new EmailDetails();
            email.setRecipient(ele.getUserRegisterJoinSemester().getStudent().getEmail());
            email.setSubject(details.getSubject());
            email.setMsgBody(details.getMsgBody());
            email.setAttachment(details.getAttachment());
            sendSimpleMail(email);

            EmailDetails email_1 = new EmailDetails();
            email_1.setRecipient(ele.getUserRegisterJoinSemester().getStudent().getParent().getEmail());
            email_1.setSubject(details.getSubject());
            email_1.setMsgBody(details.getMsgBody());
            email_1.setAttachment(details.getAttachment());
            sendSimpleMail(email_1);
        });
        return "Mail Sent Successfully...";
    }

    public String sendMailToTeacher(CreateEmailDetailRequest details) {
        List<User> allUser = userRepository.findAll();
        Notification savedNotification = Notification.builder()
            .name(details.getSubject())
            .description(details.getMsgBody())
            .build();
        notificationRepository.save(savedNotification);

        allUser.forEach(user -> {           
            List<String> role_name = new ArrayList<>();
            Set<Role> role = user.getRoles();
            role.forEach(ele -> {
                role_name.add(ele.getName());
            });

            if (role_name.contains("TEACHER_USER")){
                EmailDetails email = new EmailDetails();
                email.setRecipient(user.getEmail());
                email.setSubject(details.getSubject());
                email.setMsgBody(details.getMsgBody());
                sendSimpleMail(email);

                UserReadNotificationKey id = new UserReadNotificationKey(user.getId(), savedNotification.getId());
                UserReadNotification savedUserReadNotification = UserReadNotification.builder()
                    .id(id)
                    .notification(savedNotification)
                    .user(user)
                    .is_read(false)
                    .build();
                uuserReadNotificationRepository.save(savedUserReadNotification);
            }
        });
        return "Mail sent Successfully"; 
    }

    public String sendMailAttachmentToTeacher(CreateEmailDetailRequest details) {
        List<User> allUser = userRepository.findAll();
        allUser.forEach(user -> {           
            List<String> role_name = new ArrayList<>();
            Set<Role> role = user.getRoles();
            role.forEach(ele -> {
                role_name.add(ele.getName());
            });

            if (role_name.contains("TEACHER_USER")){
                EmailDetails email = new EmailDetails();
                email.setRecipient(user.getEmail());
                email.setSubject(details.getSubject());
                email.setMsgBody(details.getMsgBody());
                email.setAttachment(details.getAttachment());
                sendSimpleMail(email);
            }
        });
        return "Mail sent Successfully"; 
    }

    public String sendMailToStudent(CreateEmailDetailRequest details) {
        Notification savedNotification = Notification.builder()
            .name(details.getSubject())
            .description(details.getMsgBody())
            .build();
        notificationRepository.save(savedNotification);

        List<User> allUser = userRepository.findAll();
        allUser.forEach(user -> {           
            List<String> role_name = new ArrayList<>();
            Set<Role> role = user.getRoles();
            role.forEach(ele -> {
                role_name.add(ele.getName());
            });

            if (role_name.contains("PARENT_USER") || role_name.contains("STUDENT_USER")){
                EmailDetails email = new EmailDetails();
                email.setRecipient(user.getEmail());
                email.setSubject(details.getSubject());
                email.setMsgBody(details.getMsgBody());
                sendSimpleMail(email);
                UserReadNotificationKey id = new UserReadNotificationKey(user.getId(), savedNotification.getId());
                UserReadNotification savedUserReadNotification = UserReadNotification.builder()
                    .id(id)
                    .notification(savedNotification)
                    .user(user)
                    .is_read(false)
                    .build();
                uuserReadNotificationRepository.save(savedUserReadNotification);
            }
        });
        return "Mail sent Successfully"; 
    }

    public String sendMailAttachmentToStudent(CreateEmailDetailRequest details) {
        List<User> allUser = userRepository.findAll();
        allUser.forEach(user -> {           
            List<String> role_name = new ArrayList<>();
            Set<Role> role = user.getRoles();
            role.forEach(ele -> {
                role_name.add(ele.getName());
            });

            if (role_name.contains("PARENT_USER") || role_name.contains("STUDENT_USER")){
                EmailDetails email = new EmailDetails();
                email.setRecipient(user.getEmail());
                email.setSubject(details.getSubject());
                email.setMsgBody(details.getMsgBody());
                email.setAttachment(details.getAttachment());
                sendSimpleMail(email);
            }
        });
        return "Mail sent Successfully"; 
    }
 
    // Method 2
    // To send an email with attachment
    public String
    sendMailWithAttachment(EmailDetails details)
    {
        // Creating a mime message
        MimeMessage mimeMessage
            = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
 
        try {
 
            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                details.getSubject());
 
            // Adding the attachment
            FileSystemResource file
                = new FileSystemResource(
                    new File(details.getAttachment()));
 
            mimeMessageHelper.addAttachment(
                file.getFilename(), file);
 
            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }
 
        // Catch block to handle MessagingException
        catch (MessagingException e) {
 
            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }
}