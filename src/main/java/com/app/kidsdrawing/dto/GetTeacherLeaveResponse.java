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
    private String section_name;
    private Long class_id;
    private String class_name;
    private Long teacher_id;
    private String teacher_name;
    private Long reviewer_id;
    private Long substitute_teacher_id;
    private String substitute_teacher_name;
    private String description;
    private String status;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
