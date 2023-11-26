package com.project.payload.request.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.io.File;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BookRequest {

    @NotNull(message = "Book name must not be empty!")
    @Size(min=2 , max =20 , message= "Book name should be at least 2 characters!")
    private String bookName;

    @NotNull(message = "Book ISBN must not be empty!")
    @Size(min = 17, max = 17, message = "ISBN number must be exactly 17 characters long!")
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d$",
            message = "Please enter a valid ISBN number in the format 999-99-99999-99-9 !")
    private String isbn;

    @Nullable
    private Integer pageCount;


    private Long authorId;


    private Long publisherId;


    private Long categoryId;

    @Nullable
    @Min(value = 1000, message = "Publish date must be a valid year (e.g., 1000 or later)!")
    @Max(value = 9999, message = "Publish date must be a valid year (e.g., 9999 or earlier)!")
    private int publishDate;

    @Nullable
    private File image;

    @NotNull
    private boolean loanable = true;

    @NotNull(message = "Shelf code must not be empty!")
    @Pattern(regexp = "^[A-Z]{2}-\\d{3}$", message = "Shelf code must be in the format 'WF-214'!")
    @Size(min = 6, max = 6, message = "Shelf code must be exactly 6 characters long!")
    private String shelfCode;

    @NotNull
    private boolean active;

    @NotNull
    private boolean featured;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mmZ", timezone = "US")
    private LocalDateTime createDate;

    @NotNull
    private boolean builtIn;


}
