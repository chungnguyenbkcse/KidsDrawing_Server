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
public class GetSemesterClassParentResponse {
    private UUID id;
    private UUID course_id;
    private String name;
    private String course_name;
    private String description;
    private Integer max_participant;
    private Integer num_of_section;
    private Float price;
    private String status;
    private String image_url;
    private String art_type_name;
    private String art_level_name;
    private String art_age_name;
    private String art_type_id;
    private String art_level_id;
    private String art_age_id;
    private String semester_name;
    private LocalDateTime registration_deadline;
    private UUID semester_id;
    private String schedule;
    private Set<String> student_registered_name;
    private Set<UUID> student_registered_id;
}
