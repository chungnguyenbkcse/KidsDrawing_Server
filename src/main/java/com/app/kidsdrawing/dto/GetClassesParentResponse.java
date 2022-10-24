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
public class GetClassesParentResponse {
    private UUID id;
    private String link_url;
    private String course_name;
    private UUID course_id;
    private UUID semester_id;
    private UUID user_register_join_semester_id;
    private UUID semester_class_id;
    private String semester_class_name;
    private String semester_name;
    private String teacher_name;
    private UUID teacher_id;
    private int total_student;
    private int total_section;
    private UUID art_type_id;
    private UUID art_level_id;
    private UUID student_id;
    private String student_name;
    private UUID art_age_id;
    private String art_type_name;
    private String art_level_name;
    private String art_age_name;
    private UUID creator_id;
    private UUID user_register_teach_semester;
    private String security_code;
    private String name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
