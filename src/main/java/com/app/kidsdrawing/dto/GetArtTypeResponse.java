package com.app.kidsdrawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetArtTypeResponse {
    private UUID id;
    private String name;
    private String description;
}
