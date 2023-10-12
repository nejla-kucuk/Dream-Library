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
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // Not: signin()  ********************************
    @PostMapping("/signin") // http://localhost:8080/signin  + POST + JSON
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid SigninRequest signinRequest){

        return authenticationService.authenticateUser(signinRequest);
    }

    // FindByEmail()*********
    // http://localhost:8080/auth/user   + GET
    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MEMBER')")
    public ResponseEntity<UserResponse> findByUsername(HttpServletRequest request){

        String email = (String) request.getAttribute("email");
        UserResponse userResponse =  authenticationService.findByEmail(email);
        return ResponseEntity.ok(userResponse);
    }

    // Not: updatePassword() ************************************
    // http://localhost:8080/auth/updatePassword
    @PatchMapping("/updatePassword")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MEMBER')")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest,
                                                 HttpServletRequest request){

        authenticationService.updatePassword(updatePasswordRequest, request);
        String response = SuccessMessages.PASSWORD_CHANGED_RESPONSE_MESSAGE;
        return  ResponseEntity.ok(response);

    }
}
