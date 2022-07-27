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
public class GetTeacherRegisterTutorialResponse {
    private Long id;
    private Long reviewer_id;
    private Long teacher_id;
    private Long tutorial_id;
    private Long tutorial_template_id;
    private String status;
    private LocalDateTime time;
}
