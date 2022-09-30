package com.app.kidsdrawing.service;
// Importing required classes

import com.app.kidsdrawing.dto.CreateEmailDetailRequest;
import com.app.kidsdrawing.entity.EmailDetails;
import java.util.UUID;

// Interface
public interface EmailService {
 
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);
 
    // Admin send mail for user
    String sendMailByAdmin(CreateEmailDetailRequest details);
    String sendMailWithAttachmentByAdmin(CreateEmailDetailRequest details);
    String sendMailToTeacher(CreateEmailDetailRequest details);
    String sendMailToStudent(CreateEmailDetailRequest details);
    String sendMailAttachmentToTeacher(CreateEmailDetailRequest details);
    String sendMailAttachmentToStudent(CreateEmailDetailRequest details);

    // Teacher send mail for student
    String sendMailByClass(CreateEmailDetailRequest details, UUID id);
    String sendMailWithAttachmentByClass(CreateEmailDetailRequest details, UUID id);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
