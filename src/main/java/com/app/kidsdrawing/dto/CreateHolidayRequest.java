package com.app.kidsdrawing.dto;

import java.time.LocalDate;
import java.util.Set;
import lombok.Data;                                   

@Data
public class CreateHolidayRequest {
    private Long semester_id;
    private Set<LocalDate> time;
}
