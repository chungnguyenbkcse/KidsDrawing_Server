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
public class GetCourseResponse {
    private Long id;
    private String name;
    private String description;
    private Integer max_participant;
    private Integer num_of_section;
    private Float price;
    private String image_url;
    private Boolean is_enabled;
    private Long creator_id;
    private Long art_type_id;
    private Long art_level_id;
    private Long art_age_id;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}