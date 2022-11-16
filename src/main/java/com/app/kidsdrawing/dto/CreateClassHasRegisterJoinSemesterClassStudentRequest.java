package com.app.kidsdrawing.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateClassHasRegisterJoinSemesterClassStudentRequest {
    private Long classes_id;
    private Long student_id;
    private String student_feedback;
    private int review_star;
}
