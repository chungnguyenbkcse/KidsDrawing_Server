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
public class GetSemesterClassTeacherNewResponse {
    private Long id;
    private Long course_id;
    private String name;
    private String course_name;
    private String description;
    private Integer max_participant;
    private Integer num_of_section;
    private Float price;
    private int total_register;
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
    private LocalDateTime registration_expiration_time;
    private Long semester_id;
    private String schedule;
}
