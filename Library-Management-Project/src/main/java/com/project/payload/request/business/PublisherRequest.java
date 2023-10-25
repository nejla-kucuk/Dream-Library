package com.project.payload.request.business;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PublisherRequest {

    @NotNull(message = "Publisher name must not be empty!")
    @Size(min=4 , max =70 , message= "Publisher nameshould be at least 4 characters!")
    private String publisherName;

    @NotNull
    private boolean builtIn = false;
}
