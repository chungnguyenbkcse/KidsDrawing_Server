package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInfoClassTeacherResponse {
    private UUID id;
    private UUID user_register_teach_semester;
    private String security_code;
    private String name;
    private String link_url;
    private UUID course_id;
    private String course_name;
    private UUID semster_class_id;
    private int total_student;
    private int num_of_section;
    private String art_type_name;
    private String art_level_name;
    private String art_age_name;
    private String semester_name;
    private String schedule;
}
