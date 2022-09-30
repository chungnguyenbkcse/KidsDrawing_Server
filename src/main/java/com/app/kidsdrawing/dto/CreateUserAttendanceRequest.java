package com.app.kidsdrawing.dto;

import lombok.Data;                                   import java.util.UUID;

@Data
public class CreateUserAttendanceRequest {
    private UUID section_id;
    private UUID student_id;
    private Boolean status;
}
