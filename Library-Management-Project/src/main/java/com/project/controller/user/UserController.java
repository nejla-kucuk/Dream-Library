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

import javax.validation.Valid;

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


    // Loans()********
    // http://localhost:8080//user/loans?page=1&size=10&sort=createDate&type=desc
    @GetMapping("/loan")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MEMBER')")
    public ResponseEntity<Page<UserResponse>> getUserLoans(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "createDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {

        Page<UserResponse> adminsOrEmployeeOrMember = userService.getUserByPage(page,size,sort,type);

        return new ResponseEntity<>(adminsOrEmployeeOrMember, HttpStatus.OK);
    }




}
