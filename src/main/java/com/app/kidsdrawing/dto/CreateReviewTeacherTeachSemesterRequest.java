package com.app.kidsdrawing.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateReviewTeacherTeachSemesterRequest {
    private UUID reviewer_id;
    private Boolean status;
}
