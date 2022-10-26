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
public class GetSectionTemplateResponse {
    private Long id;
    private Long creator_id;
    private Long course_id;
    private String name;
    private Integer number;
    private Boolean teaching_form;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
