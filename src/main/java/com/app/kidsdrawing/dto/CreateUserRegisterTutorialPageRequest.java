package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateUserRegisterTutorialPageRequest {
    private Long user_register_section_id;
    private String name;
    private String description;
    private int number;
}
