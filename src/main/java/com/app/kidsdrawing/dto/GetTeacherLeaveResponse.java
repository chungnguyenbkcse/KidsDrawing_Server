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
public class GetTeacherLeaveResponse {
    private Long id;
    private Long section_id;
    private int section_number;
    private String section_name;
    private Long classes_id;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String class_name;
    private Long substitute_teacher_id;
    private String substitute_teacher_name;
    private String description;
    private String status;
    private LocalDateTime time_approved;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
