package com.app.kidsdrawing.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class CreateAnonymousNotificationRequest {
    private UUID notification_id;
    private String user_full_name;
    private String email;
    private String phone;
}
