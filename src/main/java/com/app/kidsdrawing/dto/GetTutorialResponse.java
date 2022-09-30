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
public class GetTutorialResponse {
    private UUID id;
    private UUID section_id;
    private UUID creator_id;
    private String creator_name;
    private String class_name;
    private UUID classes_id;
    private int section_number;
    private String name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
