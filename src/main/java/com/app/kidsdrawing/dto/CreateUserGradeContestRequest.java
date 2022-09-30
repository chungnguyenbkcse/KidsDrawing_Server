package com.app.kidsdrawing.dto;

import lombok.Data;                                   import java.util.UUID;

@Data
public class CreateUserGradeContestRequest {
    private UUID teacher_id;
    private UUID contest_id;
}
