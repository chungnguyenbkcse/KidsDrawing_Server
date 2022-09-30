package com.app.kidsdrawing.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class CreateClassHasRegisterJoinSemesterClassStudentRequest {
    private UUID classes_id;
    private UUID user_register_join_semester_id;
    private String student_feedback;
    private int review_star;
}
