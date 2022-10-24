package com.app.kidsdrawing.dto;

import lombok.Data;                                   import java.util.UUID;

@Data
public class CreateUserRegisterJoinContestRequest {
    private UUID student_id;
    private UUID contest_id;
}
