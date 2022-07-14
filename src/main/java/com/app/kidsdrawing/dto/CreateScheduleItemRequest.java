package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateScheduleItemRequest {
    private Long schedule_id;
    private Long lesson_time;
    private Integer date_of_week;
}
