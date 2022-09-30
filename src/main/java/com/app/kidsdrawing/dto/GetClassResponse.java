package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetClassResponse {
    private UUID id;
    private UUID creator_id;
    private UUID user_register_teach_semester;
    private String security_code;
    private String name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
