package com.app.kidsdrawing.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetHolidayResponse {
    private Long id;
    private LocalDate date;
    private Long semester_id;
}
