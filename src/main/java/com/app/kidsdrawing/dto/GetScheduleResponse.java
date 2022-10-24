package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetScheduleResponse {
    private UUID id;
    private String lesson_time;
    private UUID lesson_time_id;
    private UUID semester_classes_id;
    private Integer date_of_week; 
}
