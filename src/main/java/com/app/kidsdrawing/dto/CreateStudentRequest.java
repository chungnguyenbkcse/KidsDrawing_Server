package com.app.kidsdrawing.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import lombok.Data;                                   

@Data
public class CreateStudentRequest {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String profile_image_url;
    private String sex;
    private String phone;
    private String address;
    private UUID parent_id;
    private Set<String> roleNames;
}
