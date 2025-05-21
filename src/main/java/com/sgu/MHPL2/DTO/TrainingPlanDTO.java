package com.sgu.MHPL2.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPlanDTO {

    private Integer id;

    @NotEmpty(message = "Tên kế hoạch đào tạo không được để trống")
    @Size(max = 255, message = "Tên kế hoạch đào tạo không được vượt quá 255 ký tự")
    private String name;

    @NotEmpty(message = "Năm học không được để trống")
    private String academicYear;

    @NotNull(message = "Học kỳ không được để trống")
    private Integer semester;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate startDate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDate endDate;

    @NotNull(message = "Trạng thái kích hoạt không được để trống")
    private Boolean isActive;

    @NotEmpty(message = "Khoa/Ban không được để trống")
    private String department;

    private String description;

    private Integer createdBy;

    private Integer approvedBy;

    private LocalDate approvalDate;

    @NotEmpty(message = "Trạng thái không được để trống")
    private String status;

    private List<ClassGroupDTO> classGroups = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
