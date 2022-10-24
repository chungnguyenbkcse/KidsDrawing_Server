package com.app.kidsdrawing.dto;

import java.time.LocalTime;

import lombok.Data;                                   

@Data
public class CreateLessonTimeRequest {
    private LocalTime start_time;
    private LocalTime end_time;
}
