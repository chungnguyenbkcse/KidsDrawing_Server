package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTeacherLeaveResponse {
    private UUID id;
    private UUID section_id;
    private int section_number;
    private String section_name;
    private UUID classes_id;
    private String class_name;
    private UUID teacher_id;
    private String teacher_name;
    private UUID reviewer_id;
    private UUID substitute_teacher_id;
    private String substitute_teacher_name;
    private String description;
    private String status;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
