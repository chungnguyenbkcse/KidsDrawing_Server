package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserGradeContestResponse {
    private UUID id;
    private UUID teacher_id;
    private String teacher_name;
    private UUID contest_id;
    private String contest_name;
}
