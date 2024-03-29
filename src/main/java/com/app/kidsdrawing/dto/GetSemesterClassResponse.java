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
public class GetSemesterClassResponse {
    private Long id;
    private String name;
    private Long semester_id;
    private String semester_name;
    private Long course_id;
    private String course_name;
    private Boolean is_new;
    private Integer total_register;
    private Integer max_participant;
    private LocalDateTime registration_time;
    private LocalDateTime registration_expiration_time;
}
