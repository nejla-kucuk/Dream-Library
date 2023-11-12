package com.project.payload.response.business;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.entity.business.Author;
import com.project.entity.business.Category;
import com.project.entity.business.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.File;


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

    private Author author;

    private Publisher publisher;

    private Category category;
}
