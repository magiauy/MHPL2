package com.sgu.MHPL2.DTO;

import com.sgu.MHPL2.Model.ClassGroup;

import java.util.stream.Collectors;

public class ClassGroupMapper {

    public static ClassGroupDTO toDTO(ClassGroup classGroup) {
        if (classGroup == null) {
            return null;
        }

        ClassGroupDTO.ClassGroupDTOBuilder builder = ClassGroupDTO.builder()
                .id(classGroup.getId())
                .groupCode(classGroup.getGroupCode())
                .subjectId(classGroup.getSubjectId())
                .studentCount(classGroup.getStudentCount())
                .maxStudentCount(classGroup.getMaxStudentCount())
                .teacherId(classGroup.getTeacherId())
                .semester(classGroup.getSemester())
                .schoolYear(classGroup.getSchoolYear())
                .dayOfWeek(classGroup.getDayOfWeek())
                .startPeriod(classGroup.getStartPeriod())
                .endPeriod(classGroup.getEndPeriod())
                .location(classGroup.getLocation())
                .status(classGroup.getStatus())
                .createdAt(classGroup.getCreatedAt())
                .updatedAt(classGroup.getUpdatedAt());

        if (classGroup.getSubject() != null) {
            builder.subjectName(classGroup.getSubject().getName());
        }

        if (classGroup.getTeacher() != null) {
            builder.teacherName(classGroup.getTeacher().getName());
        }

        return builder.build();
    }

    public static ClassGroup toEntity(ClassGroupDTO dto) {
        if (dto == null) {
            return null;
        }

        return ClassGroup.builder()
                .id(dto.getId())
                .groupCode(dto.getGroupCode())
                .subjectId(dto.getSubjectId())
                .studentCount(dto.getStudentCount())
                .maxStudentCount(dto.getMaxStudentCount())
                .teacherId(dto.getTeacherId())
                .semester(dto.getSemester())
                .schoolYear(dto.getSchoolYear())
                .dayOfWeek(dto.getDayOfWeek())
                .startPeriod(dto.getStartPeriod())
                .endPeriod(dto.getEndPeriod())
                .location(dto.getLocation())
                .status(dto.getStatus())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
