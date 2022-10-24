package com.app.kidsdrawing.dto;
import java.util.UUID;


import lombok.Data;                                   

@Data
public class CreateExerciseSubmissionRequest {
    private UUID student_id;
    private UUID exercise_id;
    private String image_url;
}
