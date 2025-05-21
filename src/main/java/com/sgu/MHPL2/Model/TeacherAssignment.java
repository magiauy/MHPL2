package com.sgu.MHPL2.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "teacher_assignments",
       uniqueConstraints = @UniqueConstraint(
           columnNames = {"class_group_id", "teacher_id", "teaching_type"},
           name = "UK_teacher_assignment_class_teacher_type"
       ))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "class_group_id", nullable = false)
    private Integer classGroupId;

    @ManyToOne
    @JoinColumn(name = "class_group_id", insertable = false, updatable = false)
    private ClassGroup classGroup;

    @Column(name = "teacher_id", nullable = false)
    private Integer teacherId;

    @ManyToOne
    @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
    private Teacher teacher;

    @Column(name = "teaching_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TeachingType teachingType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    public enum TeachingType {
        THEORY,     // Lý thuyết
        PRACTICAL,  // Thực hành
        INTERNSHIP  // Thực tập
    }

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
