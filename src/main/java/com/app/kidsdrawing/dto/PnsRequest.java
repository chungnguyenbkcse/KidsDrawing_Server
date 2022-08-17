package com.app.kidsdrawing.dto;
import lombok.Data;

@Data
public class PnsRequest {
    private String fcmToken;
    private String title;
    private String body;  
}