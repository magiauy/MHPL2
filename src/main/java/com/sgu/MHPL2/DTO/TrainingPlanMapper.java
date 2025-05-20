package com.sgu.MHPL2.DTO;

import com.sgu.MHPL2.Model.TrainingPlan;

import java.util.stream.Collectors;

public class TrainingPlanMapper {

    public static TrainingPlanDTO toDTO(TrainingPlan trainingPlan) {
        if (trainingPlan == null) {
            return null;
        }

        TrainingPlanDTO.TrainingPlanDTOBuilder builder = TrainingPlanDTO.builder()
                .id(trainingPlan.getId())
                .name(trainingPlan.getName())
                .academicYear(trainingPlan.getAcademicYear())
                .semester(trainingPlan.getSemester())
                .startDate(trainingPlan.getStartDate())
                .endDate(trainingPlan.getEndDate())
                .isActive(trainingPlan.getIsActive())
                .department(trainingPlan.getDepartment())
                .description(trainingPlan.getDescription())
                .createdBy(trainingPlan.getCreatedBy())
                .approvedBy(trainingPlan.getApprovedBy())
                .approvalDate(trainingPlan.getApprovalDate())
                .status(trainingPlan.getStatus())
                .createdAt(trainingPlan.getCreatedAt())
                .updatedAt(trainingPlan.getUpdatedAt());

        // Chuyển đổi danh sách các classGroups
        if (trainingPlan.getClassGroups() != null && !trainingPlan.getClassGroups().isEmpty()) {
            builder.classGroups(trainingPlan.getClassGroups().stream()
                    .map(TrainingPlanClassGroupMapper::toDTO)
                    .collect(Collectors.toSet()));
        }

        return builder.build();
    }

    public static TrainingPlan toEntity(TrainingPlanDTO dto) {
        if (dto == null) {
            return null;
        }

        TrainingPlan.TrainingPlanBuilder builder = TrainingPlan.builder()
                .id(dto.getId())
                .name(dto.getName())
                .academicYear(dto.getAcademicYear())
                .semester(dto.getSemester())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .isActive(dto.getIsActive())
                .department(dto.getDepartment())
                .description(dto.getDescription())
                .createdBy(dto.getCreatedBy())
                .approvedBy(dto.getApprovedBy())
                .approvalDate(dto.getApprovalDate())
                .status(dto.getStatus())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt());

        return builder.build();
    }
}
