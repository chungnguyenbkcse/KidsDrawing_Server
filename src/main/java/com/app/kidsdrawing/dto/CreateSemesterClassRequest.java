package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateSemesterClassRequest {
    private Long creation_id;
    private Long course_id;
    private Integer max_participant;
}
