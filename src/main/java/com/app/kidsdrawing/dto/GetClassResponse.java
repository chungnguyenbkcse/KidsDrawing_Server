package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetClassResponse {
    private Long id;
    private Long creator_id;
    private Long registration_id;
    private String security_code;
    private String name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
