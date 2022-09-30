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
public class GetTeacherRegisterQualificationResponse {
    private UUID id;
    private UUID teacher_id;
    private UUID reviewer_id;
    private UUID course_id;
    private String degree_photo_url;
    private String status;
}
