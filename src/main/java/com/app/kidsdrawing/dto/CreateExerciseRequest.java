package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateExerciseRequest {
    private Long section_id;
    private Long level_id;
    private String name;
    private String description;
}
