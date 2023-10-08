package com.project.entity.business;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Category name must not be empty!")
    @Size(min=2 , max =80 , message= "Category name should be at least 2 characters!")
    private String categoryName;

    @NotNull
    private boolean builtIn = false;

    @NotNull(message = "Squence name must not be empty!")
    // One more than the largest number in sequence fields ??
    private int sequence;
}
