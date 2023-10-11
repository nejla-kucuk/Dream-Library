package com.project.service.user;


import com.project.entity.enums.RoleType;
import com.project.entity.user.User;
import com.project.payload.mapper.UserMapper;
import com.project.payload.message.SuccessMessages;
import com.project.payload.request.user.UserRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.UserResponse;
import com.project.repository.user.UserRepository;
import com.project.service.validation.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UniquePropertyValidator uniquePropertyValidator;

    private final UserMapper userMapper;

    private final UserRoleService userRoleService;

    private final PasswordEncoder passwordEncoder;

    // Register()********
    public ResponseMessage<UserResponse> registerUser(UserRequest userRequest) {

        // kayıtlı mı kontrolü
        uniquePropertyValidator.checkDuplicate(userRequest.getEmail(), userRequest.getPhone());

        User user = userMapper.mapUserRequestToUser(userRequest);
        user.setUserRole(userRoleService.getUserRole(RoleType.MEMBER));
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return ResponseMessage.<UserResponse>builder()
                .object(userMapper.mapUserToUserResponse((userRepository.save(user))))
                .message(SuccessMessages.USER_SAVE)
                .build();

    }
}
