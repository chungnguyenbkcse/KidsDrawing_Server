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
public class GetSectionStudentResponse {
    private Long id;
    private Long classes_id;
    private String class_name;
    private String name;
    private Integer number;
    private String teacher_name;
    private String recording;
    private String status;
    private int total_exercise_not_submit;
    private String message;
    private Boolean teach_form;
    private LocalDateTime time_approved;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
