package com.app.kidsdrawing.dto;

import java.util.Set;

import lombok.Data;

@Data
public class CreateSemesterClassRequest {
    private Long creation_id;
    private Long course_id;
    private Set<Long> schedule_id;
    private Integer max_participant;
}
