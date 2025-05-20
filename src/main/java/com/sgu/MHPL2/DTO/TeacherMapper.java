package com.sgu.MHPL2.DTO;

import com.sgu.MHPL2.Model.Position;
import com.sgu.MHPL2.Model.Teacher;
import com.sgu.MHPL2.Repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherMapper {

    private final PositionRepository positionRepository;

    public Teacher toEntity(TeacherDTO dto) {
        Teacher entity = new Teacher();
        entity.setId(dto.getId());
        entity.setName(dto.getName());

        // Handle the position relationship
        if (dto.getPositionId() != null) {
            Position position = positionRepository.findById(dto.getPositionId())
                .orElseThrow(() -> new IllegalArgumentException("Position not found with id: " + dto.getPositionId()));
            entity.setPosition(position);
        }

        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : true);
        return entity;
    }

    public TeacherDTO toDTO(Teacher entity) {
        TeacherDTO dto = new TeacherDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        if (entity.getPosition() != null) {
            dto.setPositionId(entity.getPosition().getId());
            dto.setPositionName(entity.getPosition().getName());
        }

        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}