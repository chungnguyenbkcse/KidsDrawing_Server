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
public class GetUserRegisterJoinSemesterResponse {
    private UUID id;
    private UUID student_id;
    private String student_name;
    private String semester_classes_name;
    private String link_url;
    private UUID semester_classes_id;
    private UUID payer_id;
    private Float price;
    private String status;
    private LocalDateTime time;
}