package com.app.kidsdrawing.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetLessonTimeResponse {
    private UUID id;
    private LocalTime start_time;
    private LocalTime end_time;
}
