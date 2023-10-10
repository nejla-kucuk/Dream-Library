package com.project.controller.user;


import com.project.payload.request.authentication.SigninRequest;
import com.project.payload.response.authentication.AuthResponse;
import com.project.service.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // signin()********
    // http://localhost:8080/auth/signin  + POST + JSON
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid SigninRequest signinRequest){

        return authenticationService.authenticateUser(signinRequest);
    }
}
