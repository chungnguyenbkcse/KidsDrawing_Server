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
public class GetExerciseResponse {
    private Long id;
    private Long section_id;
    private Long level_id;
    private String name;
    private String description;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
