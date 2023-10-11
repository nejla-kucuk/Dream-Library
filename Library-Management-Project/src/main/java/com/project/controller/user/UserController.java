package com.project.controller.user;

import com.project.payload.request.user.UserRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.UserResponse;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Register()********
    // http://localhost:8080/user/register
    @PostMapping("/register")
    public ResponseEntity<ResponseMessage<UserResponse>> registerUser(@RequestBody @Valid UserRequest userRequest){

        return ResponseEntity.ok(userService.registerUser(userRequest));

    }

    // User()********
    // http://localhost:8080/users/user ???



    // getAllLoans()********
    // http://localhost:8080/user/loans?page=1&size=10&sort=createDate&type=desc
    @GetMapping("/loan")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MEMBER')")
    public ResponseEntity<Page<UserResponse>> getAllLoans(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "createDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {

        Page<UserResponse> adminsOrEmployeeOrMember = userService.getAllLoans(page,size,sort,type);

        return new ResponseEntity<>(adminsOrEmployeeOrMember, HttpStatus.OK);
    }

    // getAllUsers()********
    // http://localhost:8080/user/users?page=1&size=10&sort=createDate&type=desc
    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseEntity<Page<UserResponse>> getAllUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "createDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {

        Page<UserResponse> employeeOrMember = userService.getAllUsers(page,size,sort,type);

        return new ResponseEntity<>(employeeOrMember, HttpStatus.OK);

    }


    // getUserById() *******
    // http://localhost:8080/user/users/4  + GET
    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseMessage<UserResponse> getUserById(@PathVariable Long userId){

        return userService.getUserById(userId);
    }


    // UsersCreateForAdminOrEmployee() ****
    @PostMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseEntity<ResponseMessage<UserResponse>> usersCreateForAdminOrEmployee(@RequestBody @Valid UserRequest userRequest){

        return ResponseEntity.ok(userService.usersCreateForAdminOrEmployee(userRequest));

    }

    // UpdateForAdminOrEmployee() *****
    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')") // Bu kısımda ikiye mi bölünecek? Admin için ayrı Employee için ayrı?????
    public ResponseMessage<UserResponse> updateForAdminOrEmployee(@RequestBody @Valid UserRequest userRequest,
                                                                     @PathVariable Long userId) {
        return userService.updateForAdminOrEmployee(userRequest, userId);
    }

    // Not: DeleteUser() **********
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId,
                                                 HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(userService.deleteUserById(userId, httpServletRequest));
    }




}
