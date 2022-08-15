package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreateSemesterRequest {
    private String name;
    private String description;
    private Integer number;
    private Integer year;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private Long creator_id;    
}
