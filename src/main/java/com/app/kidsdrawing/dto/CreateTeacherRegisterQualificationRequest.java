package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateTeacherRegisterQualificationRequest {
    private Long teacher_id;
    private Long art_type_id;
    private Long art_age_id;
    private String degree_photo_url;
    private Boolean status;
}
