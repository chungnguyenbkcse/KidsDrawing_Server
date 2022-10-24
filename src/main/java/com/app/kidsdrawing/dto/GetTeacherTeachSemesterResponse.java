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
public class GetTeacherTeachSemesterResponse {
    private UUID id;
    private UUID teacher_id;
    private UUID reviewer_id;
    private UUID semester_classes_id;
    private Boolean status;
    private LocalDateTime time;
}
