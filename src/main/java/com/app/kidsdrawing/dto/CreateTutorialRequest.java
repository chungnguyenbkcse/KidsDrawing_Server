package com.app.kidsdrawing.dto;

import lombok.Data;                                   import java.util.UUID;

@Data
public class CreateTutorialRequest {
    private UUID section_id;
    private UUID creator_id;
    private String name;
}
