package com.app.kidsdrawing.dto;

import lombok.Data;                                   import java.util.UUID;

@Data
public class CreateTutorialTemplatePageRequest {
    private UUID tutorial_template_id;
    private String name;
    private String description;
    private Integer number; 
}
