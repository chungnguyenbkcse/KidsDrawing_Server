package com.app.kidsdrawing.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateScheduleRequest {
    private UUID lesson_time;
    private UUID semester_classes_id;
    private Integer date_of_week; 
}
