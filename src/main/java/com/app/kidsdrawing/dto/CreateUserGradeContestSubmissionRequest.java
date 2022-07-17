package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateUserGradeContestSubmissionRequest {
    private Long student_id;
    private Long contest_submission_id;
    private String feedback;
    private Float score;
}
