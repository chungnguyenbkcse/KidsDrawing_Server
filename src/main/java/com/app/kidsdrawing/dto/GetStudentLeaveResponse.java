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
    private Long id;
    private Long section_id;
    private Long class_id;
    private Long student_id;
    private Long reviewer_id;
    private String description;
    private Boolean status;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
