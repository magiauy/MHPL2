package com.sgu.MHPL2.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Position is required")
    private Integer positionId;

    private String positionName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean status;
}