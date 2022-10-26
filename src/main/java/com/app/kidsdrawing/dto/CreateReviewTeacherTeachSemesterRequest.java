package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateReviewTeacherTeachSemesterRequest {
    private Long reviewer_id;
    private Boolean status;
}
