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
public class GetTeacherAskedAdjustTutorialResponse {
    private Long id;
    private Long reviewer_id;
    private Long teacher_id;
    private Long section_id;
    private String tutorial_name;
    private String teacher_name;
    private String status;
    private LocalDateTime time;
}
