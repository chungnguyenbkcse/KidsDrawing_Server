package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserGradeContestResponse {
    private Long id;
    private Long teacher_id;
    private String teacher_name;
    private Long contest_id;
    private String contest_name;
}
