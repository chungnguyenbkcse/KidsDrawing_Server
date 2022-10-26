package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateUserRegisterJoinSemesterRequest {
    private Long student_id;
    private Long semester_classes_id;
    private Long payer_id;
    private Float price;
    private String status;
}
