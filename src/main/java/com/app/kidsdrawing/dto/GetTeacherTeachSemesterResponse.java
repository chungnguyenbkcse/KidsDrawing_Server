package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTeacherTeachSemesterResponse {
    private Long id;
    private Long teacher_id;
    private Long reviewer_id;
    private Long semester_course_id;
    private Boolean status;
    private LocalDateTime time;
}
