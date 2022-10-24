package com.app.kidsdrawing.dto;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSemesterResponse {
    private UUID id;
    private String name;
    private String description;
    private Integer number;
    private Integer year;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private UUID creator_id;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
