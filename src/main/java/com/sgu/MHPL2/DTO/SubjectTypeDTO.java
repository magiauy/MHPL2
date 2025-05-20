package com.sgu.MHPL2.DTO;

import jakarta.validation.constraints.NotEmpty;
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
public class SubjectTypeDTO {

    private Integer id;

    @NotEmpty(message = "Tên loại học phần không được để trống")
    @Size(max = 255, message = "Tên loại học phần không được vượt quá 255 ký tự")
    private String name;

    @NotEmpty(message = "Mô tả không được để trống")
    private String description;

    private Integer parentId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
