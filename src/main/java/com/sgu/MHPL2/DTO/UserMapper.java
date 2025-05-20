package com.sgu.MHPL2.DTO;

import org.springframework.stereotype.Component;
import com.sgu.MHPL2.Model.User;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .isAdmin(user.isAdmin())
                .build();
    }

    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .isAdmin(userDTO.isAdmin())
                .build();
    }

}
