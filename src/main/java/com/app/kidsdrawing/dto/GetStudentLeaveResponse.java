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
public class GetStudentLeaveResponse {
    private Long section_id;
    private int section_number;
    private String section_name;
    private Long classes_id;
    private String class_name;
    private Long student_id;
    private String student_name;
    private LocalDateTime time_approved;
    private String description;
    private String status;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
