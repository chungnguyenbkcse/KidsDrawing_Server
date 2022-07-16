package com.app.kidsdrawing.dto;


import lombok.Data;

@Data
public class CreateExerciseSubmissionRequest {
    private Long student_id;
    private Long exercise_id;
    private String image_url;
}
