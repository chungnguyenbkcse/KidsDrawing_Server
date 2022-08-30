package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreateSemesterClassRequest {
    private String name;
    private Long semester_id;
    private Long course_id;
    private LocalDateTime registration_time;
}
