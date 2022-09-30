package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;                                   

@Data
public class CreateContestRequest {
    private String name;
    private String description;
    private Integer max_participant;
    private LocalDateTime registration_time;
    private String image_url;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private Boolean is_enabled;
    private UUID creator_id;
    private UUID art_age_id;
    private UUID art_type_id;
}
