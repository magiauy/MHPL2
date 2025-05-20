package com.sgu.MHPL2.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPlanClassGroupDTO {

    private Integer id;
    private Integer trainingPlanId;
    private Integer classGroupId;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
