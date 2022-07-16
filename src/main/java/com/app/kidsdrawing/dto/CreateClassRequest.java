package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateClassRequest {
    private Long creator_id;
    private Long registration_id;
    private String security_code;
    private String name;
}
