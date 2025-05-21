package com.sgu.MHPL2.DTO;

import com.sgu.MHPL2.Model.TeacherAssignment;
import org.springframework.stereotype.Component;

@Component
public class TeacherAssignmentMapper {

    public TeacherAssignmentDTO toDTO(TeacherAssignment assignment) {
        if (assignment == null) {
            return null;
        }

        return TeacherAssignmentDTO.builder()
                .id(assignment.getId())
                .classGroupId(assignment.getClassGroupId())
                .teacherId(assignment.getTeacherId())
                .teacherName(assignment.getTeacher() != null ? assignment.getTeacher().getName() : null)
                .teachingType(assignment.getTeachingType())
                .status(assignment.getStatus())
                .build();
    }

    public TeacherAssignment toEntity(TeacherAssignmentDTO dto) {
        if (dto == null) {
            return null;
        }

        return TeacherAssignment.builder()
                .id(dto.getId())
                .classGroupId(dto.getClassGroupId())
                .teacherId(dto.getTeacherId())
                .teachingType(dto.getTeachingType())
                .status(dto.getStatus())
                .build();
    }
}
