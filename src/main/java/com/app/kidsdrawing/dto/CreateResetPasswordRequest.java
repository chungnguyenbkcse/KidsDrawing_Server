package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateResetPasswordRequest {
    private String oldPassword;

    private  String token;

    private String newPassword;
}
