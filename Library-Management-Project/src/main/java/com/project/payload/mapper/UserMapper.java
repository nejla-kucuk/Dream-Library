package com.project.payload.mapper;

import com.project.entity.user.User;
import com.project.payload.response.user.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse mapUserToUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .address(user.getAddress())
                .userRole(user.getUserRole().getRoleType().name())
                .build();
    }

}
