package com.app.kidsdrawing.service;
// Importing required classes

import com.app.kidsdrawing.entity.EmailDetails;

// Interface
public interface EmailService {
 
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);
 
    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
