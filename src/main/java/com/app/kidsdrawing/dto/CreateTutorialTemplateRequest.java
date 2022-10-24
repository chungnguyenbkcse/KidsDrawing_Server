package com.app.kidsdrawing.dto;

import lombok.Data;                                   import java.util.UUID;

@Data
public class CreateTutorialTemplateRequest {
    private UUID section_template_id;
    private String name;
    private UUID creator_id;
}
