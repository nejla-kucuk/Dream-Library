package com.project.controller.user;

import com.project.payload.request.user.UserRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.UserResponse;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Register()********
    // http://localhost:8080/users/register
    @PostMapping("/register")
    public ResponseEntity<ResponseMessage<UserResponse>> registerUser(@RequestBody @Valid UserRequest userRequest){

        return ResponseEntity.ok(userService.registerUser(userRequest));

    }




}
