package com.app.kidsdrawing.dto;

import lombok.Data; 

@Data
public class CreateArtTypeRequest {
    private String name;
    private String description;
}
