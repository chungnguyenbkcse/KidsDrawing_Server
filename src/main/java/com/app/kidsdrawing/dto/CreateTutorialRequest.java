package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateTutorialRequest {
    private Long section_id;
    private Long creator_id;
    private String name;
    private String description;
    private Boolean is_tutorial_nomal = false; 
}