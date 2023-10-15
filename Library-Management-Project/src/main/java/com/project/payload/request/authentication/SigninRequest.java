package com.project.payload.request.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninRequest {

    @NotNull(message = "Email must not be empty!")
    @Email(message = "Please, enter valid email!")
    @Size(min=10, max=80 , message = "Your email should be between 10 and 80 chars!")
    private String email;

    @NotNull(message = "Please, enter your password!")
    @Size(min = 8, max = 60,message = "Your password should be at least 8 chars or maximum 60 characters!")
    //@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\\\d)(?=.*[-+_!@#$%^&*., ?]).+$")
    private String password;
}
