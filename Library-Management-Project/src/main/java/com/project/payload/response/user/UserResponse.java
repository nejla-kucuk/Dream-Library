package com.project.payload.response.user;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    private LocalDate birthDate;

    private String email;

    private int score = 0 ;

    private String userRole;

}
