package com.app.kidsdrawing.dto;

import lombok.Data;                                   import java.util.UUID;

@Data
public class CreateUserGradeExerciseSubmissionRequest {
    private UUID teacher_id;
    private UUID exercise_submission_id;
    private String feedback;
    private Float score;
}
