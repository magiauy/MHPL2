package com.sgu.MHPL2.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "subject")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @Column(name = "theory_lessons", nullable = false)
    private Integer theoryLessons;

    @Column(name = "practice_lessons", nullable = false)
    private Integer practiceLessons;

    @Column(name = "training_lessons", nullable = false)
    private Integer trainingLessons;

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired;

    @Column(name = "subject_type_id")
    private Integer subjectTypeId;

    @Column(nullable = false)
    private Double coefficient;

    @Column(name = "prerequisite_subject_code")
    private String prerequisiteSubjectCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
