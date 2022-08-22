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
    private Long class_id;
    private String name;
    private String description;
    private Integer number;
    private String recording;
    private String message;
    private Boolean teach_form;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
