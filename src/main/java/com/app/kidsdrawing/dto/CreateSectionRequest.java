package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateSectionRequest {
    private Long class_id;
    private String name;
    private String description;
    private Integer number;
    private String recording;
    private String message;
}
