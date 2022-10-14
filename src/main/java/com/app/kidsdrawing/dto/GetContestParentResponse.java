package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetContestParentResponse {
    private UUID id;
    private String name;
    private String description;
    private String student_name;
    private UUID student_id;
    private Integer max_participant;
    private Integer total_register_contest;
    private Integer total_contest_submission;
    private Integer total_contest_submission_graded;
    private LocalDateTime registration_time;
    private String image_url;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private Boolean is_enabled;
    private UUID creator_id;
    private UUID art_age_id;
    private UUID art_type_id;
    private String art_age_name;
    private String art_type_name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private Set<String> student_registered_name;
    private Set<UUID> student_registered_id;
}
