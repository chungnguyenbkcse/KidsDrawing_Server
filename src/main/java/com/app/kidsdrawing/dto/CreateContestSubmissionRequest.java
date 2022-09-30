package com.app.kidsdrawing.dto;
import java.util.UUID;

import lombok.Data;                                   

@Data
public class CreateContestSubmissionRequest {
    private UUID student_id;
    private UUID contest_id;
    private String image_url;
}
