package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.Data;                                  

@Data
public class CreateExerciseRequest {
    private Long section_id;
    private String name;
    private LocalDateTime deadline;
    private String description;
}
