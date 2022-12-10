package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateUserRegisterTutorialRequest {
    
    private Long section_id;
    private String name;
    private String status;
}
