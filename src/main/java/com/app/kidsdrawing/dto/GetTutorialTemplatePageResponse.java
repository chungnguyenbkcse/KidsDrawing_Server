package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTutorialTemplatePageResponse {
    private Long id;
    private Long section_template_id;
    private String description;
    private Integer number; 
}
