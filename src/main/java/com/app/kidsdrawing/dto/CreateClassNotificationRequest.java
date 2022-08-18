package com.app.kidsdrawing.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateClassNotificationRequest {
    private Long notification_id;
    private Long class_id;
}
