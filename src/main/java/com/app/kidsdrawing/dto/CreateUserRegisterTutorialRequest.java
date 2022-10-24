package com.app.kidsdrawing.dto;

import lombok.Data;                                   import java.util.UUID;

@Data
public class CreateUserRegisterTutorialRequest {
    private UUID creator_id;
    private UUID section_id;
    private String name;
    private String status;
}
