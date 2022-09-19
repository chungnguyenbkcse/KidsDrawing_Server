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
public class GetUserAttendanceResponse {
    private Long id;
    private Long student_id;
    private Long section_id;
    private String status;
    private String student_name;
    private String section_name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
