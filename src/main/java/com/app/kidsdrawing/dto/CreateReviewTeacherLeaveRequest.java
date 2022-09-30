package com.app.kidsdrawing.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateReviewTeacherLeaveRequest {
    private UUID reviewer_id;
    private String status;
}
