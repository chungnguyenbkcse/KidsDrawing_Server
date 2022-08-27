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
public class GetTutorialResponse {
    private Long id;
    private Long section_id;
    private Long creator_id;
    private String creator_name;
    private String class_name;
    private Long class_id;
    private int section_number;
    private String name;
    private String status;
    private String description;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
