package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserGradeExerciseSubmissionResponse {
    private Long student_id;
    private Long exercise_submission_id;
    private String feedback;
    private Float score;
    private LocalDateTime time;
}
