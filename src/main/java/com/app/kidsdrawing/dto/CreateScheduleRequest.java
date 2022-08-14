package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateScheduleRequest {
    private Long lesson_time;
    private Long semester_class_id;
    private Integer date_of_week; 
}
