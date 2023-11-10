package com.project.payload.request.business;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CategoryRequest {

    @NotNull(message = "Category name must not be empty!")
    @Size(min=2 , max =80 , message= "Category name should be at least 2 characters!")
    private String categoryName;

    @NotNull
    private boolean builtIn = false;

    @NotNull(message = "Squence name must not be empty!")
    // One more than the largest number in sequence fields ??
    private int sequence;
}
