package com.sgu.MHPL2.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {

    private Integer id;

    @NotEmpty(message = "Tên học phần không được để trống")
    @Size(max = 255, message = "Tên học phần không được vượt quá 255 ký tự")
    private String name;

    @NotNull(message = "Số tín chỉ không được để trống")
    @Min(value = 1, message = "Số tín chỉ phải lớn hơn 0")
    private Integer credits;

    @NotNull(message = "Số tiết lý thuyết không được để trống")
    @Min(value = 0, message = "Số tiết lý thuyết không được âm")
    private Integer theoryLessons;

    @NotNull(message = "Số tiết thực hành không được để trống")
    @Min(value = 0, message = "Số tiết thực hành không được âm")
    private Integer practiceLessons;

    @NotNull(message = "Số tiết thực tập không được để trống")
    @Min(value = 0, message = "Số tiết thực tập không được âm")
    private Integer trainingLessons;

    @NotNull(message = "Trạng thái bắt buộc không được để trống")
    private Boolean isRequired;

    private Integer subjectTypeId;

    @NotNull(message = "Hệ số không được để trống")
    @Min(value = 0, message = "Hệ số không được âm")
    private Double coefficient;

    private String prerequisiteSubjectCode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Thêm trường subjectTypeName để hiển thị tên loại học phần (nếu cần)
    private String subjectTypeName;
}
