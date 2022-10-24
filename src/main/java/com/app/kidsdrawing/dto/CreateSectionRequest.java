package com.app.kidsdrawing.dto;

import java.util.UUID;
import lombok.Data;                                   

@Data
public class CreateSectionRequest {
    private UUID classes_id;
    private String name;
    private Integer number;
    private String recording;
    private String message;
}
