package com.app.kidsdrawing.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInforRegisterSemesterResponse {
    private Set<UUID> user_register_join_semester_ids;
    private Set<UUID> user_register_teach_semesters_ids;
}
