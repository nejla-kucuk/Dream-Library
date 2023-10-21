package com.project.payload.response.business;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.io.File;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse {

    private Long id;

    private String bookName;

    private String isbn;

    private Integer pageCount;

    private int publishDate;

    private File image;

    private boolean loanable = true;

    private String shelfCode;

    private boolean active = true;

    private boolean featured  = false;

   // private LocalDateTime createDate;

   // private boolean builtIn = false;
}
