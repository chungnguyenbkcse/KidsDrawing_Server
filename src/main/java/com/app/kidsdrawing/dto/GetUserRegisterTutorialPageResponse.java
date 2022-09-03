package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserRegisterTutorialPageResponse {
    private Long id;
    private Long user_register_tutorial_id;
    private String name;
    private String description;
    private int number;
}
