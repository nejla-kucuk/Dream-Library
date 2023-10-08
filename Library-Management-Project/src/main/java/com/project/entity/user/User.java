package com.project.entity.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)

@Entity
@Table(name = "t_user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "First name must not be empty!")
    @Size(min=2 , max =30 , message= "First name should be at least 2 characters!")
    private String firstName;

    @NotNull(message = "Last name must not be empty!")
    @Size(min=2 , max =30 , message= "Last name should be at least 2 characters!")
    private String lastName;

    @NotNull(message = "Score must not be empty!")
    // Between -2 and +2 ??
    private int score = 0 ;

    @NotNull(message = "Address must not be empty!")
    @Size(min=10 , max =100 , message= "Address should be at least 10 characters!")
    private String address;

    @NotNull(message = "Phone must not be empty!")
    @Size(min = 12, max = 12,message = "Your phone number should be 12 characters long")
    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter valid phone number")
    private String phone;

}
