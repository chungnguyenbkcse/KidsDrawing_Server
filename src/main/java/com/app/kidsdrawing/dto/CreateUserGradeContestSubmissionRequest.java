package com.app.kidsdrawing.dto;

import lombok.Data;                                   import java.util.UUID;

@Data
public class CreateUserGradeContestSubmissionRequest {
    private UUID teacher_id;
    private UUID contest_submission_id;
    private String feedback;
    private Float score;
}
