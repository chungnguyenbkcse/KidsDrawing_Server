package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetClassNotificationResponse {
    private UUID notification_id;
    private UUID classes_id;
    private String class_name;
    private String notification_name;
}
