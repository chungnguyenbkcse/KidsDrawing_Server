package com.app.kidsdrawing.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;

@Data
public class CreateSemesterRequest {
    private String name;
    private String description;
    private Integer number;
    private Set<LocalDate> time;
    private Integer year;
    private LocalDateTime start_time;
        
}
