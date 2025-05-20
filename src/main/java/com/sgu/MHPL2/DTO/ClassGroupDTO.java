package com.sgu.MHPL2.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassGroupDTO {

    private Integer id;

    @NotEmpty(message = "Mã nhóm không được để trống")
    @Size(max = 50, message = "Mã nhóm không được vượt quá 50 ký tự")
    private String groupCode;

    @NotNull(message = "ID học phần không được để trống")
    private Integer subjectId;

    private String subjectName;

    @NotNull(message = "Số lượng sinh viên không được để trống")
    @Min(value = 1, message = "Số lượng sinh viên phải lớn hơn 0")
    private Integer studentCount;

    private Integer maxStudentCount;

    private Integer teacherId;

    private String teacherName;

    @NotNull(message = "Học kỳ không được để trống")
    private Integer semester;

    @NotEmpty(message = "Năm học không được để trống")
    private String schoolYear;

    private String dayOfWeek;

    private Integer startPeriod;

    private Integer endPeriod;

    private String location;

    @NotEmpty(message = "Trạng thái không được để trống")
    private String status;

    private Set<TrainingPlanClassGroupDTO> trainingPlans = new HashSet<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

