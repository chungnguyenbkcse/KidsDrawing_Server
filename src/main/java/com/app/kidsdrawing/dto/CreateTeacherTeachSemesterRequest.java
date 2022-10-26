package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateTeacherTeachSemesterRequest {
    private Long teacher_id;
    private Long semester_classes_id;
}
