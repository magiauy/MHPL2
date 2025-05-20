package com.sgu.MHPL2.DTO;

import com.sgu.MHPL2.Model.TrainingPlanClassGroup;

public class TrainingPlanClassGroupMapper {

    public static TrainingPlanClassGroupDTO toDTO(TrainingPlanClassGroup entity) {
        if (entity == null) {
            return null;
        }

        return TrainingPlanClassGroupDTO.builder()
                .id(entity.getId())
                .trainingPlanId(entity.getTrainingPlan().getId())
                .classGroupId(entity.getClassGroup().getId())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static TrainingPlanClassGroup toEntity(TrainingPlanClassGroupDTO dto) {
        if (dto == null) {
            return null;
        }

        TrainingPlanClassGroup entity = new TrainingPlanClassGroup();
        entity.setId(dto.getId());
        entity.setNotes(dto.getNotes());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());

        return entity;
    }
}
