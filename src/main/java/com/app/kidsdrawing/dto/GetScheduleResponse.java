package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetScheduleResponse {
    private Long id;
    private String lesson_time;
    private Long lesson_time_id;
    private Long semester_class_id;
    private Integer date_of_week; 
}
