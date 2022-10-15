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
public class GetCourseTeacherNewResponse {
    private UUID id;
    private UUID course_id;
    private UUID semster_class_id;
    private String name;
    private String course_name;
    private String description;
    private Integer max_participant;
    private Integer num_of_section;
    private Float price;
    private Boolean is_enabled;
    private UUID art_type_id;
    private UUID art_level_id;
    private UUID art_age_id;
    private String image_url;
    private String art_type_name;
    private String art_level_name;
    private String art_age_name;
    private String semester_name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private LocalDateTime registration_deadline;
    private String schedule;
    private int total;
    private int total_registed;
}
