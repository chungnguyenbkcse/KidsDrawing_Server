package com.app.kidsdrawing.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class CreateClassNotificationRequest {
    private UUID notification_id;
    private UUID classes_id;
}
