package com.app.kidsdrawing.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateTeacherRegisterQualificationRequest {
    private UUID teacher_id;
    private UUID course_id;
    private String degree_photo_url;
    private String status;
}
