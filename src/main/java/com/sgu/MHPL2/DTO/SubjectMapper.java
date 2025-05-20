package com.sgu.MHPL2.DTO;

import com.sgu.MHPL2.Model.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

    public SubjectDTO toDTO(Subject subject) {
        if (subject == null) {
            return null;
        }

        return SubjectDTO.builder()
                .id(subject.getId())
                .name(subject.getName())
                .credits(subject.getCredits())
                .theoryLessons(subject.getTheoryLessons())
                .practiceLessons(subject.getPracticeLessons())
                .trainingLessons(subject.getTrainingLessons())
                .isRequired(subject.getIsRequired())
                .subjectTypeId(subject.getSubjectTypeId())
                .coefficient(subject.getCoefficient())
                .prerequisiteSubjectCode(subject.getPrerequisiteSubjectCode())
                .createdAt(subject.getCreatedAt())
                .updatedAt(subject.getUpdatedAt())
                .build();
    }

    public Subject toEntity(SubjectDTO dto) {
        if (dto == null) {
            return null;
        }

        Subject subject = new Subject();
        subject.setId(dto.getId());
        subject.setName(dto.getName());
        subject.setCredits(dto.getCredits());
        subject.setTheoryLessons(dto.getTheoryLessons());
        subject.setPracticeLessons(dto.getPracticeLessons());
        subject.setTrainingLessons(dto.getTrainingLessons());
        subject.setIsRequired(dto.getIsRequired());
        subject.setSubjectTypeId(dto.getSubjectTypeId());
        subject.setCoefficient(dto.getCoefficient());
        subject.setPrerequisiteSubjectCode(dto.getPrerequisiteSubjectCode());

        return subject;
    }
}
