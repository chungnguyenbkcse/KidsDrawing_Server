package com.app.kidsdrawing.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private String status;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String profile_image_url;
    private String sex;
    private String phone;
    private String address;
    private Long parents;
    private String parent_name;
    private List<Long> student_ids;
    private List<String> student_names;
    private LocalDateTime createTime;
}
