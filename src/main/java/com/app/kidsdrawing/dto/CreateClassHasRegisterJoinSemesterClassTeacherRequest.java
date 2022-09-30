package com.app.kidsdrawing.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class CreateClassHasRegisterJoinSemesterClassTeacherRequest {
    private UUID classes_id;
    private UUID user_register_join_semester_id;
    private String teacher_feedback;
}
