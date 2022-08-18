package com.app.kidsdrawing.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.app.kidsdrawing.entity.Role;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.entity.Class;
import com.app.kidsdrawing.repository.ClassRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.EmailService;
 
// Annotation
@Service
// Class
// Implementing EmailService interface
public class EmailServiceImpl implements EmailService {
 
    @Autowired private JavaMailSender javaMailSender;
    @Autowired private UserRepository userRepository;
    @Autowired private ClassRepository classRepository;
 
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
        List<User> allUser = userRepository.findAll();

        allUser.forEach(ele -> {
            EmailDetails email = new EmailDetails();
            email.setRecipient(ele.getEmail());
            email.setSubject(details.getSubject());
            email.setMsgBody(details.getMsgBody());
            sendSimpleMail(email);
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

    public String sendMailByClass(CreateEmailDetailRequest details, Long id) {
        Optional<Class> classOpt = classRepository.findById(id);
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        classes.getUserRegisterJoinSemesters().forEach(ele -> {
            EmailDetails email = new EmailDetails();
            email.setRecipient(ele.getStudent().getEmail());
            email.setSubject(details.getSubject());
            email.setMsgBody(details.getMsgBody());
            sendSimpleMail(email);
        });

        classes.getUserRegisterJoinSemesters().forEach(ele -> {
            EmailDetails email = new EmailDetails();
            email.setRecipient(ele.getStudent().getParent().getEmail());
            email.setSubject(details.getSubject());
            email.setMsgBody(details.getMsgBody());
            sendSimpleMail(email);
        });
        return "Mail Sent Successfully...";
    }

    public String sendMailWithAttachmentByClass(CreateEmailDetailRequest details, Long id) {
        Optional<Class> classOpt = classRepository.findById(id);
        Class classes = classOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Class.not_found");
        });

        classes.getUserRegisterJoinSemesters().forEach(ele -> {
            EmailDetails email = new EmailDetails();
            email.setRecipient(ele.getStudent().getEmail());
            email.setSubject(details.getSubject());
            email.setMsgBody(details.getMsgBody());
            email.setAttachment(details.getAttachment());
            sendSimpleMail(email);
        });

        classes.getUserRegisterJoinSemesters().forEach(ele -> {
            EmailDetails email = new EmailDetails();
            email.setRecipient(ele.getStudent().getParent().getEmail());
            email.setSubject(details.getSubject());
            email.setMsgBody(details.getMsgBody());
            email.setAttachment(details.getAttachment());
            sendSimpleMail(email);
        });
        return "Mail Sent Successfully...";
    }

    public String sendMailToTeacher(CreateEmailDetailRequest details) {
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
                sendSimpleMail(email);
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