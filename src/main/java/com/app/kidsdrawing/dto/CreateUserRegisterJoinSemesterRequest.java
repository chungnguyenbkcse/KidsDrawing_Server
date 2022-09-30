package com.app.kidsdrawing.dto;

import lombok.Data;                                   import java.util.UUID;

@Data
public class CreateUserRegisterJoinSemesterRequest {
    private UUID student_id;
    private UUID semester_classes_id;
    private UUID payer_id;
    private Float price;
    private String status;
}
