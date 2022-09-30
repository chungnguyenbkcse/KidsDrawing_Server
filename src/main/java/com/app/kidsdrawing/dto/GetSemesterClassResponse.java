package com.app.kidsdrawing.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSemesterClassResponse {
    private UUID id;
    private String name;
    private UUID semester_id;
    private String semester_name;
    private UUID course_id;
    private String course_name;
    private Integer max_participant;
    private LocalDateTime registration_time;
}
