package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateUserGradeContestRequest {
    private Long teacher_id;
    private Long contest_id;
}
