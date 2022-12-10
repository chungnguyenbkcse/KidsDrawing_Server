package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateTutorialRequest {
    private Long section_id;
    
    private String name;
}
