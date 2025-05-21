package com.sgu.MHPL2.DTO;

import com.sgu.MHPL2.Model.TeacherAssignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherAssignmentDTO {
    private Integer id;
    private Integer classGroupId;
    private Integer teacherId;
    private String teacherName;
    private TeacherAssignment.TeachingType teachingType;
    private Boolean status;
}
