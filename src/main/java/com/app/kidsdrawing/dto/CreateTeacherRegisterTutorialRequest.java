package com.app.kidsdrawing.dto;

import lombok.Data;

@Data
public class CreateTeacherRegisterTutorialRequest {
    private Long reviewer_id;
    private Long teacher_id;
    private Long tutorial_id;
    private Boolean status;
}
