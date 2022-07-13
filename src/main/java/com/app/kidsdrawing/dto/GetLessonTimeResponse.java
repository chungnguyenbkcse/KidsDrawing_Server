package com.app.kidsdrawing.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetLessonTimeResponse {
    private Long id;
    private LocalTime start_time;
    private LocalTime end_time;
}
