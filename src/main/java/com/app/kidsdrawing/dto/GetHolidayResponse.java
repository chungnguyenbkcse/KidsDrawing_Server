package com.app.kidsdrawing.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetHolidayResponse {
    private UUID id;
    private LocalDate date;
    private UUID semester_id;
}
