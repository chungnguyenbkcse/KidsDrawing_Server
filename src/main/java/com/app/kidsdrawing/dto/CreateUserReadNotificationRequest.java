package com.app.kidsdrawing.dto;


import lombok.Data;                                   import java.util.UUID;

@Data
public class CreateUserReadNotificationRequest {
    private String user_id;
    private UUID notification_id;
    private Boolean is_read;
}
