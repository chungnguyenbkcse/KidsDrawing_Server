package com.app.kidsdrawing.dto;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSemesterResponse {
    private Long id;
    private String name;
    private String description;
    private Integer number;
    private Boolean checked_genaration;
    private Integer year;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private Long creator_id;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
