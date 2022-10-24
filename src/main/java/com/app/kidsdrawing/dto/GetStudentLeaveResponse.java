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
public class GetStudentLeaveResponse {
    private UUID id;
    private UUID section_id;
    private int section_number;
    private String section_name;
    private UUID classes_id;
    private String class_name;
    private UUID student_id;
    private String student_name;
    private UUID reviewer_id;
    private String description;
    private String status;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
