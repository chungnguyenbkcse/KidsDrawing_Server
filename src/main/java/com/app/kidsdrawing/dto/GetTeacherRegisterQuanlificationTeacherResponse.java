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
public class GetTeacherRegisterQuanlificationTeacherResponse {
    private UUID id;
    private UUID teacher_id;
    private String teacher_name;
    private UUID reviewer_id;
    private UUID course_id;
    private String course_name;
    private String art_age_name;
    private String art_type_name;
    private String art_level_name;
    private String degree_photo_url;
    private String status;
}
