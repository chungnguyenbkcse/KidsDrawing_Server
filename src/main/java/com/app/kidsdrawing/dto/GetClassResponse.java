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
public class GetClassResponse {
    private Long id;
    private Long teacher_id;
    private String teacher_name;
    private Integer total_student;
    private Long course_id;
    private String course_name;
    private Long semester_id;
    private String semester_name;
    private Long semester_class_id;
    private String security_code;
    private String name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
