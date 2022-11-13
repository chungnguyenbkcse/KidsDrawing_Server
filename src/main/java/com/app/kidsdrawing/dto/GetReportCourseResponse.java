package com.app.kidsdrawing.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetReportCourseResponse {
    private Long id;
    private String name;
    private int total_register;
}
