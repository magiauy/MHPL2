package com.sgu.MHPL2.DTO;

import org.springframework.stereotype.Component;
import com.sgu.MHPL2.Model.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final TeacherMapper teacherMapper;

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO.UserDTOBuilder builder = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .isAdmin(user.isAdmin());

        if (user.getTeacher() != null) {
            builder.teacherId(user.getTeacher().getId());
            builder.teacher(teacherMapper.toDTO(user.getTeacher()));
        }

        return builder.build();
    }

    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User.UserBuilder builder = User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .isAdmin(userDTO.isAdmin());

        return builder.build();
    }
}
