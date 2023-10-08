package com.project.entity.user;

import com.project.entity.enums.RoleType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_roles")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType roleType;

    @NotNull(message = "Role name must not be empty!")
    private String roleName;
}
