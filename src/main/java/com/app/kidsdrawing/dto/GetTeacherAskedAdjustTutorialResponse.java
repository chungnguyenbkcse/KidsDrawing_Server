package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTeacherAskedAdjustTutorialResponse {
    private UUID id;
    private UUID reviewer_id;
    private UUID teacher_id;
    private UUID tutorial_id;
    private String tutorial_name;
    private String teacher_name;
    private String status;
    private LocalDateTime time;
}
