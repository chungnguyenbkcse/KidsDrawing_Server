package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTeacherRegisterQuanlificationTeacherResponse {
    private Long id;
    private Long teacher_id;
    private String teacher_name;
    private Long reviewer_id;
    private Long course_id;
    private String course_name;
    private String art_age_name;
    private String art_type_name;
    private String art_level_name;
    private String degree_photo_url;
    private String status;
}
