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
public class GetContestSubmissionResponse {
    private Long id;
    private Long contest_id;
    private String student_name;
    private String contest_name;
    private Long student_id;
    private String image_url;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
