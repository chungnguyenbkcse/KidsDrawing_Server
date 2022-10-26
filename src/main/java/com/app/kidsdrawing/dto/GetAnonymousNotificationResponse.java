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
public class GetAnonymousNotificationResponse {
    private Long notification_id;
    private String notification_name;
    private String notification_body;
    private LocalDateTime time;
    private String email;
    private String user_full_name;
    private String phone;
}
