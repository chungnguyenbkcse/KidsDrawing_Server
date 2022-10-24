package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTutorialTemplateResponse {
    private UUID id;
    private UUID section_template_id;
    private String name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
