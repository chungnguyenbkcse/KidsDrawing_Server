package com.app.kidsdrawing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.kidsdrawing.entity.EmailDetails;
import com.app.kidsdrawing.service.EmailService;
 
// Annotation
@RestController
@RequestMapping(value = "/api/v1/sendEmail")
public class EmailController {
 
    @Autowired private EmailService emailService;
 
    // Sending a simple Email
    @PostMapping
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        System.out.println("Hello");
        String status
            = emailService.sendSimpleMail(details);
 
        return status;
    }
 
    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
        @RequestBody EmailDetails details)
    {
        String status
            = emailService.sendMailWithAttachment(details);
 
        return status;
    }
}