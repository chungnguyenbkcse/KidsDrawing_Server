package com.app.kidsdrawing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.kidsdrawing.dto.CreateEmailDetailRequest;
import com.app.kidsdrawing.entity.EmailDetails;
import com.app.kidsdrawing.service.EmailService;
 
// Annotation
@RestController
@RequestMapping(value = "/api/v1/sendEmail")
public class EmailController {
 
    @Autowired private EmailService emailService;
 
    // Sending a simple Email
    @CrossOrigin
    @PostMapping
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        
        String status
            = emailService.sendSimpleMail(details);
 
        return status;
    }

    @CrossOrigin
    @PostMapping(value = "/admin")
    public String
    sendMailByAdmin(@RequestBody CreateEmailDetailRequest details)
    {
        
        String status
            = emailService.sendMailByAdmin(details);
 
        return status;
    }

    @CrossOrigin
    @PostMapping(value = "/admin/attachment")
    public String
    sendMailWithAttachmentByAdmin(@RequestBody CreateEmailDetailRequest details)
    {
        
        String status
            = emailService.sendMailWithAttachmentByAdmin(details);
 
        return status;
    }

    @CrossOrigin
    @PostMapping(value = "/class/{id}")
    public String
    sendMailByClass(@PathVariable Long id, @RequestBody CreateEmailDetailRequest details)
    {
        
        String status
            = emailService.sendMailByClass(details, id);
 
        return status;
    }


    @CrossOrigin
    @PostMapping(value = "/teacher")
    public String
    sendMailToTeacher(@RequestBody CreateEmailDetailRequest details)
    {
        
        String status
            = emailService.sendMailToTeacher(details);
 
        return status;
    }

    @CrossOrigin
    @PostMapping(value = "/teacher/attachment")
    public String
    sendMailAttachmentToTeacher(@RequestBody CreateEmailDetailRequest details)
    {
        
        String status
            = emailService.sendMailAttachmentToTeacher(details);
 
        return status;
    }

    @CrossOrigin
    @PostMapping(value = "/student")
    public String
    sendMailToStudent(@RequestBody CreateEmailDetailRequest details)
    {
        
        String status
            = emailService.sendMailToStudent(details);
 
        return status;
    }

    @CrossOrigin
    @PostMapping(value = "/student/attachment")
    public String
    sendMailAttachmentToStudent(@RequestBody CreateEmailDetailRequest details)
    {
        
        String status
            = emailService.sendMailAttachmentToStudent(details);
 
        return status;
    }

    @CrossOrigin
    @PostMapping(value = "/class/attachment/{id}")
    public String
    sendMailWithAttachmentByClass(@PathVariable Long id, @RequestBody CreateEmailDetailRequest details)
    {
        
        String status
            = emailService.sendMailWithAttachmentByClass(details, id);
 
        return status;
    }
 
    // Sending email with attachment
    @CrossOrigin
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
        @RequestBody EmailDetails details)
    {
        String status
            = emailService.sendMailWithAttachment(details);
 
        return status;
    }
}