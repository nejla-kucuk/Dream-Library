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
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Author name must not be empty!")
    @Size(min=4 , max =70 , message= "Author nameshould be at least 4 characters!")
    private String authorName;

    @NotNull
    private boolean builtIn = false;

    // Realitions
   // @OneToMany(mappedBy = "author",cascade = CascadeType.REMOVE)
   // private List<Book> books = new ArrayList<>();


}
