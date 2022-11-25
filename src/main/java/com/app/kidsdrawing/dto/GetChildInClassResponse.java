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
public class GetChildInClassResponse {
    private Long student_id;
    private String student_name;
    private LocalDate dateOfBirth;
    private String sex;
    private String phone;
    private String address; 
}
