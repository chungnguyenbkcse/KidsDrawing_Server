package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetClassNotificationResponse {
    private Long notification_id;
    private Long classes_id;
    private String class_name;
    private String notification_name;
}
