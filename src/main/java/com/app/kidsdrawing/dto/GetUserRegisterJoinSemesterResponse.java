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
public class GetUserRegisterJoinSemesterResponse {
    private Long id;
    private Long student_id;
    private String student_name;
    private String semester_classes_name;
    private String link_url;
    private Long semester_classes_id;
    private Long payer_id;
    private Float price;
    private String status;
    private LocalDateTime time;
}