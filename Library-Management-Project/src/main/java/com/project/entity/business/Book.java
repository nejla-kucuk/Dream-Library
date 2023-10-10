package com.project.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Book name must not be empty!")
    @Size(min=2 , max =20 , message= "Book name should be at least 2 characters!")
    private String bookName;

    @NotNull(message = "Book ISBN must not be empty!")
    @Size(min = 17, max = 17, message = "ISBN number must be exactly 17 characters long!")
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d$",
            message = "Please enter a valid ISBN number in the format 999-99-99999-99-9 !")
    @Column(unique = true)
    private String isbn;

    @Nullable
    private int pageCount;

    @NotNull(message = "Author Id must not be empty!")
    private Long authorId;

    @NotNull(message = "Publisher Id must not be empty!")
    private Long publisherId;

    @Nullable
    @Min(value = 1000, message = "Publish date must be a valid year (e.g., 1000 or later)!")
    @Max(value = 9999, message = "Publish date must be a valid year (e.g., 9999 or earlier)!")
    private int publishDate;

    @NotNull(message = "Category Id must not be empty!")
    private Long categoryId;

    @Nullable
    private File image;

    @NotNull
    private boolean loanable = true;

    @NotNull(message = "Shelf code must not be empty!")
    @Pattern(regexp = "^[A-Z]{2}-\\d{3}$", message = "Shelf code must be in the format 'AA-999'!")
    @Size(min = 6, max = 6, message = "Shelf code must be exactly 6 characters long!")
    private String shelfCode;

    @NotNull
    private boolean active = true;

    @NotNull
    private boolean featured  = false;

    @NotNull(message = "Create Date must not be empty!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mmZ", timezone = "US")
    private LocalDateTime createDate;

    @NotNull
    private boolean builtIn = false;


    // Realitions
    @OneToMany(mappedBy = "book",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Loan> loans = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(cascade = CascadeType.PERSIST) //ManyToMay olabilir mi?
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;


}
