package com.app.kidsdrawing.dto;

import java.util.Set;
import java.util.UUID;

import lombok.Data;                                   

@Data
public class CreateClassRequest {
    private UUID creator_id;
    private UUID user_register_teach_semester;
    private String security_code;
    private String name;
    private Set<UUID> user_register_join_semester;
}
