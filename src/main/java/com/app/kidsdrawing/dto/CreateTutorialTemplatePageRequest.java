package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateTutorialTemplatePageRequest {
    private Long tutorial_template_id;
    private String name;
    private String description;
    private Integer number; 
}
