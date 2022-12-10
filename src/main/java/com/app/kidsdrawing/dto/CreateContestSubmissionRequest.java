package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateContestSubmissionRequest {
    private Long student_id;
    private Long contest_id;
    private String image_url;
    private String feedback;
    private Float score;
    private Long teacher_id;
}
