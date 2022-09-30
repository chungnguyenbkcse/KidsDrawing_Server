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
public class GetUserRegisterTutorialPageResponse {
    private UUID id;
    private UUID user_register_tutorial_id;
    private String name;
    private String description;
    private int number;
}
