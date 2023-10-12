package com.project.payload.request.user;

import com.project.entity.user.UserRole;
import com.project.payload.request.abstracts.BaseUserRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserRequest extends BaseUserRequest {

   @NotNull(message = "")
   private Set<String> userRole;


}
