package com.app.kidsdrawing.dto;


import lombok.Data;                                   

@Data
public class CreateUserReadNotificationRequest {
    private Long user_id;
    private Long notification_id;
    private Boolean is_read;
}
