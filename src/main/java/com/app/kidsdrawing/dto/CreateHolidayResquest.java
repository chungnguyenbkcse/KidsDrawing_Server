package com.app.kidsdrawing.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.Data;

@Data
public class CreateHolidayResquest {
    private Set<LocalDate> time;
}
