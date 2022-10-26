package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateTutorialPageRequest {
    private Long tutorial_id;
    private String name;
    private String description;
    private Integer number; 
}
