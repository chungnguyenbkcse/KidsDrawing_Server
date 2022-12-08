package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateUserRegisterJoinSemesterRequest {
    private Long student_id;
    private Long semester_classes_id;
    private String register_by_type;
    private Float price;
    private String status;
}
