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
public class GetExerciseSubmissionResponse {
    private Long student_id;
    private Long exercise_id;
    private String student_name;
    private String exercise_name;
    private String exercise_description;
    private LocalDateTime exercise_deadline;
    private String image_url;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private Float score;
    private LocalDateTime time;
    private String feedback;
}
