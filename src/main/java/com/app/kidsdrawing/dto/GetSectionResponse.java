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
public class GetSectionResponse {
    private Long id;
    private Long classes_id;
    private String name;
    private Integer number;
    private String status;
    private String teacher_name;
    private String recording;
    private String message;
    private Boolean teach_form;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
