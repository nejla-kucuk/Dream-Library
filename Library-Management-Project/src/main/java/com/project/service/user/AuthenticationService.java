package com.project.service.user;

import com.project.entity.user.User;
import com.project.exception.BadRequestException;
import com.project.payload.mapper.UserMapper;
import com.project.payload.message.ErrorMessages;
import com.project.payload.request.authentication.SigninRequest;
import com.project.payload.request.business.UpdatePasswordRequest;
import com.project.payload.response.authentication.AuthResponse;
import com.project.payload.response.user.UserResponse;
import com.project.repository.user.UserRepository;
import com.project.security.jwt.JwtUtils;
import com.project.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

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

        // Optional<String> role = roles.stream().findFirst();

        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();
        authResponse.email(userDetails.getEmail());
        authResponse.token(token.substring(7));
        authResponse.roles(roles); //TODO: null veya empty



        return ResponseEntity.ok(authResponse.build());

    }


    // FindByEmail()*********
    public UserResponse findByEmail(String email) {

        User user = userRepository.findByEmailEquals(email);

        return userMapper.mapUserToUserResponse(user);

    }

    // Not: updatePassword() ************************************
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest,
                               HttpServletRequest request) {

        String email = (String) request.getAttribute("email");

        User user = userRepository.findByEmailEquals(email);


        if(Boolean.TRUE.equals(user.isBuiltIn())){
            throw  new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }


        if(!passwordEncoder.matches(updatePasswordRequest.getOldPassword(),user.getPassword())){
            throw  new BadRequestException(ErrorMessages.PASSWORD_NOT_MATCHED);
        }

        String encodedPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }
}
