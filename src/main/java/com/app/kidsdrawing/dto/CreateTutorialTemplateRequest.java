package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateTutorialTemplateRequest {
    private Long section_template_id;
    private String name;
    private String description; 
}
