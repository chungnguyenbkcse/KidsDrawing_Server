package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateTeacherLeaveRequest {
    private Long section_id;
    private Long class_id;
    private Long teacher_id;
    private Long substitute_teacher_id;
    private String description;
}
