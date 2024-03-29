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
public class GetContestParentResponse {
    private Long id;
    private String name;
    private String description;
    private Integer max_participant;
    private Integer total_register_contest;
    private LocalDateTime registration_time;
    private String image_url;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private Boolean is_enabled;
    
    private Long art_age_id;
    private Long art_type_id;
    private String art_age_name;
    private String art_type_name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private String student_name;
    private Long student_id;
}
