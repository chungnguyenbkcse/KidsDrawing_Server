package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateChangePassowrdRequest {
    private String pre_password;
    private String new_password;
}
