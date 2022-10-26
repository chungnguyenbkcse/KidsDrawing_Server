package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateStudentLeaveRequest {
    private Long section_id;
    private Long classes_id;
    private Long student_id;
    private String description;
}
