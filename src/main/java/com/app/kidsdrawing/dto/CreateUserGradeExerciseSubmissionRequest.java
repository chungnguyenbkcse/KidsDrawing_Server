package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateUserGradeExerciseSubmissionRequest {
    private Long teacher_id;
    private Long exercise_submission_id;
    private String feedback;
    private Float score;
}
