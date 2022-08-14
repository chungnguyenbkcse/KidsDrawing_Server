package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateScheduleRequest {
    private Long lesson_time;
    private Integer date_of_week; 
}
