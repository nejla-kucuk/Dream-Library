package com.project.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.entity.user.User;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User ID must not be empty!")
    private Long userId;

    @NotNull(message = "Book ID must not be empty!")
    private Long bookId;

    @NotNull(message = "Loan Date must not be empty!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mmZ", timezone = "US")
    private LocalDateTime loanDate;

    @NotNull(message = "Expire Date must not be empty!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mmZ", timezone = "US")
    private LocalDateTime expireDate;

    @NotNull(message = "Return Date must not be empty!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mmZ", timezone = "US")
    private LocalDateTime returnDate;

    @Nullable
    @Size(max = 300, message = "The notes should be limited to 300 characters!")
    // Only for admin and employee
    private String notes;



    //Realitions
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST) // OneToOne olabilir mi?
    private Book book;

}
