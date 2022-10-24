package com.app.kidsdrawing.dto;
import java.util.UUID;

import lombok.Data;                                   

@Data
public class CreateCourseRequest {
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
}
