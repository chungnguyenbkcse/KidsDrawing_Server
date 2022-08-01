package com.app.kidsdrawing.dto;

import java.util.Set;

import lombok.Data;

@Data
public class CreateClassRequest {
    private Long creator_id;
    private Long registration_id;
    private String security_code;
    private String name;
    private Set<Long> user_register_join_semester;
}
