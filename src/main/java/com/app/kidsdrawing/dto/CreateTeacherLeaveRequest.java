package com.app.kidsdrawing.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateTeacherLeaveRequest {
    private UUID section_id;
    private UUID classes_id;
    private UUID teacher_id;
    private UUID substitute_teacher_id;
    private String description;
}
