package com.project.entity.business;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Publisher name must not be empty!")
    @Size(min=4 , max =70 , message= "Publisher nameshould be at least 4 characters!")
    private String publisherName;

    @NotNull
    private boolean builtIn = false;

    // Realitions
    @OneToMany(mappedBy = "publisher",cascade = CascadeType.REMOVE)
    private List<Book> books = new ArrayList<>();
}
