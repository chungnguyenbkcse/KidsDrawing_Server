package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateSectionTemplateRequest {
    private Long creator_id;
    private Long course_id;
    private String name;
    private Integer number;
    private Boolean teaching_form;
}
