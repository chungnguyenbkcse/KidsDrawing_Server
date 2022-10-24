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
public class GetSectionTemplateResponse {
    private UUID id;
    private UUID creator_id;
    private UUID course_id;
    private String name;
    private Integer number;
    private Boolean teaching_form;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
