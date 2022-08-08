package com.app.kidsdrawing.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.Data;

@Data
public class CreateHolidayRequest {
    private Set<LocalDate> time;
}
