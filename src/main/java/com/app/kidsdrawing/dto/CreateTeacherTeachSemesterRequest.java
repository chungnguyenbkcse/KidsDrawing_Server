package com.app.kidsdrawing.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateTeacherTeachSemesterRequest {
    private UUID teacher_id;
    private UUID semester_classes_id;
}
