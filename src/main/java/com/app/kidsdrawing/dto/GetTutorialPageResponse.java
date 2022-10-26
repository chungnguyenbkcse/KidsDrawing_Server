package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTutorialPageResponse {
    private Long id;
    private Long tutorial_id;
    private String name;
    private String description;
    private Integer number; 
}
