package com.project.controller.user;


import com.project.payload.message.SuccessMessages;
import com.project.payload.request.authentication.SigninRequest;
import com.project.payload.request.business.UpdatePasswordRequest;
import com.project.payload.response.authentication.AuthResponse;
import com.project.payload.response.user.UserResponse;
import com.project.service.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
//@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // Not: signin()  ********************************
    @PostMapping("/signin") // http://localhost:8080/signin  + POST + JSON
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid SigninRequest signinRequest){

        return authenticationService.authenticateUser(signinRequest);
    }

}
