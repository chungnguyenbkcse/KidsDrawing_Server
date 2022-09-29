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
public class GetClassesStudentResponse {
    private Long id;
    private String link_url;
    private String teacher_name;
    private Long teacher_id;
    private int total_student;
    private int total_section;
    private Long art_type_id;
    private Long art_level_id;
    private Long art_age_id;
    private String art_type_name;
    private String art_level_name;
    private String art_age_name;
    private Long creator_id;
    private Long user_register_join_semester_id;
    private Long user_register_teach_semester;
    private String security_code;
    private String name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
