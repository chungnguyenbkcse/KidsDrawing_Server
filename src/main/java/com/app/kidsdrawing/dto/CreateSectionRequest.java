package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateSectionRequest {
    private Long classes_id;
    private String name;
    private Integer number;
    private String status;
}
