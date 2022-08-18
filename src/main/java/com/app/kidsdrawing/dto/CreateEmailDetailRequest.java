package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateEmailDetailRequest {
    private String msgBody;
    private String subject;
    private String attachment;
}
