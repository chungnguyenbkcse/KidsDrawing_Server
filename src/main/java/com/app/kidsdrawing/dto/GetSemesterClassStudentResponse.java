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
public class GetSemesterClassStudentResponse {
    private UUID id;
    private UUID course_id;
    private UUID semster_class_id;
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
    private String semester_name;
    private LocalDateTime registration_deadline;
    private UUID semester_id;
    private String schedule;
}
