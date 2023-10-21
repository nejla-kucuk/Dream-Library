package com.project.payload.request.user;

import com.project.payload.request.abstracts.BaseUserRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@SuperBuilder
public class UserRequestWithoutUserRole extends BaseUserRequest {
}
