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
public class GetCourseResponse {
    private UUID id;
    private String name;
    private String description;
    private Integer num_of_section;
    private Float price;
    private String image_url;
    private Boolean is_enabled;
    private UUID creator_id;
    private UUID art_type_id;
    private UUID art_level_id;
    private UUID art_age_id;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
