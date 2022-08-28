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
public class GetReportUserRegisterJoinSemesterResponse {
    private Long id;
    private Long student_id;
    private String student_name;
    private Long semester_class_id;
    private String semester_class_name;
    private Long payer_id;
    private String payer_name;
    private Float price;
    private LocalDateTime time;
}
