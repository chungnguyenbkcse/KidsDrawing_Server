package com.app.kidsdrawing.dto;

import lombok.Data;                                   import java.util.UUID;

@Data
public class CreateUserRegisterTutorialPageRequest {
    private UUID user_register_tutorial_id;
    private String name;
    private String description;
    private int number;
}
