package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateTeacherRegisterQualificationRequest {
    private Long teacher_id;
    private Long course_id;
    private String degree_photo_url;
    private String status;
}
