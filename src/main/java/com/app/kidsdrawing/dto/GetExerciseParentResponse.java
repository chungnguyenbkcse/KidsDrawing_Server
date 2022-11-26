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
public class GetExerciseParentResponse {
    private Long id;
    private Long section_id;
    private Long level_id;
    private Long exercise_submission_id;
    private String level_name;
    private String teacher_name;
    private String section_name;
    private String name;
    private String student_name;
    private Long student_id;
    private String description;
    private LocalDateTime deadline;
    private LocalDateTime time_submit;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
