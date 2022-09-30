package com.app.kidsdrawing.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateStudentLeaveRequest {
    private UUID section_id;
    private UUID classes_id;
    private UUID student_id;
    private String description;
}
