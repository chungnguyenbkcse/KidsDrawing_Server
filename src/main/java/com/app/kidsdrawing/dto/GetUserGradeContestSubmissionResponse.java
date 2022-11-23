package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserGradeContestSubmissionResponse {
    private Long teacher_id;
    private String teacher_name;
    private String student_name;
    private Long student_id;
    private Long contest_id;
    private String url_conest_submission;
    private String art_type_name;
    private String art_age_name;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String contest_name;
    private Long contest_submission_id;
    private String feedback;
    private Float score;
    private LocalDateTime time;
}
