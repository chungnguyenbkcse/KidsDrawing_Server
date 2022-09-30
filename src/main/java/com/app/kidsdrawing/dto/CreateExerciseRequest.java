package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;                                  

@Data
public class CreateExerciseRequest {
    private UUID section_id;
    private UUID level_id;
    private String name;
    private LocalDateTime deadline;
    private String description;
}
