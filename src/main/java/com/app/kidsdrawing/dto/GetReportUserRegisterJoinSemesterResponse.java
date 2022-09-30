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
public class GetReportUserRegisterJoinSemesterResponse {
    private UUID id;
    private UUID student_id;
    private String student_name;
    private UUID semester_classes_id;
    private String semester_class_name;
    private UUID payer_id;
    private String payer_name;
    private Float price;
    private LocalDateTime time;
}
