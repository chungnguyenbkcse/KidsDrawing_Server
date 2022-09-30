
package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetClassHasRegisterJoinSemesterClassResponse {
    private UUID classes_id;
    private UUID user_register_join_semester_id;
    private Integer review_star;
    private String student_feedback;
    private String teacher_feedback;
}
