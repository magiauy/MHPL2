package com.sgu.MHPL2.DTO;

import com.sgu.MHPL2.Model.SubjectType;

public class SubjectTypeMapper {

    public static SubjectTypeDTO toDTO(SubjectType subjectType) {
        if (subjectType == null) {
            return null;
        }

        return SubjectTypeDTO.builder()
                .id(subjectType.getId())
                .name(subjectType.getName())
                .description(subjectType.getDescription())
                .createdAt(subjectType.getCreatedAt())
                .updatedAt(subjectType.getUpdatedAt())
                .build();
    }

    public static SubjectType toEntity(SubjectTypeDTO subjectTypeDTO) {
        if (subjectTypeDTO == null) {
            return null;
        }

        return SubjectType.builder()
                .id(subjectTypeDTO.getId())
                .name(subjectTypeDTO.getName())
                .description(subjectTypeDTO.getDescription())
                .createdAt(subjectTypeDTO.getCreatedAt())
                .updatedAt(subjectTypeDTO.getUpdatedAt())
                .build();
    }
}
