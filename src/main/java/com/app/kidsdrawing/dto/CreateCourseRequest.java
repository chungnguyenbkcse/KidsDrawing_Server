package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateCourseRequest {
    private String name;
    private String description;
    private Integer num_of_section;
    private Float price;
    private String image_url;
    private Boolean is_enabled;
    
    private Long art_type_id;
    private Long art_level_id;
    private Long art_age_id;
}
