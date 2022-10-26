package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateUserRegisterJoinContestRequest {
    private Long student_id;
    private Long contest_id;
}
