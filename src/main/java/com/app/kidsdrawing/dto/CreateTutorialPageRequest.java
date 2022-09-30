package com.app.kidsdrawing.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateTutorialPageRequest {
    private UUID tutorial_id;
    private String name;
    private String description;
    private Integer number; 
}
