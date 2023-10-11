package com.project.payload.request.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.payload.request.abstracts.AbstractUserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequestWithoutPassword extends AbstractUserRequest {



}
