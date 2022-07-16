package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateReviewTeacherTeachSemesterRequest {
    private Long teacher_id;
    private Long reviewer_id;
    private Long semester_course_id;
    private Boolean status;
}
