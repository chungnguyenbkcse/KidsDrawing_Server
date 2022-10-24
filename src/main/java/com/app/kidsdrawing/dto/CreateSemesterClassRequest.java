package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateSemesterClassRequest {
    private String name;
    private UUID semester_id;
    private UUID course_id;
    private LocalDateTime registration_time;
}
