package com.app.kidsdrawing.service;
// Importing required classes

import com.app.kidsdrawing.dto.CreateEmailDetailRequest;
import com.app.kidsdrawing.entity.EmailDetails;

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

    // Teacher send mail for student
    String sendMailByClass(CreateEmailDetailRequest details, Long id);
    String sendMailWithAttachmentByClass(CreateEmailDetailRequest details, Long id);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
