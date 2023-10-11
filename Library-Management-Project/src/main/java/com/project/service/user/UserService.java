package com.project.service.user;


import com.project.entity.enums.RoleType;
import com.project.entity.user.User;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mapper.UserMapper;
import com.project.payload.message.ErrorMessages;
import com.project.payload.message.SuccessMessages;
import com.project.payload.request.user.UserRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.UserResponse;
import com.project.repository.user.UserRepository;
import com.project.service.helper.PageableHelper;
import com.project.service.utils.Util;
import com.project.service.validation.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UniquePropertyValidator uniquePropertyValidator;

    private final UserMapper userMapper;

    private final UserRoleService userRoleService;

    private final PasswordEncoder passwordEncoder;

    private final PageableHelper pageableHelper;

    private final Util util;

    // Register()********
    public ResponseMessage<UserResponse> registerUser(UserRequest userRequest) {

        // kayıtlı mı kontrolü
        uniquePropertyValidator.checkDuplicate(userRequest.getEmail(), userRequest.getPhone());

        User user = userMapper.mapUserRequestToUser(userRequest);
        user.setUserRole(userRoleService.getUserRole(RoleType.MEMBER));
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return ResponseMessage.<UserResponse>builder()
                .object(userMapper.mapUserToUserResponse((userRepository.save(user))))
                .message(SuccessMessages.USER_SAVE_MESSAGE)
                .build();

    }


    // getAllLoans()********
    public Page<UserResponse> getAllLoans(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);

        return userRepository.findAll(pageable).map(userMapper::mapUserToUserResponse);
    }


    // getAllUsers()********
    public Page<UserResponse> getAllUsers(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);

        return userRepository.findAll(pageable).map(userMapper::mapUserToUserResponse);
    }

    // getUserById() *******
    public ResponseMessage<UserResponse> getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE)));

        UserResponse userResponse = userMapper.mapUserToUserResponse(user);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_FOUND_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(userResponse)
                .build();
    }

    // UsersCreateForAdminOrEmployee() ****
    public ResponseMessage<UserResponse> usersCreateForAdminOrEmployee(UserRequest userRequest) {

        // kayıtlı mı kontrolü
        uniquePropertyValidator.checkDuplicate(userRequest.getEmail(), userRequest.getPhone());

        User user = userMapper.mapUserRequestToUser(userRequest);
        user.setUserRole(userRoleService.getUserRole(RoleType.MEMBER));
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return ResponseMessage.<UserResponse>builder()
                .object(userMapper.mapUserToUserResponse((userRepository.save(user))))
                .message(SuccessMessages.USER_SAVE_MESSAGE)
                .build();

    }


    // UpdateForAdminOrEmployee() ****
    public ResponseMessage<UserResponse> updateForAdminOrEmployee(UserRequest userRequest, Long userId) {

        User user = util.isUserExist(userId);

        util.isBuiltIn(user);

        uniquePropertyValidator.checkUniqueProperties(user,userRequest);

        User updatedUser = userMapper.mapUserRequestToUpdatedUser(userRequest, userId);

        updatedUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        updatedUser.setUserRole(user.getUserRole());

        User savedUser = userRepository.save(updatedUser);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_UPDATE_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToUserResponse(savedUser))
                .build();


    }


    // Not: DeleteUser() **********
    public String deleteUserById(Long userId, HttpServletRequest request) {

        util.isUserExist(userId);

        userRepository.deleteById(userId);

        return SuccessMessages.USER_DELETE_MESSAGE;
    }
}
