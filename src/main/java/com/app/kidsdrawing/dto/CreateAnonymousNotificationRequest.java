package com.app.kidsdrawing.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAnonymousNotificationRequest {
    private Long notification_id;
    private String user_full_name;
    private String email;
    private String phone;
}
