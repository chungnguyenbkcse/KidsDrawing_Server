package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class CreateSemesterClassRequest {
    private String name;
    private Long semester_id;
    private List<Long> lesson_time_ids;
    private List<Integer> date_of_weeks;
    private Long course_id;
    private LocalDateTime registration_time;
    private LocalDateTime registration_expiration_time;
}
