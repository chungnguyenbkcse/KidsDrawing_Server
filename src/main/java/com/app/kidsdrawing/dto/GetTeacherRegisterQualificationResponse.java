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
public class GetTeacherRegisterQualificationResponse {
    private Long id;
    private Long teacher_id;
    private LocalDateTime time_approved;
    private Long course_id;
    private String degree_photo_url;
    private String status;
}
