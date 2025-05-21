package com.sgu.MHPL2.DTO;

import com.sgu.MHPL2.Model.ClassGroup;
import com.sgu.MHPL2.Model.TeacherAssignment;
import com.sgu.MHPL2.Repository.TeacherAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClassGroupMapper {

    @Autowired
    private TeacherAssignmentMapper teacherAssignmentMapper;

    @Autowired
    private TeacherAssignmentRepository teacherAssignmentRepository;

    public ClassGroupDTO toDTO(ClassGroup classGroup) {
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
                .trainingPlanId(classGroup.getTrainingPlanId())
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

        if (classGroup.getTrainingPlan() != null) {
            builder.trainingPlanName(classGroup.getTrainingPlan().getName());
        }

        // Map teacher assignments
        if (classGroup.getTeacherAssignments() != null) {
            List<TeacherAssignmentDTO> teacherAssignmentDTOs = classGroup.getTeacherAssignments().stream()
                    .map(teacherAssignmentMapper::toDTO)
                    .collect(Collectors.toList());
            builder.teacherAssignments(teacherAssignmentDTOs);
        }

        return builder.build();
    }

    public ClassGroup toEntity(ClassGroupDTO dto) {
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
                .trainingPlanId(dto.getTrainingPlanId())
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
