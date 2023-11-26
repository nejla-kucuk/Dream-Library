package com.project.payload.request.business;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AuthorRequest {

    @NotNull(message = "Author name must not be empty!")
    @Size(min=4 , max =70 , message= "Author nameshould be at least 4 characters!")
    private String authorName;

    @NotNull
    private Boolean builtIn = false;

}
