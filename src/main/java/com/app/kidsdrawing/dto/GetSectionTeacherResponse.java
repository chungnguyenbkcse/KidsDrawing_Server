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
public class GetSectionTeacherResponse {
    private Long id;
    private Long classes_id;
    private String name;
    private Integer number;
    private String teacher_name;
    private String recording;
    private int total_exercise_submission;
    private int total_user_grade_exercise_submission;
    private String message;
    private String status;
    private Boolean teach_form;
    private LocalDateTime time_approved;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
