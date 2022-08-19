package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreateSemesterClassRequest {
    private String name;
    private Long creation_id;
    private Long course_id;
    private Integer max_participant;
    private LocalDateTime registration_time;
}
