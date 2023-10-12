package com.project.payload.mapper;


import com.project.entity.user.User;
import com.project.payload.request.user.UserRequest;
import com.project.payload.response.business.LoanResponse;
import com.project.payload.response.user.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                .score(user.getScore())
                //.userRole(user.getUserRole().getRoleType().name())
                .build();
    }

    public User mapUserRequestToUser(UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .address(userRequest.getAddress())
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
    }

    public User mapUserRequestToUpdatedUser(UserRequest userRequest, Long userId) {

        return User.builder()
                .id(userId)
                .firstName(userRequest.getFirstName())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .address(userRequest.getAddress())
                .phone(userRequest.getPhone())
                .birthDate(userRequest.getBirthDate())
                .createDate(userRequest.getCreateDate())
                .build();
    }


}
