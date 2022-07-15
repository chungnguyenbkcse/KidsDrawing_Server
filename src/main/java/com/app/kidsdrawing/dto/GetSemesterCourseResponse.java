package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSemesterCourseResponse {
    private Long id;
    private Long creation_id;
    private Long course_id;
    private Long schedule_id;
}
