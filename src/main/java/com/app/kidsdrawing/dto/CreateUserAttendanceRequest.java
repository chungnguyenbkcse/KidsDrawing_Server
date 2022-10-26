package com.app.kidsdrawing.dto;

import lombok.Data;                                   

@Data
public class CreateUserAttendanceRequest {
    private Long section_id;
    private Long student_id;
    private Boolean status;
}
