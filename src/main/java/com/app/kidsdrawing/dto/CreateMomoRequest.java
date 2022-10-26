package com.app.kidsdrawing.dto;


import java.util.List;

import lombok.Data;                                   

@Data
public class CreateMomoRequest {
    private String partnerCode;
    private String orderId;
    private String requestId;
    private int resultCode;
    private String message;
    private Long responseTime;
    private String extraData;
    private String signature;
    private Long parent_id;
    private List<Long> student_ids;
}
