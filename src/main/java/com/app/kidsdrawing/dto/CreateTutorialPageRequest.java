package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateTutorialPageRequest {
    private Long section_id;
    private String description;
    private Integer number; 
}
