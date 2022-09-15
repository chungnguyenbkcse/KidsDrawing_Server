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
public class GetContestTeacherResponse {
    private Long id;
    private String name;
    private String description;
    private Integer max_participant;
    private Integer total_register_contest;
    private Integer total_contest_submission;
    private Integer total_contest_submission_graded;
    private LocalDateTime registration_time;
    private String image_url;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private Boolean is_enabled;
    private String art_age_name;
    private String art_type_name;
}
