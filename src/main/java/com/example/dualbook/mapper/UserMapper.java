package com.example.dualbook.mapper;

import com.example.dualbook.dto.UserDTO;
import com.example.dualbook.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setFullName(user.getFullName());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setDisableDate(user.getDisableDate());

        return dto;
    }
}