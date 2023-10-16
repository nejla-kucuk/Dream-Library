package com.project.payload.mapper;


import com.project.entity.user.User;
import com.project.payload.request.user.UserRequest;
import com.project.payload.request.user.UserRequestWithoutUserRole;
import com.project.payload.response.user.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse mapUserToUserResponse(User user){

        if (user == null) {
            // User null ise gerekli işlemleri yapabilirsiniz.
            // Örneğin, bir hata mesajı döndürebilir veya bir exception fırlatabilirsiniz.
            throw new IllegalArgumentException("User cannot be null");
        }

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

    public User mapUserRequestToUser(UserRequestWithoutUserRole userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .address(userRequest.getAddress())
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
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
                .lastName(userRequest.getLastName())
                .address(userRequest.getAddress())
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .birthDate(userRequest.getBirthDate())
                .build();
    }


}
