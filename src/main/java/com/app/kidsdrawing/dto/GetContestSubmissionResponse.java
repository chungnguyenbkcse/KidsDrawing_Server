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
public class GetContestSubmissionResponse {
    private UUID id;
    private UUID contest_id;
    private String student_name;
    private String contest_name;
    private UUID student_id;
    private String image_url;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
