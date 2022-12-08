package com.app.kidsdrawing.dto;

import java.util.Set;

import lombok.Data;                                   

@Data
public class CreateClassRequest {
    private Long teacher_id;
    private Long semester_class_id;
    private String security_code;
    private String name;
    private Set<Long> student_ids;
}
