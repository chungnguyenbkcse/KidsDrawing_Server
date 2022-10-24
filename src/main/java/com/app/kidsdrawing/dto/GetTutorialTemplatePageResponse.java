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
public class GetTutorialTemplatePageResponse {
    private UUID id;
    private UUID tutorial_template_id;
    private String name;
    private String description;
    private Integer number; 
}
