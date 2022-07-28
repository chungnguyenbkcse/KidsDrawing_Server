package com.app.kidsdrawing.dto;


import lombok.Data;

@Data
public class CreateReviewStudentLeaveRequest {
    private Long reviewer_id;
    private String status;
}
