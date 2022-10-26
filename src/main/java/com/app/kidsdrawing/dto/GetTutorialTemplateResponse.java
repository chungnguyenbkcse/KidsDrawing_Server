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
public class GetTutorialTemplateResponse {
    private Long id;
    private Long section_template_id;
    private String name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
