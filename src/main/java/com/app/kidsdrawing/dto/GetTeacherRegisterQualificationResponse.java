package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTeacherRegisterQualificationResponse {
    private Long id;
    private Long art_type_id;
    private Long art_age_id;
    private String degree_photo_url;
    private Boolean status;
}
