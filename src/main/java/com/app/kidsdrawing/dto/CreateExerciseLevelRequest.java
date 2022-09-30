package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateExerciseLevelRequest {
    private String name;
    private String description;
    private Float weight;
}
