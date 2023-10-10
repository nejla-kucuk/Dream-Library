package com.project.service.user;

import com.project.payload.request.authentication.SigninRequest;
import com.project.payload.response.authentication.AuthResponse;
import com.project.security.jwt.JwtUtils;
import com.project.security.sevice.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private  final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    // Signin()********
    public ResponseEntity<AuthResponse> authenticateUser(SigninRequest signinRequest) {

        String email = signinRequest.getEmail();

        String password = signinRequest.getPassword();

        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(email,password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = "Bearer " + jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Optional<String> role = roles.stream().findFirst();

        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();
        authResponse.email(userDetails.getEmail());
        authResponse.firstName(userDetails.getFirstName());
        authResponse.lastName(userDetails.getLastName());
        authResponse.token(token.substring(7));

        role.ifPresent(authResponse::role);

        return ResponseEntity.ok(authResponse.build());

    }
}
