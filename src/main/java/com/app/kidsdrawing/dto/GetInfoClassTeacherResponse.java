package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInfoClassTeacherResponse {
    private Long id;
    private Long semester_class_id;
    private Long teacher_id;
    private String security_code;
    private String name;
    private float review_star;
    private String link_url;
    private Long course_id;
    private String course_name;
    private Long semster_class_id;
    private int total_student;
    private int num_of_section;
    private String art_type_name;
    private String art_level_name;
    private String art_age_name;
    private String semester_name;
    private String schedule;
}
