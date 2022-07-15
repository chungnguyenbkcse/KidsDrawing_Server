package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateSemesterCourseRequest {
    private Long creation_id;
    private Long course_id;
    private Long schedule_id;
}
