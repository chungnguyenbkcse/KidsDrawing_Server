package com.app.kidsdrawing.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import lombok.Data;                                   

@Data
public class CreateHolidayRequest {
    private UUID semester_id;
    private Set<LocalDate> time;
}
