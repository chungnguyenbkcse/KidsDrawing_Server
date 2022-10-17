package com.app.kidsdrawing.dto;


import lombok.Data;                                   import java.util.UUID;

@Data
public class CreateUserReadNotificationRequest {
    private UUID user_id;
    private UUID notification_id;
    private Boolean is_read;
}
