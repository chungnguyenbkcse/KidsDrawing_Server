package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateScheduleRequest {
    private Long creator_id;
    private String name; 
}
