package com.project.controller.user;

import com.project.payload.request.abstracts.BaseUserRequest;
import com.project.payload.request.user.UserRequest;
import com.project.payload.request.user.UserRequestWithoutUserRole;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.UserResponse;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // saveAdmin() ****
    public String saveAdmin(@RequestBody @Valid UserRequest userRequest) {

        return userService.saveAdmin(userRequest);
    }


    // Register()********
    // http://localhost:8080/register  --Kütüphaneye ilk kayıt olma işlemi
    @PostMapping("/register")
    public ResponseEntity<ResponseMessage<UserResponse>> registerUser(@RequestBody @Valid UserRequestWithoutUserRole userRequest){

        return ResponseEntity.ok(userService.registerUser(userRequest));

    }

    // User()******** ??
    // http://localhost:8080/user
    @PostMapping("/user")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MEMBER')")
    public ResponseMessage<UserResponse> getUser(HttpServletRequest httpServletRequest){

        return userService.getUser(httpServletRequest );

    }


    // Loans()********
    // http://localhost:8080/user/loans?page=1&size=10&sort=createDate&type=desc
  //  @GetMapping("user/loans")
  //  @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MEMBER')")
  //  public Page<LoanResponse> getAllLoans(
  //          HttpServletRequest request,
  //          @RequestParam(value = "page", defaultValue = "0") int page,
  //          @RequestParam(value = "size", defaultValue = "20") int size,
  //          @RequestParam(value = "sort", defaultValue = "createDate") String sort,
  //          @RequestParam(value = "type", defaultValue = "desc") String type
  //  ) {
//
  //      return userService.getAllLoans(request,page,size,sort,type);
//
  //  }

    // getAllUsers()******** ??
    // http://localhost:8080/users?page=1&size=10&sort=createDate&type=desc
    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public Page<List<UserResponse>> getAllUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "createDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {

        return userService.getAllUser(page,size,sort,type);

    }


    // getUserById() *******
    // http://localhost:8080/users/4  + GET
    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseMessage<UserResponse> getUserById(@PathVariable Long userId){

        return userService.getUserById(userId);
    }


    // UsersCreateForAdminOrEmployee() ****
    // http://localhost:8080/users
    @PostMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseEntity<ResponseMessage<UserResponse>> usersCreateForAdminOrEmployee(HttpServletRequest httpServletRequest,
                                                                                       @RequestBody @Valid UserRequest userRequest){

        return ResponseEntity.ok(userService.usersCreateForAdminOrEmployee(httpServletRequest,userRequest));

    }



    // UpdateForAdminOrEmployee() *****
    // http://localhost:8080/users/2
    @PutMapping("/users/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseMessage<UserResponse>  updateById(@RequestBody @Valid UserRequest userRequest,
                                                     @PathVariable Long userId,
                                                     HttpServletRequest request) {
        return userService.updateById(userRequest,request,userId);
    }



    // Not: DeleteUser() **********
    // http://localhost:8080/users/2
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<UserResponse> deleteUserById(@PathVariable Long id) {

        return userService.deleteUserById(id);
    }




}
