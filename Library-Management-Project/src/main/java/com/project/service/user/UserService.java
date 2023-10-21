package com.project.service.user;


import com.project.entity.enums.RoleType;
import com.project.entity.user.User;
import com.project.entity.user.UserRole;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mapper.UserMapper;
import com.project.payload.message.ErrorMessages;
import com.project.payload.message.SuccessMessages;
import com.project.payload.request.user.UserRequest;
import com.project.payload.request.user.UserRequestWithoutUserRole;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.UserResponse;
import com.project.repository.user.UserRepository;
import com.project.service.helper.PageableHelper;
import com.project.service.utils.Util;
import com.project.service.validation.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    // saveAdmin() ****
    public String saveAdmin (UserRequest userRequest) {

        Set<UserRole> userRole = new HashSet<>();

        UserRole admin = userRoleService.getUserRole(RoleType.ADMIN);

        userRole.add(admin);

        User user = userMapper.mapUserRequestToUser(userRequest);

        user.setBuiltIn(Boolean.TRUE);

        user.setUserRole(userRole);

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        user.setCreateDate(LocalDateTime.now());

        userRepository.save(user);

        return SuccessMessages.USER_SAVE_MESSAGE;
    }

    // Register()********
    public ResponseMessage<UserResponse> registerUser(UserRequestWithoutUserRole userRequest) {

        uniquePropertyValidator.checkDuplicate(userRequest.getEmail(), userRequest.getPhone());

        User user = userMapper.mapUserRequestToUser(userRequest);

        Set<UserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(userRoleService.getUserRole(RoleType.MEMBER));

        user.setUserRole(userRoleSet);

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        user.setBuiltIn(Boolean.FALSE);

        user.setCreateDate(LocalDateTime.now());

        return ResponseMessage.<UserResponse>builder()
                .object(userMapper.mapUserToUserResponse((userRepository.save(user))))
                .message(SuccessMessages.USER_SAVE_MESSAGE)
                .build();

    }

    // User()********
    public ResponseMessage<UserResponse> getUser(HttpServletRequest request) {

        User user = util.getAttributeUser(request);


        UserResponse userResponse = userMapper.mapUserToUserResponse(user);

        return ResponseMessage.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(userResponse)
                .build();
    }


    // getAllLoans()********

 //  public Page<LoanResponse> getAllLoans(HttpServletRequest request, int page, int size, String sort, String type) {

 //      User user = util.getAttributeUser(request);

 //      Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

 //      Page<User> loanPage = userRepository.findAll(pageable);

 //     // Page<LoanResponse> loanResponsePage = userMapper.mapUserToLoanResponse(loanPage);

 //      return loanResponsePage;

 //  }


    // getAllUsers()********
    public Page<List<UserResponse>> getAllUser(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page,size);

        List<UserResponse> userResponseList = userRepository.findAll()
                .stream()
                .map(userMapper::mapUserToUserResponse)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();

        int end = Math.min((start + pageable.getPageSize()), userResponseList.size());

        Page<UserResponse> pageResponse = new PageImpl<>(userResponseList.subList(start, end), pageable, userResponseList.size());

        List<UserResponse> content = pageResponse.getContent();

        return new PageImpl<>(List.of(content),pageable,pageResponse.getTotalElements());
    }




    // getUserById() *******
    public ResponseMessage<UserResponse> getUserById(Long userId) {

        User user = util.isUserExist(userId);

        UserResponse userResponse = userMapper.mapUserToUserResponse(user);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_FOUND_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(userResponse)
                .build();
    }

    // UsersCreateForAdminOrEmployee() ****
    public ResponseMessage<UserResponse> usersCreateForAdminOrEmployee(HttpServletRequest request,
                                                                       UserRequest userRequest) {

        User user = util.getAttributeUser(request);
        Set<UserRole> roles = user.getUserRole();

        Set<String> requestRoles = userRequest.getUserRole();
        Set<UserRole> userRole = new HashSet<>();

        UserRole admin = userRoleService.getUserRole(RoleType.ADMIN);
        UserRole employee = userRoleService.getUserRole(RoleType.EMPLOYEE);
        UserRole member = userRoleService.getUserRole(RoleType.MEMBER);

        uniquePropertyValidator.checkDuplicate(userRequest.getEmail(), userRequest.getPhone());


        if (roles.stream()
                .anyMatch(t -> t.equals(admin))) {

            for (String role : requestRoles) {

                if (admin.getRoleName().equalsIgnoreCase(role)) {

                    userRole.add(admin);


                } else if (employee.getRoleName().equalsIgnoreCase(role)) {

                    userRole.add(employee);


                } else if (member.getRoleName().equalsIgnoreCase(role)) {

                    userRole.add(member);

                } else {

                    throw new ResourceNotFoundException(ErrorMessages.NOT_ROLE_TYPE_VALID_MESSAGE);
                }

            }


        } else if (user.getUserRole().stream().anyMatch(role -> role.equals(employee))) {

            // Kullanıcının 'employee' rolü varsa, sadece 'member' rolünü setle
            if (requestRoles.size() == 1 && requestRoles.stream().anyMatch(role -> role.equalsIgnoreCase(member.getRoleName()))) {
                userRole.add(member);

            } else {
                throw new BadRequestException(ErrorMessages.NOT_PERMITTED_USER_MESSAGE);
            }

        }


        User newUser = userMapper.mapUserRequestToUser(userRequest);

        newUser.setUserRole(userRole);

        newUser.setCreateDate(LocalDateTime.now());

        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User savedUser = userRepository.save(newUser);

        return ResponseMessage.<UserResponse>builder()
                .object(userMapper.mapUserToUserResponse(savedUser))
                .message(SuccessMessages.USER_SAVE_MESSAGE)
                .build();

    }


    // UpdateForAdminOrEmployee() ****
    public ResponseMessage<UserResponse> updateById(UserRequest userRequest,
                                                    HttpServletRequest request,
                                                    Long userId) {
        // Giriş yapan kullanıcı kim?
        User user = util.getAttributeUser(request);

        // Giriş yapan kullanıcının rollerini atadık.
        Set<UserRole> roles = user.getUserRole();

        // Update etmek istediği kullanıcının ID var mı?
        User user2 = util.isUserExist(userId);

        // Update etmek istenilen kişinin rolü nedir?
        Set<String> requestUserRole = userRequest.getUserRole();


        UserRole admin = userRoleService.getUserRole(RoleType.ADMIN);
        UserRole employee = userRoleService.getUserRole(RoleType.EMPLOYEE);
        UserRole member = userRoleService.getUserRole(RoleType.MEMBER);

        // update edilen user role setlemesi için kullanıldı.
        Set<UserRole> userRoleSet = new HashSet<>();

        // Update edilen fieldler unique mi?
        uniquePropertyValidator.checkUniqueProperties(user2, userRequest);

        // DTO --> POJO dönüşümü
        User uptadedUser = userMapper.mapUserRequestToUpdatedUser(userRequest,userId);

        if (roles.stream().anyMatch(role->role.equals(admin))) {

            for (String role : requestUserRole) { // Admin - ADmin - ADMiN farketmicek

                if (role.equalsIgnoreCase(admin.getRoleName())) {
                    userRoleSet.add(admin);

                } else if (role.equalsIgnoreCase(employee.getRoleName())) {
                    userRoleSet.add(employee);

                } else if (role.equalsIgnoreCase(member.getRoleName())) {
                    userRoleSet.add(member);

                } else {
                    throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_PERMITTED_USER_MESSAGE, role)); // yeniden bak
                }
            }

        } else if (roles.stream().anyMatch(role->role.equals(employee))){

            if(user2.getUserRole().stream().anyMatch(userRole -> userRole.getRoleType().equals(RoleType.ADMIN) ||
                    userRole.getRoleType().equals(RoleType.EMPLOYEE))){

                throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);

            }else{

                userRoleSet.add(member);
            }
        }else {

            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }





        // Password encode edilmesi
        uptadedUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));

       // Built-in kontrolü
       uptadedUser.setBuiltIn(Boolean.FALSE);

       // User Role setleme işlemi
       uptadedUser.setUserRole(userRoleSet);

       //
       uptadedUser.setCreateDate(LocalDateTime.now());

       User savedUser = userRepository.save(uptadedUser);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_UPDATE_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToUserResponse(savedUser))
                .build();

    }




    // Not: DeleteUser() **********
    public ResponseMessage<UserResponse> deleteUserById(Long userId ) {

        // DB'de bu kişili Id var mı?
        User user = util.isUserExist(userId);

        userRepository.deleteById(userId);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_DELETE_MESSAGE)
                .object(userMapper.mapUserToUserResponse(user))
                .httpStatus(HttpStatus.OK)
                .build();


    }

    // Not: Write For Runner ***************************************
    public long countAllAdmins(){
        return userRepository.countAdmin(RoleType.ADMIN);
    }


}
