package com.sgu.MHPL2.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int id;
    @NotEmpty(message = "Email không được để trống")
    @Size(min = 5, max = 50)
    private String email;
    @NotEmpty(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 50)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$",
            message = "Mật khẩu phải chứa ít nhất 1 chữ cái viết hoa, 1 chữ cái viết thường và 1 số")
    private String password;

    private boolean isAdmin;

    private Integer teacherId;

    private TeacherDTO teacher;

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
