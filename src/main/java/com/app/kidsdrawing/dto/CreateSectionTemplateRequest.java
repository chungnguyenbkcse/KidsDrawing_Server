package com.app.kidsdrawing.dto;

import lombok.Data;

import java.util.UUID;
@Data
public class CreateSectionTemplateRequest {
    private UUID creator_id;
    private UUID course_id;
    private String name;
    private Integer number;
    private Boolean teaching_form;
}
