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
    private String student_name;
    private Long teacher_id;
    private String teacher_name;
    private String exercise_name;
    private LocalDateTime time_submit;
    private LocalDateTime deadline;
    private String image_url;
    private String description;
    private Long exercise_submission_id;
    private String feedback;
    private Float score;
    private LocalDateTime time;
}
