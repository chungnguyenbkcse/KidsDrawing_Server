
package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetClassHasRegisterJoinSemesterClassResponse {
    private Long classes_id;
    private Long student_id;
    private String student_name;
    private Integer review_star;
    private String student_feedback;
    private String teacher_feedback;
}
