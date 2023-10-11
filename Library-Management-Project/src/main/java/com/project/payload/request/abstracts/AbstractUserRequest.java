package com.project.payload.request.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public  abstract class AbstractUserRequest {
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
    @Size(min = 12, max = 12,message = "Your phone number should be 12 characters long!")
    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please, enter valid phone number")
    @Column(unique = true)
    private String phone;

    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotNull(message = "Email must not be empty!")
    @Email(message = "Please, enter valid email!")
    @Size(min=10, max=80 , message = "Your email should be between 10 and 80 chars!")
    @Column(unique = true)
    private String email;


    @NotNull(message = "Create Date must not be empty!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mmZ", timezone = "US")
    private LocalDateTime createDate;

    @Nullable
    // Hash ??
    private String resetPasswordCode;

    @NotNull
    private boolean builtIn = false;
}
