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
public class GetUserAttendanceResponse {
    private UUID id;
    private UUID student_id;
    private UUID section_id;
    private int section_number;
    private UUID course_id;
    private String course_name;
    private String email;
    private Boolean status;
    private String student_name;
    private String section_name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
