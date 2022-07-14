package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetScheduleItemResponse {
    private Long id;
    private Long schedule_id;
    private Long lesson_time;
    private Integer date_of_week;
}
