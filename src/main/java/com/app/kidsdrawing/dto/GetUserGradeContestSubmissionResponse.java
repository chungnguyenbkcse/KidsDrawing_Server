package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserGradeContestSubmissionResponse {
    private UUID teacher_id;
    private String teacher_name;
    private String student_name;
    private UUID student_id;
    private UUID contest_id;
    private String contest_name;
    private UUID contest_submission_id;
    private String feedback;
    private Float score;
    private LocalDateTime time;
}
