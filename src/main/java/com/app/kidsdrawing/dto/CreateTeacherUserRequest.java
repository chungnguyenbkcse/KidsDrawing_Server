package com.app.kidsdrawing.dto;

import lombok.Data;                                 

@Data
public class CreateTeacherUserRequest {
    private String username;
    private String email;
}
