package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateTutorialTemplatePageRequest {
    private Long section_template_id;
    private String description;
    private Integer number; 
}
