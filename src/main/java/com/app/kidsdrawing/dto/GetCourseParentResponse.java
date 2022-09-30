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
public class GetCourseParentResponse {
    private UUID id;
    private String name;
    private String description;
    private Integer num_of_section;
    private Float price;
    private String image_url;
    private Boolean is_enabled;
    private UUID student_id;
    private String student_name;
    private UUID creator_id;
    private UUID art_type_id;
    private String art_type_name;
    private UUID art_level_id;
    private String art_level_name;
    private UUID art_age_id;
    private String art_age_name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
