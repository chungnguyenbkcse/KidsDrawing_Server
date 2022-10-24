package com.app.kidsdrawing.dto;


import java.util.List;
import java.util.UUID;

import lombok.Data;                                   

@Data
public class CreateMomoRequest {
    private String partnerCode;
    private String orderId;
    private String requestId;
    private int resultCode;
    private String message;
    private UUID responseTime;
    private String extraData;
    private String signature;
    private UUID parent_id;
    private List<UUID> student_ids;
}
