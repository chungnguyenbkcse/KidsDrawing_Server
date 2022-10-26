package com.app.kidsdrawing.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInforRegisterSemesterResponse {
    private Set<Long> user_register_join_semester_ids;
    private Set<Long> user_register_teach_semesters_ids;
}
